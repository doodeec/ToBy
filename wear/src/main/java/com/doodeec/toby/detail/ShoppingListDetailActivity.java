package com.doodeec.toby.detail;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import com.doodeec.toby.R;
import com.doodeec.toby.list.ShoppingListListActivity;
import com.doodeec.tobycommon.model.ShoppingList;
import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;
import com.doodeec.tobycommon.sync.DataSync;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingListDetailActivity extends Activity implements WearableListView.ClickListener,
        SLItemAdapter.ActionButtonListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "WEARABLE_TOBY";
    private static int i = 0;

    private GoogleApiClient mGoogleApiClient;
    private WearableListView mListView;
    private ShoppingList mShoppingList;
    private SLItemAdapter mItemsAdapter;
    private NotificationManagerCompat mNotificationManager;
    private int mShoppingListNotificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_lists_activity);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        List<IShoppingListItem> items;
        if (getIntent().getExtras() != null) {
            Integer position = getIntent().getExtras().getInt(ShoppingListListActivity.LIST_ID_EXTRA);
            mShoppingList = ShoppingListListActivity.shoppingLists.get(position);
            items = mShoppingList.getItems();
        } else {
            //TODO notification/warning
            items = new ArrayList<>();
        }

        mListView = (WearableListView) findViewById(R.id.list);
        mItemsAdapter = new SLItemAdapter(this, items);
        mItemsAdapter.setActionButtonListener(this);
        mListView.setAdapter(mItemsAdapter);
        mListView.setClickListener(this);

        mNotificationManager = NotificationManagerCompat.from(this);
        mShoppingListNotificationId = ++i;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Connecting");
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        if (mGoogleApiClient.isConnected()) {
            String list = mShoppingList.toJSON().toString();
            Asset shoppingListAsset = Asset.createFromBytes(list.getBytes());

            PutDataMapRequest dataMap = PutDataMapRequest.create(DataSync.SYNC_SYNC_LIST);
            dataMap.getDataMap().putAsset(DataSync.SYNC_SHOPPING_LIST, shoppingListAsset);
            Log.d(TAG, "Sync list to device " + list);
            Wearable.DataApi.putDataItem(mGoogleApiClient, dataMap.asPutDataRequest());
        }

        Log.d(TAG, "Disconnecting");
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Connected");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        ((SLItemViewHolder) viewHolder).showActionButtons();
    }

    @Override
    public void onTopEmptyRegionClick() {
        //do nothing
    }

    @Override
    public void onDeleteClicked(WearableListView.ViewHolder viewHolder) {
        SLItemViewHolder holder = (SLItemViewHolder) viewHolder;
        int position = holder.getTag();

        mShoppingList.getItems().remove(position);

        // close detail if no item left
        if (mShoppingList.getItems().size() == 0) {
            finish();
        }

        mItemsAdapter.notifyDataSetChanged();
        holder.hideActionButtons();
    }

    @Override
    public void onCheckClicked(WearableListView.ViewHolder viewHolder, boolean checked) {
        SLItemViewHolder holder = (SLItemViewHolder) viewHolder;
        int position = holder.getTag();

        mShoppingList.getItems().get(position).setChecked(!checked);
        reorderItems();

        // close detail if all items checked
        if (!mShoppingList.hasActiveItems()) {
            finish();
        }

        mItemsAdapter.notifyDataSetChanged();
        holder.hideActionButtons();
    }

    @Override
    protected void onStop() {
        showNotification();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        hideNotification();
        super.onDestroy();
    }

    private void reorderItems() {
        Collections.sort(mShoppingList.getItems());
    }

    private void hideNotification() {
        mNotificationManager.cancel(mShoppingListNotificationId);
    }

    private void showNotification() {
        Intent actionIntent = new Intent(this, ShoppingListDetailActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action continueAction = new NotificationCompat.Action.Builder(R.drawable.ic_done_white_48dp,
                "Continue shopping", actionPendingIntent)
                .build();

        NotificationCompat.WearableExtender wearOptions = new NotificationCompat.WearableExtender();
        wearOptions.setContentIcon(R.drawable.ic_launcher)
                .addAction(continueAction);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(mShoppingList.getName())
                .setOngoing(mShoppingList.hasActiveItems())
                .extend(wearOptions);

        // Build the notification and show it
        mNotificationManager.notify(mShoppingListNotificationId, notificationBuilder.build());
    }
}
