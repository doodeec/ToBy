package com.doodeec.toby.appstate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.doodeec.toby.dbstorage.DBHelper;
import com.doodeec.toby.dbstorage.ListItemDBEntry;
import com.doodeec.toby.dbstorage.ShopCategoryDBEntry;
import com.doodeec.toby.dbstorage.ShopDBEntry;
import com.doodeec.toby.dbstorage.ShoppingListDBEntry;
import com.doodeec.toby.objectmodel.IDbSavable;
import com.doodeec.toby.objectmodel.Shop;
import com.doodeec.toby.objectmodel.ShopCategory;
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

                    if (!sInstance.hasCategories()) {
                        sInstance.generateDefaultCategories();
                    }

                    sInstance.generateDefaultShop();
//                    instance.generateMockData();
                }
            }
        }

        return sInstance;
    }

    // wrapper for singleton creation
    private static AppData createInstance() {
        AppData appData = new AppData();

//        appData.getShops();
//        appData.getCategories();
        appData.getShoppingLists();
        appData.getShoppingListItems();

        // set database observers
        /*appData.allShops.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (Shop shop : instance.allShops.getData()) {
                    instance.saveObjectToDB(database, shop);
                }
                database.close();
                database.releaseReference();
            }
        });*/
        appData.allShoppingLists.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                synchronized (DBHelper.class) {
                    SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();

                    try {
                        for (ShoppingList list : sInstance.allShoppingLists.getData()) {
                            for (IShoppingListItem item : list.getItems()) {
                                if (item instanceof ShoppingListItem) {
                                    sInstance.saveObjectToDB(database, (ShoppingListItem) item);
                                }
                            }
                            sInstance.saveObjectToDB(database, list);
                        }
                    } finally {
                        database.close();
                        database.releaseReference();
                    }
                }
            }
        });
        /*appData.allCategories.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (ShopCategory category : instance.allCategories.getData()) {
                    instance.saveObjectToDB(database, category);
                }
                database.close();
                database.releaseReference();
            }
        });*/

        return appData;
    }

    // properties
    private BaseObservable<Shop> allShops = new BaseObservable<>();
    private BaseObservable<ShopCategory> allCategories = new BaseObservable<>();
    private BaseObservable<ShoppingList> allShoppingLists = new BaseObservable<>();
    private BaseObservable<ShoppingListItem> allShoppingListItems = new BaseObservable<>();

    public void setShops(List<Shop> shops) {
        this.allShops.updateData(shops);
    }

    public void addShop(Shop shop) {
        this.allShops.addSingleItem(shop);
    }

    public void setCategories(List<ShopCategory> categories) {
        this.allCategories.updateData(categories);
    }

    public void addCategory(ShopCategory category) {
        this.allCategories.addSingleItem(category);
    }

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
                        for (IShoppingListItem item : ((ShoppingList) observable).getItems()) {
                            if (item instanceof ShoppingListItem) {
                                saveObjectToDB(database, (ShoppingListItem) item);
                            }
                        }
                        saveObjectToDB(database, (ShoppingList) observable);
                    } finally {
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

    public List<Shop> getShops() {
        if (allShops.getData() == null) {
            synchronized (DBHelper.class) {
                SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                List<Shop> shops = new ArrayList<>();

                try {
                    Cursor cursor = db.query(ShopDBEntry.TABLE_NAME, null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        shops.add(new Shop(cursor));
                    }
                    cursor.close();
                } finally {
                    db.close();
                    db.releaseReference();
                }

                allShops.setData(shops);
            }
        }

        return allShops.getData();
    }

    public List<ShopCategory> getCategories() {
        if (allCategories.getData() == null) {
            synchronized (DBHelper.class) {
                SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                List<ShopCategory> categories = new ArrayList<>();

                try {
                    Cursor cursor = db.query(ShopCategoryDBEntry.TABLE_NAME, null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        categories.add(new ShopCategory(cursor));
                    }
                    cursor.close();
                } finally {
                    db.close();
                    db.releaseReference();
                }

                allCategories.setData(categories);
            }
        }

        return allCategories.getData();
    }

    public boolean hasCategories() {
        synchronized (DBHelper.class) {
            SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
            boolean result = false;
            try {
                Cursor cursor = db.query(ShopCategoryDBEntry.TABLE_NAME, null, null, null, null, null, null);
                if (cursor.getCount() > 0) result = true;
                cursor.close();
            } finally {
                db.close();
                db.releaseReference();
            }
            return result;
        }
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

    public ShopCategory getCategoryById(int id) {
        for (ShopCategory category : getCategories()) {
            if (category.getId() == id) return category;
        }

        return null;
    }

    public Shop getShopById(int id) {
        for (Shop shop : getShops()) {
            if (shop.getId() == id) return shop;
        }
        return null;
    }

    private void saveShoppingLists() {
        synchronized (DBHelper.class) {
            SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();

            try {
                for (ShoppingList list : sInstance.allShoppingLists.getData()) {
                    sInstance.saveObjectToDB(database, list);
                }
            } finally {
                database.close();
                database.releaseReference();
            }
        }
    }

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

    //TODO remove
    private void generateDefaultShop() {
        this.allShops.addSingleItem(new Shop("BILLA"));
    }

    /**
     * Initializes database entries for default shop categories
     */
    private void generateDefaultCategories() {
        List<ShopCategory> categories = new ArrayList<>();
        categories.add(new ShopCategory("Potraviny", 1));
        categories.add(new ShopCategory("Ovocie a zelenina", 2));
        categories.add(new ShopCategory("Mäsiarstvo", 3));
        categories.add(new ShopCategory("Domáce potreby", 4));
        categories.add(new ShopCategory("Darčeky", 5));
        categories.add(new ShopCategory("Obuv", 6));
        categories.add(new ShopCategory("Oblečenie", 7));
        categories.add(new ShopCategory("Šport", 8));
        categories.add(new ShopCategory("Drogéria", 9));
        categories.add(new ShopCategory("Nábytok", 10));

        synchronized (DBHelper.class) {
            SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
            try {
                for (ShopCategory cat : categories) {
                    saveObjectToDB(db, cat);
                }
            } finally {
                db.close();
                db.releaseReference();
            }
        }
    }
}
