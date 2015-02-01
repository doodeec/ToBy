package com.doodeec.toby;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import com.doodeec.tobycommon.model.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDetailActivity extends Activity implements WearableListView.ClickListener {

    private WearableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_lists_activity);

        List<ShoppingListItem> items;
        if (getIntent().getExtras() != null) {
            Integer position = getIntent().getExtras().getInt(ShoppingListListActivity.LIST_ID_EXTRA);
            items = ShoppingListListActivity.shoppingLists.get(position).getItems();
        } else {
            items = new ArrayList<>();
        }

        mListView = (WearableListView) findViewById(R.id.list);
        mListView.setAdapter(new ShoppingListItemWearableAdapter(this, items));
        mListView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Log.d("WEARABLE_TOBY", "item clicked");
    }

    @Override
    public void onTopEmptyRegionClick() {
        //do nothing
    }
}
