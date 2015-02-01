package com.doodeec.toby.detail;

import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;
import com.doodeec.toby.ui.WearableListItemLayout;

/**
 * Created by Dusan Bartos on 1.2.2015.
 */
public class SLItemViewHolder extends WearableListView.ViewHolder {

    TextView mName;
    WearableListItemLayout mItemLayout;

    public SLItemViewHolder(View v) {
        super(v);
        mItemLayout = (WearableListItemLayout) v;
        mName = (TextView) v.findViewById(R.id.name);
    }

    public void setName(String name) {
        mName.setText(name);
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
