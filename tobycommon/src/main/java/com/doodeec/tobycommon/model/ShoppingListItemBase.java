package com.doodeec.tobycommon.model;

/**
 * @author dusan.bartos
 */
public abstract class ShoppingListItemBase implements IShoppingListItem {
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
