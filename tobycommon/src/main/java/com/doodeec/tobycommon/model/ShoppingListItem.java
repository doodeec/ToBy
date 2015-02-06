package com.doodeec.tobycommon.model;

import android.util.Log;

import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author dusan.bartos
 */
public class ShoppingListItem extends ShoppingListItemBase {

    protected static final String KEY_CHECKED = "checked";
    protected static final String KEY_AMOUNT = "amount";
    protected static final String KEY_UNIT = "unit";

    protected boolean checked = false;
    protected double amount;
    protected UnitType unit;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String name) {
        this.name = name;
    }

    public ShoppingListItem(JSONObject object) {
        try {
            if (object.has(KEY_ID)) {
                //TODO generate key before data sync
                id = object.getInt(KEY_ID);
            }
            name = object.getString(KEY_NAME);
            checked = object.getBoolean(KEY_CHECKED);
            amount = object.getDouble(KEY_AMOUNT);
            unit = UnitType.forTypeKey(object.getString(KEY_UNIT));
        } catch (JSONException e) {
            Log.e("Shopping list item JSON exception", e.getMessage());
        }
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

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, id);
            jsonObject.put(KEY_NAME, name);
            jsonObject.put(KEY_CHECKED, checked);
            jsonObject.put(KEY_AMOUNT, amount);
            jsonObject.put(KEY_UNIT, unit != null ? unit.typeKey : "");
        } catch (JSONException e) {
            Log.e("Shopping list item JSON exception", e.getLocalizedMessage());
        }
        return jsonObject;
    }
}
