package com.doodeec.toby.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import com.doodeec.toby.R;
import com.doodeec.toby.detail.ShoppingListDetailActivity;
import com.doodeec.tobycommon.model.IShoppingListItem;
import com.doodeec.tobycommon.model.ShoppingList;
import com.doodeec.tobycommon.model.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListListActivity extends Activity implements WearableListView.ClickListener {

    private WearableListView mListView;
    public static List<ShoppingList> shoppingLists;
    public static final String LIST_ID_EXTRA = "itemId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_lists_activity);

        shoppingLists = new ArrayList<>();
        shoppingLists.add(new ShoppingList("Prvy zoznam"));
        shoppingLists.add(new ShoppingList("Druhy zoznam"));
        shoppingLists.add(new ShoppingList("Treti zoznam"));
        shoppingLists.add(new ShoppingList("Stvrty zoznam"));
        shoppingLists.add(new ShoppingList("Piaty zoznam"));
        shoppingLists.add(new ShoppingList("Siesty zoznam"));

        List<IShoppingListItem> items = new ArrayList<>();
        items.add(new ShoppingListItem("Prva polozka"));
        items.add(new ShoppingListItem("Druha polozka"));
        items.add(new ShoppingListItem("Tretia polozka"));
        items.add(new ShoppingListItem("Stvrta polozka"));
        items.add(new ShoppingListItem("Piata polozka"));
        items.add(new ShoppingListItem("Siesta polozka"));
        shoppingLists.get(0).setItems(items);

        mListView = (WearableListView) findViewById(R.id.list);
        mListView.setAdapter(new ShoppingListWearableAdapter(this, shoppingLists));
        mListView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Log.d("WEARABLE_TOBY", "shoppingList list item clicked");
        Intent detailIntent = new Intent(this, ShoppingListDetailActivity.class);
        detailIntent.putExtra(LIST_ID_EXTRA, ((ShoppingListViewHolder) viewHolder).getTag());
        startActivity(detailIntent);
    }

    @Override
    public void onTopEmptyRegionClick() {
        //do nothing
    }
}
