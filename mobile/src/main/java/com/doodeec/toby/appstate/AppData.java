package com.doodeec.toby.appstate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.doodeec.toby.dbstorage.DBHelper;
import com.doodeec.toby.dbstorage.ListItemDBEntry;
import com.doodeec.toby.dbstorage.ShoppingListDBEntry;
import com.doodeec.toby.objectmodel.IDbSavable;
import com.doodeec.toby.objectmodel.ShoppingList;
import com.doodeec.toby.objectmodel.ShoppingListItem;
import com.doodeec.tobycommon.model.BaseObservable;
import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Holds data objects in one application run
 *
 * @author Dusan Bartos
 */
public class AppData {

    // singleton
    private static AppData sInstance;

    // thread safe double null check
    public static AppData getInstance() {
        if (sInstance == null) {
            synchronized (AppData.class) {
                if (sInstance == null) {
                    sInstance = createInstance();
                }
            }
        }

        return sInstance;
    }

    // wrapper for singleton creation
    private static AppData createInstance() {
        AppData appData = new AppData();

        appData.getShoppingLists();
        appData.getShoppingListItems();

        // init observers
        appData.initInstance();

        return appData;
    }

    private void initInstance() {
        // set database observers
        allShoppingLists.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                saveShoppingLists();
            }
        });
    }

    // properties
    private BaseObservable<ShoppingList> allShoppingLists = new BaseObservable<>();
    private BaseObservable<ShoppingListItem> allShoppingListItems = new BaseObservable<>();

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        for (ShoppingList shoppingList : shoppingLists) {
            addShoppingList(shoppingList);
        }
    }

    public void addShoppingList(ShoppingList shoppingList) {
        this.allShoppingLists.addSingleItem(shoppingList);

        // register observer for shopping list items
        shoppingList.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                synchronized (DBHelper.class) {
                    SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();

                    try {
                        database.beginTransaction();
                        saveListToDB(database, (ShoppingList) observable);
                        database.setTransactionSuccessful();
                    } finally {
                        database.endTransaction();
                        database.close();
                        database.releaseReference();
                    }
                }
            }
        });
    }

    public void setShoppingListItems(List<ShoppingListItem> listItems) {
        this.allShoppingListItems.updateData(listItems);
    }

    public void addShoppingListItem(ShoppingListItem shoppingListItem) {
        this.allShoppingListItems.addSingleItem(shoppingListItem);
    }

    public List<ShoppingList> getShoppingLists() {
        if (allShoppingLists.getData() == null) {
            synchronized (DBHelper.class) {
                SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                List<ShoppingList> shoppingLists = new ArrayList<>();

                try {
                    Cursor cursor = db.query(ShoppingListDBEntry.TABLE_NAME, null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        shoppingLists.add(new ShoppingList(cursor));
                    }
                    cursor.close();
                } finally {
                    db.close();
                    db.releaseReference();
                }

                setShoppingLists(shoppingLists);
            }
        }

        return allShoppingLists.getData();
    }

    public List<ShoppingListItem> getShoppingListItems() {
        if (allShoppingListItems.getData() == null) {
            synchronized (DBHelper.class) {
                SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                List<ShoppingListItem> shoppingListItems = new ArrayList<>();

                try {
                    Cursor cursor = db.query(ListItemDBEntry.TABLE_NAME, null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        shoppingListItems.add(new ShoppingListItem(cursor));
                    }
                    cursor.close();
                } finally {
                    db.close();
                    db.releaseReference();
                }

                allShoppingListItems.setData(shoppingListItems);
            }
        }

        return allShoppingListItems.getData();
    }

    public JSONArray getShoppingListsAsJSON() {
        // save lists to ensure id attribute is available for each list
        saveShoppingLists();

        JSONArray lists = new JSONArray();
        for (ShoppingList list : getShoppingLists()) {
            lists.put(list.toJSON());
        }
        return lists;
    }

    /**
     * Saves all shopping lists to the DB
     */
    private void saveShoppingLists() {
        synchronized (DBHelper.class) {
            SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();

            try {
                database.beginTransaction();
                for (ShoppingList list : allShoppingLists.getData()) {
                    saveObjectToDB(database, list);
                }
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
                database.close();
                database.releaseReference();
            }
        }
    }

    /**
     * Saves/Updates every item in the shopping list and then saves/updates shopping list itself
     *
     * @param database database instance
     * @param list     shopping list
     */
    private void saveListToDB(SQLiteDatabase database, ShoppingList list) {
        for (IShoppingListItem item : list.getItems()) {
            if (item instanceof ShoppingListItem) {
                saveObjectToDB(database, (ShoppingListItem) item);
            }
        }
        saveObjectToDB(database, list);
    }

    /**
     * Saves interface object to the DB
     *
     * @param db     database instance
     * @param object savable interace
     */
    private void saveObjectToDB(SQLiteDatabase db, IDbSavable object) {
        ContentValues values = object.getValues();

        String[] column = {IDbSavable.id_column};
        String[] args = {String.valueOf(object.getId())};

        Cursor c = db.query(object.getTableName(), column, "id = ?", args, null, null, null);

        if (c.moveToFirst() && object.getId() != null) {
            db.update(object.getTableName(), values, "id = ?", args);

            Log.d("TOBY", "Update object " + object.getClass().getSimpleName() + " with id: " + args[0]);
        } else {
            long newRowId = db.insert(object.getTableName(), null, values);
            if (newRowId != -1) {
                object.setId((int) newRowId);
                Log.d("TOBY", "Inserted " + object.getClass().getSimpleName() + ": " + newRowId);
            } else {
                Log.e("TOBY", "Error inserting " + object.getClass().getSimpleName());
            }
        }
        c.close();
    }
}
