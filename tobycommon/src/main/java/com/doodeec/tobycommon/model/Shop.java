package com.doodeec.tobycommon.model;

/**
 * Shop object
 *
 * @author Dusan Bartos
 */
public class Shop {

    protected Integer id;
    protected String name;
    protected ShopCategory category;

    public Shop() {

    }

    public Shop(String name) {
        this.name = name;
    }

    public Shop(String name, ShopCategory category) {
        this.name = name;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }
}
