package com.doodeec.toby.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import com.doodeec.toby.R;
import com.doodeec.toby.list.ShoppingListListActivity;
import com.doodeec.tobycommon.model.IShoppingListItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDetailActivity extends Activity implements WearableListView.ClickListener, ShoppingListItemWearableAdapter.ActionButtonListener {

    private WearableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_lists_activity);

        List<IShoppingListItem> items;
        if (getIntent().getExtras() != null) {
            Integer position = getIntent().getExtras().getInt(ShoppingListListActivity.LIST_ID_EXTRA);
            items = ShoppingListListActivity.shoppingLists.get(position).getItems();
        } else {
            items = new ArrayList<>();
        }

        mListView = (WearableListView) findViewById(R.id.list);
        ShoppingListItemWearableAdapter itemsAdapter = new ShoppingListItemWearableAdapter(this, items);
        itemsAdapter.setActionButtonListener(this);
        mListView.setAdapter(itemsAdapter);
        mListView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Log.d("WEARABLE_TOBY", "item clicked");

        ((SLItemViewHolder) viewHolder).showActionButtons();
    }

    @Override
    public void onTopEmptyRegionClick() {
        //do nothing
    }

    @Override
    public void onDeleteClicked(WearableListView.ViewHolder viewHolder) {
        ((SLItemViewHolder) viewHolder).hideActionButtons();
    }

    @Override
    public void onCheckClicked(WearableListView.ViewHolder viewHolder) {
        ((SLItemViewHolder) viewHolder).hideActionButtons();
    }
}
