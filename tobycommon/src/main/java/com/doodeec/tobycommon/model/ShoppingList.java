package com.doodeec.tobycommon.model;

import android.util.Log;

import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

/**
 * Shopping list object
 *
 * @author Dusan Bartos
 */
public class ShoppingList extends Observable {

    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_COMPLETED = "completed";
    protected static final String KEY_ITEMS = "items";

    protected Integer id;
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

    public ShoppingList(JSONObject object) throws JSONException {
        id = object.getInt(KEY_ID);
        name = object.getString(KEY_NAME);
        completed = object.getBoolean(KEY_COMPLETED);
        items = deserializeItems(object.getString(KEY_ITEMS));
    }

    public void updateFrom(ShoppingList list) {
        id = list.id;
        name = list.name;
        completed = list.completed;
        items = list.items;
    }

    public void setName(String name) {
        this.name = name;
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
    
    public void checkCompletion() {
        completed = !hasActiveItems();
    }
    
    public boolean hasActiveItems() {
        if (items.size() == 0) return true;
        
        for (IShoppingListItem item : items) {
            if (!item.getChecked()) return true;
        }
        return false;
    }

    public List<IShoppingListItem> getItems() {
        return items;
    }

    public void setItems(List<IShoppingListItem> items) {
        synchronized (this) {
            if (items != null) {
                this.items = items;
            } else {
                this.items.clear();
            }
            setChanged();
            notifyObservers();
        }
    }

    public void addItem(IShoppingListItem item) {
        this.items.add(item);
        setChanged();
        notifyObservers();
    }
    
    //TODO update item + notify observers

    protected static String serializeItems(List<IShoppingListItem> items) {
        JSONArray itemsArray = new JSONArray();
        for (IShoppingListItem listItem : items) {
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
            Log.e("SLItem", "Parsing shopping list items failed" + e.getMessage());
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
            Log.e("Shopping list", "JSON exception " + e.getLocalizedMessage());
        }
        return jsonObject;
    }
}
