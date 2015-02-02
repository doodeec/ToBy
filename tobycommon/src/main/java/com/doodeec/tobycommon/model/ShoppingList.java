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
        //TODO fill string
        return "";
    }

    protected static List<IShoppingListItem> deserializeItems(String serializedItems) {
        List<IShoppingListItem> items = new ArrayList<>();
        //TODO fill list
        return items;
    }
}
