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
import com.doodeec.tobycommon.model.UnitType;
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
    private static AppData instance;

    // thread safe double null check
    public static AppData getInstance() {
        if (instance == null) {
            synchronized (AppData.class) {
                if (instance == null) {
                    instance = createInstance();

                    instance.generateDefaultCategories();
                    instance.generateDefaultShop();
//                    instance.generateMockData();
                }
            }
        }

        return instance;
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
            }
        });*/
        appData.allShoppingLists.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (ShoppingList list : instance.allShoppingLists.getData()) {
                    for (IShoppingListItem item : list.getItems()) {
                        if (item instanceof ShoppingListItem) {
                            instance.saveObjectToDB(database, (ShoppingListItem) item);
                        }
                    }
                    instance.saveObjectToDB(database, list);
                }
                database.close();
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
        this.allShoppingLists.updateData(shoppingLists);

        for (ShoppingList shoppingList : shoppingLists) {
            // register observer for shopping list items
            shoppingList.addObserver(new Observer() {
                @Override
                public void update(Observable observable, Object data) {
                    SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                    for (IShoppingListItem item : ((ShoppingList) observable).getItems()) {
                        if (item instanceof ShoppingListItem) {
                            saveObjectToDB(database, (ShoppingListItem) item);
                        }
                    }
                    database.close();
                }
            });
        }
    }

    public void addShoppingList(ShoppingList shoppingList) {
        this.allShoppingLists.addSingleItem(shoppingList);

        // register observer for shopping list items
        shoppingList.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
                for (IShoppingListItem item : ((ShoppingList) observable).getItems()) {
                    if (item instanceof ShoppingListItem) {
                        saveObjectToDB(database, (ShoppingListItem) item);
                    }
                }
                database.close();
            }
        });
    }

    public void setShoppingListItems(List<ShoppingListItem> listItems) {
        this.allShoppingListItems.updateData(listItems);
    }

    public void addShoppingListItem(ShoppingListItem shoppingListItem) {
        this.allShoppingListItems.addSingleItem(shoppingListItem);
    }

    public synchronized List<Shop> getShops() {
        if (allShops.getData() == null) {
            SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
            List<Shop> shops = new ArrayList<>();

            try {
                Cursor cursor = db.query(ShopDBEntry.TABLE_NAME, null, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    shops.add(new Shop(cursor));
                }

                allShops.setData(shops);
                cursor.close();
            } finally {
                db.close();
            }
        }

        return allShops.getData();
    }

    public synchronized List<ShopCategory> getCategories() {
        if (allCategories.getData() == null) {
            SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
            List<ShopCategory> categories = new ArrayList<>();

            try {
                Cursor cursor = db.query(ShopCategoryDBEntry.TABLE_NAME, null, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    categories.add(new ShopCategory(cursor));
                }

                allCategories.setData(categories);
                cursor.close();
            } finally {
                db.close();
            }
        }

        return allCategories.getData();
    }

    public synchronized List<ShoppingList> getShoppingLists() {
        if (allShoppingLists.getData() == null) {
            SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
            List<ShoppingList> shoppingLists = new ArrayList<>();

            try {
                Cursor cursor = db.query(ShoppingListDBEntry.TABLE_NAME, null, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    shoppingLists.add(new ShoppingList(cursor));
                }

                allShoppingLists.setData(shoppingLists);
                cursor.close();
            } finally {
                db.close();
            }
        }

        return allShoppingLists.getData();
    }

    public synchronized List<ShoppingListItem> getShoppingListItems() {
        if (allShoppingListItems.getData() == null) {
            SQLiteDatabase db = new DBHelper(AppState.getAppContext()).getReadableDatabase();
            List<ShoppingListItem> shoppingListItems = new ArrayList<>();

            try {
                Cursor cursor = db.query(ListItemDBEntry.TABLE_NAME, null, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    shoppingListItems.add(new ShoppingListItem(cursor));
                }

                allShoppingListItems.setData(shoppingListItems);
                cursor.close();
            } finally {
                db.close();
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
        SQLiteDatabase database = new DBHelper(AppState.getAppContext()).getReadableDatabase();
        for (ShoppingList list : instance.allShoppingLists.getData()) {
            instance.saveObjectToDB(database, list);
        }
        database.close();
    }

    private void saveObjectToDB(SQLiteDatabase db, IDbSavable object) {
        ContentValues values = object.getValues();

        long newRowId = -1;
        String[] column = {IDbSavable.id_column};
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

    //TODO remove
    private void generateDefaultShop() {
        this.allShops.addSingleItem(new Shop("BILLA"));
    }

    private void generateDefaultCategories() {
        addCategory(new ShopCategory("Potraviny"));
        addCategory(new ShopCategory("Ovocie a zelenina"));
        addCategory(new ShopCategory("Mäsiarstvo"));
        addCategory(new ShopCategory("Domáce potreby"));
        addCategory(new ShopCategory("Darčeky"));
        addCategory(new ShopCategory("Obuv"));
        addCategory(new ShopCategory("Oblečenie"));
        addCategory(new ShopCategory("Drogéria"));
        addCategory(new ShopCategory("Nábytok"));
    }

    //TODO remove
    private void generateMockData() {
        addShoppingList(new ShoppingList("Prvy zoznam"));
        addShoppingList(new ShoppingList("Druhy zoznam"));
        addShoppingList(new ShoppingList("Treti zoznam"));
        addShoppingList(new ShoppingList("Stvrty zoznam"));
        addShoppingList(new ShoppingList("Piaty zoznam"));
        addShoppingList(new ShoppingList("Siesty zoznam"));

        List<IShoppingListItem> items = new ArrayList<>();

        IShoppingListItem item = new ShoppingListItem("Prva polozka");
        item.setAmount(4);
        item.setUnitType(UnitType.Units);
        IShoppingListItem item2 = new ShoppingListItem("Druha polozka");
        item2.setAmount(10);
        item2.setUnitType(UnitType.Units);
        IShoppingListItem item3 = new ShoppingListItem("Tretia polozka");
        item2.setAmount(5);
        item2.setUnitType(UnitType.Liter);
        IShoppingListItem item4 = new ShoppingListItem("Stvrta polozka");
        item2.setAmount(1);
        item2.setUnitType(UnitType.Kilo);
        IShoppingListItem item5 = new ShoppingListItem("Piata polozka");
        item2.setAmount(10);
        item2.setUnitType(UnitType.Units);

        items.add(item);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        this.allShoppingLists.getData().get(0).setItems(items);
        this.allShoppingLists.getData().get(1).setItems(items);
        this.allShoppingLists.getData().get(2).setItems(items);
        this.allShoppingLists.getData().get(3).setItems(items);
        this.allShoppingLists.getData().get(4).setItems(items);
        this.allShoppingLists.getData().get(5).setItems(items);
    }
}
