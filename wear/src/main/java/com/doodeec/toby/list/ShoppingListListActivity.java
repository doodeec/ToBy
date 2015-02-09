package com.doodeec.toby.list;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import com.doodeec.toby.R;
import com.doodeec.toby.data.Parser;
import com.doodeec.toby.detail.ShoppingListDetailActivity;
import com.doodeec.tobycommon.model.ShoppingList;
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

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListListActivity extends Activity implements WearableListView.ClickListener,
        DataApi.DataListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "WEARABLE_TOBY";

    public static final String LIST_ID_EXTRA = "itemId";
    public static List<ShoppingList> shoppingLists = new ArrayList<>();

    private WearableListView mListView;
    private ShoppingListAdapter mAdapter;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_lists_activity);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        mListView = (WearableListView) findViewById(R.id.list);
        mAdapter = new ShoppingListAdapter(this, shoppingLists);
        mListView.setAdapter(mAdapter);
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
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                Asset shoppingLists = dataMapItem.getDataMap().getAsset(DataSync.SYNC_SHOPPING_LIST);

                InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                        mGoogleApiClient, shoppingLists).await().getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(assetInputStream));
                try {
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }

                    // set lists to adapter
                    final List<ShoppingList> lists = Parser.parseShoppingLists(new JSONArray(total.toString()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShoppingListListActivity.shoppingLists = lists;
                            mAdapter.setLists(lists);
                        }
                    });
                    Log.d(TAG, "Retrieved lists " + lists.toString());
                } catch (Exception e) {
                    Log.e(TAG, "exception " + e.getMessage());
                }
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
}
