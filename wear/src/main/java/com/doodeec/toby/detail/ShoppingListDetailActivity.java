package com.doodeec.toby.detail;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.view.WearableListView;

import com.doodeec.toby.R;
import com.doodeec.toby.list.ShoppingListListActivity;
import com.doodeec.tobycommon.model.IShoppingListItem;
import com.doodeec.tobycommon.model.ShoppingList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingListDetailActivity extends Activity implements WearableListView.ClickListener, SLItemAdapter.ActionButtonListener {

    private WearableListView mListView;
    private ShoppingList mShoppingList;
    private SLItemAdapter mItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_lists_activity);

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

        mItemsAdapter.notifyDataSetChanged();
        holder.hideActionButtons();
    }

    @Override
    public void onCheckClicked(WearableListView.ViewHolder viewHolder) {
        SLItemViewHolder holder = (SLItemViewHolder) viewHolder;
        int position = holder.getTag();

        mShoppingList.getItems().get(position).setChecked(true);
        reorderItems();

        mItemsAdapter.notifyDataSetChanged();
        holder.hideActionButtons();
    }

    @Override
    protected void onStop() {
        showNotification();
        super.onStop();
    }

    private void reorderItems() {
        Collections.sort(mShoppingList.getItems());
    }

    private void showNotification() {
        Intent actionIntent = new Intent(this, ShoppingListDetailActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
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
//                .setOngoing(true)
                .extend(wearOptions);

        // Build the notification and show it
        notificationManager.notify(123456789, notificationBuilder.build());
    }
}
