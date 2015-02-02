package com.doodeec.tobycommon.model;

/**
 * @author dusan.bartos
 */
public interface IShoppingListItem extends IBaseDBObject{
    void setAmount(Integer amount);

    Integer getAmount();

    void setUnitType(UnitType type);

    UnitType getUnit();
}
