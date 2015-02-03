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
import com.doodeec.tobycommon.model.UnitType;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListListActivity extends Activity implements WearableListView.ClickListener,
        DataApi.DataListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String LIST_ID_EXTRA = "itemId";
    public static List<ShoppingList> shoppingLists;

    private WearableListView mListView;

    private GoogleApiClient mGAClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_lists_activity);

        generateMockData();

        mGAClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        mGAClient.connect();

        mListView = (WearableListView) findViewById(R.id.list);
        mListView.setAdapter(new ShoppingListAdapter(this, shoppingLists));
        mListView.setClickListener(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.DataApi.addListener(mGAClient, this);
        //TODO sync data
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("TOBY", "Connection failed");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("TOBY", "Connection failed");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d("TOBY", "Data changed");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Wearable.DataApi.removeListener(mGAClient, this);
        mGAClient.disconnect();
    }

    private void generateMockData() {
        shoppingLists = new ArrayList<>();
        shoppingLists.add(new ShoppingList("Prvy zoznam"));
        shoppingLists.add(new ShoppingList("Druhy zoznam"));
        shoppingLists.add(new ShoppingList("Treti zoznam"));
        shoppingLists.add(new ShoppingList("Stvrty zoznam"));
        shoppingLists.add(new ShoppingList("Piaty zoznam"));
        shoppingLists.add(new ShoppingList("Siesty zoznam"));

        List<IShoppingListItem> items = new ArrayList<>();

        IShoppingListItem item = new ShoppingListItem("Prva polozka");
        item.setAmount(4);
        item.setUnitType(UnitType.Units);
        IShoppingListItem item2 = new ShoppingListItem("Druha polozka");
        item2.setAmount(10);
        item2.setUnitType(UnitType.Units);
        IShoppingListItem item3 = new ShoppingListItem("Tretia polozka");
        item2.setAmount(5);
        item2.setUnitType(UnitType.Liter);
        IShoppingListItem item4 = new ShoppingListItem("Stvrta polozka");
        item2.setAmount(1);
        item2.setUnitType(UnitType.Kilo);
        IShoppingListItem item5 = new ShoppingListItem("Piata polozka");
        item2.setAmount(10);
        item2.setUnitType(UnitType.Units);

        items.add(item);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        shoppingLists.get(0).setItems(items);
        shoppingLists.get(1).setItems(items);
        shoppingLists.get(2).setItems(items);
        shoppingLists.get(3).setItems(items);
        shoppingLists.get(4).setItems(items);
        shoppingLists.get(5).setItems(items);

    }
}
