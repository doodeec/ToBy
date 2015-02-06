package com.doodeec.tobycommon.model;

import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;

/**
 * @author dusan.bartos
 */
public abstract class ShoppingListItemBase implements IShoppingListItem {
    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";

    protected Integer id;
    protected String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
