package com.doodeec.toby.ApplicationState;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doodeec.toby.Model.BaseObservable;
import com.doodeec.toby.Model.Shop;
import com.doodeec.toby.Model.ShopCategory;
import com.doodeec.toby.Model.ShoppingList;
import com.doodeec.toby.Storage.DBHelper;
import com.doodeec.toby.Storage.ShopDBEntry;

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
        appData.allShops.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (Shop shop : instance.allShops.getData()) {
                    shop.saveToDb(database);
                }
                database.close();
            }
        });
        appData.allShoppingLists.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (ShoppingList list : instance.allShoppingLists.getData()) {
                    list.saveToDb(database);
                }
                database.close();
            }
        });
        appData.allCategories.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (ShopCategory shop : instance.allCategories.getData()) {
                    shop.saveToDb(database);
                }
                database.close();
            }
        });

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
        for (ShopCategory category: getCategories()) {
            if (category.getId() == id) return category;
        }

        return null;
    }
}
