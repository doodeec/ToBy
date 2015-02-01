package com.doodeec.tobycommon.model;

/**
 * Shop Category object
 *
 * @author Dusan Bartos
 */
public class ShopCategory {

    protected Integer id;
    protected String name;
    protected Integer usage;

    protected ShopCategory() {
    }

    public ShopCategory(String name) {
        this.name = name;
        this.usage = 0;
    }

    public Integer getId() {
        return id;
    }
}
