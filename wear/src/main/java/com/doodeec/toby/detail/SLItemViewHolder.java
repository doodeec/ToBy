package com.doodeec.toby.detail;

import android.support.annotation.StringRes;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;
import com.doodeec.toby.ui.SLItemLayout;

/**
 * @author dusan.bartos
 */
public class SLItemViewHolder extends WearableListView.ViewHolder {

    TextView mName;
    TextView mAmount;
    TextView mUnit;
    SLItemLayout mItemLayout;

    public SLItemViewHolder(View v) {
        super(v);
        mItemLayout = (SLItemLayout) v;
        mName = (TextView) v.findViewById(R.id.name);
        mAmount = (TextView) v.findViewById(R.id.amount);
        mUnit = (TextView) v.findViewById(R.id.unit);
    }

    public void setName(String name) {
        mName.setText(name);
    }

    public void setAmount(double amount) {
        mAmount.setText(String.format("%.1f", amount));
    }

    public void setUnit(@StringRes int unitResource) {
        mUnit.setText(unitResource);
    }

    public void setTag(Integer tag) {
        itemView.setTag(tag);
    }

    public Integer getTag() {
        return (Integer) itemView.getTag();
    }

    public void showActionButtons() {
        mItemLayout.setActionButtonsVisible(true);
    }

    public void hideActionButtons() {
        mItemLayout.setActionButtonsVisible(false);
    }

    public void setActionBtnListener(final ShoppingListItemWearableAdapter.ActionButtonListener listener) {
        mItemLayout.setActionButtonListener(new ShoppingListItemWearableAdapter.ActionButtonListener() {
            @Override
            public void onDeleteClicked(WearableListView.ViewHolder viewHolder) {
                listener.onDeleteClicked(SLItemViewHolder.this);
            }

            @Override
            public void onCheckClicked(WearableListView.ViewHolder viewHolder) {
                listener.onCheckClicked(SLItemViewHolder.this);
            }
        });
    }
}
