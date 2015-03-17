package com.doodeec.tobycommon.model;

import com.doodeec.tobycommon.model.interfaces.IShopCategory;

/**
 * Shop Category object
 *
 * @author Dusan Bartos
 */
public class ShopCategory implements IShopCategory {

    protected Integer id = -1;
    protected String name;
    protected Integer usage;

    protected ShopCategory() {
    }

    public ShopCategory(String name) {
        this.name = name;
        this.usage = 0;
    }

    public ShopCategory(String name, int id) {
        this(name);
        this.id = id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
