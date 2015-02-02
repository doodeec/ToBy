package com.doodeec.tobycommon.model;

/**
 * @author dusan.bartos
 */
public interface IShoppingListItem extends IBaseDBObject {
    void setAmount(double amount);

    double getAmount();

    void setUnitType(UnitType type);

    UnitType getUnit();
}
