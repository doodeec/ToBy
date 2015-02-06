package com.doodeec.toby.list;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import com.doodeec.toby.R;
import com.doodeec.toby.detail.ShoppingListDetailActivity;
import com.doodeec.tobycommon.model.IShoppingListItem;
import com.doodeec.tobycommon.model.ShoppingList;
import com.doodeec.tobycommon.model.ShoppingListItem;
import com.doodeec.tobycommon.model.UnitType;
import com.doodeec.tobycommon.sync.DataSync;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListListActivity extends Activity implements WearableListView.ClickListener,
        DataApi.DataListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "WEARABLE_TOBY";

    public static final String LIST_ID_EXTRA = "itemId";
    public static List<ShoppingList> shoppingLists;

    private WearableListView mListView;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_lists_activity);

        generateMockData();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        mListView = (WearableListView) findViewById(R.id.list);
        mListView.setAdapter(new ShoppingListAdapter(this, shoppingLists));
        mListView.setClickListener(this);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Connecting");
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Disconnecting");
        super.onPause();
        Wearable.DataApi.removeListener(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Connected");
        Wearable.DataApi.addListener(mGoogleApiClient, this);

        // request data sync
        new SyncDataTask().execute();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged");
        Log.d(TAG, "data buffer: " + dataEvents);

        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                Asset word = dataMapItem.getDataMap()
                        .getAsset("word");

                InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                        mGoogleApiClient, word).await().getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(assetInputStream));
                StringBuilder total = new StringBuilder();
                try {
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "exception");
                }

                Log.d(TAG, "Result data " + total.toString());
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                Log.d(TAG, "DataItem Deleted " + event.getDataItem().toString());
            } else {
                Log.d(TAG, "Unknown data event type = " + event.getType());
            }
        }
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Log.d(TAG, "shoppingList list item clicked");
        Intent detailIntent = new Intent(this, ShoppingListDetailActivity.class);
        detailIntent.putExtra(LIST_ID_EXTRA, ((ShoppingListViewHolder) viewHolder).getTag());
        startActivity(detailIntent);
    }

    @Override
    public void onTopEmptyRegionClick() {
        //do nothing
    }

    /**
     * Async task which requests data sync from device using message api
     */
    class SyncDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            for (Node node: getNodes()) {
                sendDataSyncRequestToNode(node);
            }
            return null;
        }

        private List<Node> getNodes() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            return nodes.getNodes();
        }

        private void sendDataSyncRequestToNode(Node node) {
            Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(),
                    DataSync.SYNC_REQUEST_PATH, new byte[0])
                    .setResultCallback(
                            new ResultCallback<MessageApi.SendMessageResult>() {
                                @Override
                                public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                    if (!sendMessageResult.getStatus().isSuccess()) {
                                        Log.e(TAG, "Failed to send message with status code: "
                                                + sendMessageResult.getStatus().getStatusCode());
                                    } else {
                                        Log.d(TAG, "Sync request was sent");
                                    }
                                }
                            }
                    );
        }
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
