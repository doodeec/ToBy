package com.doodeec.tobycommon.model.interfaces;

/**
 * @author dusan.bartos
 */
public interface IShop extends IBaseDBObject {

    IShopCategory getCategory();

    void setCategory(IShopCategory category);
}
