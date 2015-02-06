package com.doodeec.tobycommon.model.interfaces;

import com.doodeec.tobycommon.model.UnitType;

import org.json.JSONObject;

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

    JSONObject toJSON();
}
