package com.doodeec.tobycommon.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Shopping list object
 *
 * @author Dusan Bartos
 */
public class ShoppingList {

    protected Integer id;
    protected ShopCategory category;
    protected Shop shop;
    protected String name;
    protected Boolean completed = false;
    protected List<ShoppingListItem> items = new ArrayList<>();
    protected Date created;
    protected Date edited;
    protected Date dueDate;
    protected Date dateCompleted;

    protected ShoppingList() {

    }

    public ShoppingList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingListItem> items) {
        if (items != null) {
            this.items = items;
        } else {
            this.items.clear();
        }
    }

    public void addItem(ShoppingListItem item) {
        this.items.add(item);
    }

    protected static String serializeItems(List<ShoppingListItem> items) {
        //TODO fill string
        return "";
    }

    protected static List<ShoppingListItem> deserializeItems(String serializedItems) {
        List<ShoppingListItem> items = new ArrayList<>();
        //TODO fill list
        return items;
    }
}
