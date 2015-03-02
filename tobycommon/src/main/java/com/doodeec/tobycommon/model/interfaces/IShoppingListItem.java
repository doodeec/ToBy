package com.doodeec.tobycommon.model.interfaces;

import com.doodeec.tobycommon.model.UnitType;

import org.json.JSONObject;

/**
 * @author dusan.bartos
 */
public interface IShoppingListItem extends IBaseDBObject, Comparable<IShoppingListItem> {
    /**
     * Sets amount of measurement units of this item to buy
     *
     * @param amount amount to buy
     */
    void setAmount(double amount);

    /**
     * Gets amount of measurement units of this item
     *
     * @return amount
     */
    double getAmount();

    /**
     * Sets item's measurement unit
     *
     * @param type unit type
     */
    void setUnitType(UnitType type);

    /**
     * Gets item's measurement unit
     *
     * @return unit
     */
    UnitType getUnit();

    /**
     * Set item as checked/bought/picked
     *
     * @param isChecked is checked
     */
    void setChecked(boolean isChecked);

    /**
     * Is item checked in the cart
     *
     * @return true if item is already checked
     */
    boolean getChecked();

    JSONObject toJSON();
}
