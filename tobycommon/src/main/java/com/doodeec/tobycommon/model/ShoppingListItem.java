package com.doodeec.tobycommon.model;

/**
 * @author dusan.bartos
 */
public class ShoppingListItem extends ShoppingListItemBase {

    protected boolean checked = false;
    protected double amount;
    protected UnitType unit;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String name) {
        this.name = name;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public double getAmount() {
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

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean getChecked() {
        return checked;
    }

    @Override
    public int compareTo(IShoppingListItem another) {
        if (checked) {
            return 1;
        } else if (another.getChecked()) {
            return -1;
        } else {
            return 0;
        }
    }
}
