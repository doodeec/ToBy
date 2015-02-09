package com.doodeec.toby;

import android.util.Log;

import com.doodeec.toby.appstate.AppData;
import com.doodeec.toby.objectmodel.ShoppingList;
import com.doodeec.tobycommon.sync.DataSync;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author dusan.bartos
 */
public class WearService extends WearableListenerService {

    private static final String TAG = "TOBY_SERVICE";

    GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "WEAR create");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "WEAR Data changed " + dataEvents);
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                Asset shoppingList = dataMapItem.getDataMap().getAsset(DataSync.SYNC_SHOPPING_LIST);

                InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                        mGoogleApiClient, shoppingList).await().getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(assetInputStream));
                try {
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }

                    // parse and update
                    ShoppingList shList = new ShoppingList(new JSONObject(total.toString()));
                    for (ShoppingList list: AppData.getInstance().getShoppingLists()) {
                        if (list.getId().equals(shList.getId())) list.updateFrom(shList);
                    }
                    Log.d(TAG, "Retrieved list " + total.toString());
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
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "WEAR Message " + messageEvent);

        switch (messageEvent.getPath()) {
            case DataSync.SYNC_REQUEST_PATH:
                sendData();
                break;

            default:
                break;
        }
    }

    @Override
    public void onPeerConnected(Node peer) {
        Log.d(TAG, "WEAR Connected " + peer);
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        Log.d(TAG, "WEAR Disconnected " + peer);
    }

    public void sendData() {
        String lists = AppData.getInstance().getShoppingListsAsJSON().toString();
        Asset shoppingListsAsset = Asset.createFromBytes(lists.getBytes());

        PutDataMapRequest dataMap = PutDataMapRequest.create(DataSync.SYNC_LIST_DATA);
        dataMap.getDataMap().putAsset(DataSync.SYNC_SHOPPING_LIST, shoppingListsAsset);
        PutDataRequest request = dataMap.asPutDataRequest();
        Log.d("TOBY", "Send lists to wearable " + lists);
        Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {
                        Log.d("TOBY", "Sending shopping lists successful: " + dataItemResult.getStatus()
                                .isSuccess());
                    }
                });
    }
}
