package com.doodeec.toby;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.tobycommon.model.ShoppingListItem;

import java.util.List;

/**
 * Created by Dusan Bartos on 1.2.2015.
 */
public class ShoppingListItemWearableAdapter extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    private List<ShoppingListItem> mShoppingListItems;

    public ShoppingListItemWearableAdapter(Context context, List<ShoppingListItem> list) {
        mInflater = LayoutInflater.from(context);
        mShoppingListItems = list;
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
    }

    @Override
    public int getItemCount() {
        return mShoppingListItems.size();
    }
}
