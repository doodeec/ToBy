package com.doodeec.toby.ApplicationState;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.doodeec.toby.Model.BaseObservable;
import com.doodeec.toby.Model.DbSavable;
import com.doodeec.toby.Model.Shop;
import com.doodeec.toby.Model.ShopCategory;
import com.doodeec.toby.Model.ShoppingList;
import com.doodeec.toby.Storage.DBHelper;
import com.doodeec.toby.Storage.ShopDBEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds data objects in one application run
 *
 * @author Dusan Bartos
 */
public class AppData {

    // singleton
    private static AppData instance;

    // thread safe double null check
    public static AppData getInstance() {
        if (instance == null) {
            synchronized (AppData.class) {
                if (instance == null) {
                    instance = createInstance();
                }
            }
        }

        return instance;
    }

    // wrapper for singleton creation
    private static AppData createInstance() {
        AppData appData = new AppData();

        // set database observers
        /*appData.allShops.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (Shop shop : instance.allShops.getData()) {
                    instance.saveObjectToDB(database, shop);
                }
                database.close();
            }
        });
        appData.allShoppingLists.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (ShoppingList list : instance.allShoppingLists.getData()) {
                    instance.saveObjectToDB(database, list);
                }
                database.close();
            }
        });
        appData.allCategories.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (ShopCategory category : instance.allCategories.getData()) {
                    instance.saveObjectToDB(database, category);
                }
                database.close();
            }
        });*/

        return appData;
    }

    // properties
    private BaseObservable<Shop> allShops = new BaseObservable<>();
    private BaseObservable<ShopCategory> allCategories = new BaseObservable<>();
    private BaseObservable<ShoppingList> allShoppingLists = new BaseObservable<>();

    public void setShops(List<Shop> shops) {
        this.allShops.updateData(shops);
    }

    public void setCategories(List<ShopCategory> categories) {
        this.allCategories.updateData(categories);
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.allShoppingLists.updateData(shoppingLists);
    }

    public List<Shop> getShops() {
        if (allShops.getData() == null) {
            SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
            List<Shop> shops = new ArrayList<>();

            try {
                Cursor cursor = db.query(ShopDBEntry.TABLE_NAME, null, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    shops.add(new Shop(cursor));
                }

                allShops.setData(shops);
            } finally {
                db.close();
            }
        }

        return allShops.getData();
    }

    public List<ShopCategory> getCategories() {
        if (allCategories.getData() == null) {
            SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
            List<ShopCategory> categories = new ArrayList<>();

            try {
                Cursor cursor = db.query(ShopDBEntry.TABLE_NAME, null, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    categories.add(new ShopCategory(cursor));
                }

                allCategories.setData(categories);
            } finally {
                db.close();
            }
        }

        return allCategories.getData();
    }

    public List<ShoppingList> getShoppingLists() {
        if (allShoppingLists.getData() == null) {
            SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
            List<ShoppingList> shoppingLists = new ArrayList<>();

            try {
                Cursor cursor = db.query(ShopDBEntry.TABLE_NAME, null, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    shoppingLists.add(new ShoppingList(cursor));
                }

                allShoppingLists.setData(shoppingLists);
            } finally {
                db.close();
            }
        }

        return allShoppingLists.getData();
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

    private void saveObjectToDB(SQLiteDatabase db, DbSavable object) {
        ContentValues values = object.getValues();

        long newRowId = -1;
        String[] column = {DbSavable.id_column};
        String[] args = {String.valueOf(object.getId())};

        Cursor c = db.query(object.getTableName(), column, "id = ?", args, null, null, null);

        if (c.moveToFirst() && object.getId() != null) {
            db.update(object.getTableName(), values, "id = ?", args);

            Log.d("TOBY", "Update object " + object.getClass().getSimpleName() + " with id: " + args[0]);
        } else {
            newRowId = db.insert(object.getTableName(), null, values);
            object.setId((int) newRowId);

            Log.d("TOBY", "Inserted " + object.getClass().getSimpleName() + ": " + newRowId);
        }
        c.close();
    }
}
