package com.doodeec.toby.views.shoppinglist.detail;

import android.graphics.Paint;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author dusan.bartos
 */
public class SLDetailViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.item_name)
    TextView mName;
    @InjectView(R.id.item_amount)
    TextView mAmount;
    @InjectView(R.id.item_unit)
    TextView mUnit;

    public SLDetailViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }

    public void setName(String name) {
        mName.setText(name);
    }

    public void setAmount(double amount) {
        if (amount > 0) {
            mAmount.setText(String.format("%.1f", amount));
        } else {
            mAmount.setText("");
        }
    }

    public void setUnit(@StringRes int unitResource) {
        mUnit.setText(unitResource);
    }

    public void setChecked(boolean isCompleted) {
        if (isCompleted) {
            mName.setPaintFlags(mName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            mName.setPaintFlags(mName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
