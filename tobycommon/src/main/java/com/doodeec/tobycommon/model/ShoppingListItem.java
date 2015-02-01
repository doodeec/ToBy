package com.doodeec.tobycommon.model;

/**
 * Created by Dusan Bartos on 12.1.2015.
 */
public class ShoppingListItem {

    protected Integer id;
    protected String name;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
