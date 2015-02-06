package com.doodeec.tobycommon.model;

import android.util.Log;

import com.doodeec.tobycommon.model.interfaces.IShop;
import com.doodeec.tobycommon.model.interfaces.IShopCategory;
import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Shopping list object
 *
 * @author Dusan Bartos
 */
public class ShoppingList {

    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_COMPLETED = "completed";
    protected static final String KEY_ITEMS = "items";

    protected Integer id;
    protected IShopCategory category;
    protected IShop shop;
    protected String name;
    protected Boolean completed = false;
    protected List<IShoppingListItem> items = new ArrayList<>();
    protected Date created;
    protected Date edited;
    protected Date dueDate;
    protected Date dateCompleted;

    protected ShoppingList() {

    }

    public ShoppingList(String name) {
        this.name = name;
    }

    public ShoppingList(JSONObject object) {
        try {
            id = object.getInt(KEY_ID);
            name = object.getString(KEY_NAME);
            completed = object.getBoolean(KEY_COMPLETED);
            items = deserializeItems(object.getString(KEY_ITEMS));
        } catch (JSONException e){
            Log.e("Shopping list JSON exception", e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShop(IShop shop) {
        this.shop = shop;
    }

    public IShop getShop() {
        return shop;
    }

    public void setShopCategory(IShopCategory shopCategory) {
        this.category = shopCategory;
    }

    public IShopCategory getShopCategory() {
        return category;
    }

    public boolean hasActiveItems() {
        for (IShoppingListItem item : items) {
            if (!item.getChecked()) return true;
        }
        return false;
    }

    public List<IShoppingListItem> getItems() {
        return items;
    }

    public void setItems(List<IShoppingListItem> items) {
        if (items != null) {
            this.items = items;
        } else {
            this.items.clear();
        }
    }

    public void addItem(IShoppingListItem item) {
        this.items.add(item);
    }

    protected static String serializeItems(List<IShoppingListItem> items) {
        JSONArray itemsArray = new JSONArray();
        for (IShoppingListItem listItem: items) {
            itemsArray.put(listItem.toJSON());
        }
        return itemsArray.toString();
    }

    protected static List<IShoppingListItem> deserializeItems(String serializedItems) {
        List<IShoppingListItem> items = new ArrayList<>();

        try {
            JSONArray itemsArray = new JSONArray(serializedItems);
            for (int i = 0; i < itemsArray.length(); i++) {
                items.add(new ShoppingListItem(itemsArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("Shopping list items", "Parsing shopping list items failed" + e.getMessage());
        }

        return items;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, id);
            jsonObject.put(KEY_NAME, name);
            jsonObject.put(KEY_COMPLETED, completed);
            jsonObject.put(KEY_ITEMS, serializeItems(items));
        } catch (JSONException e) {
            Log.e("Shopping list JSON exception", e.getLocalizedMessage());
        }
        return jsonObject;
    }
}
