package com.doodeec.tobycommon.model;

/**
 * @author dusan.bartos
 */
public class ShoppingListItem extends ShoppingListItemBase {

    protected Integer amount;
    protected UnitType unit;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String name) {
        this.name = name;
    }

    @Override
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public Integer getAmount() {
        return amount;
    }

    @Override
    public void setUnitType(UnitType type) {
        this.unit = type;
    }

    @Override
    public UnitType getUnit() {
        return unit;
    }
}
