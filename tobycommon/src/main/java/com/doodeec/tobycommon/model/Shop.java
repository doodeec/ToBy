package com.doodeec.tobycommon.model;

/**
 * Shop object
 *
 * @author Dusan Bartos
 */
public class Shop implements IShop {

    protected Integer id;
    protected String name;
    protected IShopCategory category;

    public Shop() {

    }

    public Shop(String name) {
        this.name = name;
    }

    public Shop(String name, IShopCategory category) {
        this.name = name;
        this.category = category;
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

    @Override
    public IShopCategory getCategory() {
        return category;
    }
}
