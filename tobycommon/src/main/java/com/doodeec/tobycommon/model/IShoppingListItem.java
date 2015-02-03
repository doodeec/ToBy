package com.doodeec.tobycommon.model;

/**
 * @author dusan.bartos
 */
public interface IShoppingListItem extends IBaseDBObject, Comparable<IShoppingListItem> {
    void setAmount(double amount);

    double getAmount();

    void setUnitType(UnitType type);

    UnitType getUnit();

    void setChecked(boolean isChecked);

    boolean getChecked();
}
