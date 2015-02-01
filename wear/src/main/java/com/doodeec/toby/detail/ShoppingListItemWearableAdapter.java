package com.doodeec.toby.detail;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.toby.R;
import com.doodeec.tobycommon.model.ShoppingListItem;

import java.util.List;

/**
 * Created by Dusan Bartos on 1.2.2015.
 */
public class ShoppingListItemWearableAdapter extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    private List<ShoppingListItem> mShoppingListItems;
    private ActionButtonListener mListener;

    public ShoppingListItemWearableAdapter(Context context, List<ShoppingListItem> list) {
        mInflater = LayoutInflater.from(context);
        mShoppingListItems = list;
    }

    public void setActionButtonListener(ActionButtonListener listener) {
        mListener = listener;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SLItemViewHolder(mInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ShoppingListItem item = mShoppingListItems.get(position);
        SLItemViewHolder viewHolder = (SLItemViewHolder) holder;

        viewHolder.setName(item.getName());
        viewHolder.setTag(position);
        viewHolder.setActionBtnListener(mListener);
    }

    @Override
    public int getItemCount() {
        return mShoppingListItems.size();
    }

    public interface ActionButtonListener {
        void onDeleteClicked(WearableListView.ViewHolder viewHolder);

        void onCheckClicked(WearableListView.ViewHolder viewHolder);
    }
}
