package com.doodeec.toby.list;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.toby.R;
import com.doodeec.tobycommon.model.ShoppingList;

import java.util.List;

/**
 * @author dusan.bartos
 */
public class ShoppingListAdapter extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    private List<ShoppingList> mShoppingListList;

    public ShoppingListAdapter(Context context, List<ShoppingList> list) {
        mInflater = LayoutInflater.from(context);
        mShoppingListList = list;
    }

    public void setLists(List<ShoppingList> list) {
        mShoppingListList = list;
        notifyDataSetChanged();
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShoppingListViewHolder(mInflater.inflate(R.layout.shopping_list, parent, false));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ShoppingList shoppingList = mShoppingListList.get(position);
        ShoppingListViewHolder viewHolder = (ShoppingListViewHolder) holder;

        viewHolder.setName(shoppingList.getName());
        viewHolder.setCompleted(!shoppingList.hasActiveItems());
        viewHolder.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mShoppingListList.size();
    }
}
