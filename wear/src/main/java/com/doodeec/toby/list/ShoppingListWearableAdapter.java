package com.doodeec.toby.list;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.toby.R;
import com.doodeec.tobycommon.model.ShoppingList;

import java.util.List;

/**
 * Created by Dusan Bartos on 1.2.2015.
 */
public class ShoppingListWearableAdapter extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    private List<ShoppingList> mShoppingListList;

    public ShoppingListWearableAdapter(Context context, List<ShoppingList> list) {
        mInflater = LayoutInflater.from(context);
        mShoppingListList = list;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShoppingListViewHolder(mInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ShoppingList shoppingList = mShoppingListList.get(position);
        ShoppingListViewHolder viewHolder = (ShoppingListViewHolder) holder;

        viewHolder.setName(shoppingList.getName());
        viewHolder.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mShoppingListList.size();
    }
}
