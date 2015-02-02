package com.doodeec.toby.views.shoppingitem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;
import com.doodeec.tobycommon.model.UnitType;

/**
 * @author dusan.bartos
 */
public class SLItemViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView amount;
    TextView unit;

    public SLItemViewHolder(View view) {
        super(view);

        name = (TextView) view.findViewById(R.id.item_name);
        amount = (TextView) view.findViewById(R.id.item_amount);
        unit = (TextView) view.findViewById(R.id.item_unit);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setUnitAmount(double amount, UnitType unitType) {
        this.amount.setText(String.format("%.1f", amount));
        this.unit.setText(unitType.resource);
    }
}
