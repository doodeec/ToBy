package com.doodeec.toby.detail;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.toby.R;
import com.doodeec.tobycommon.model.IShoppingListItem;
import com.doodeec.tobycommon.model.UnitType;

import java.util.List;

/**
 * @author dusan.bartos
 */
public class SLItemAdapter extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    private List<IShoppingListItem> mShoppingListItems;
    private ActionButtonListener mListener;

    public SLItemAdapter(Context context, List<IShoppingListItem> list) {
        mInflater = LayoutInflater.from(context);
        mShoppingListItems = list;
    }

    public void setActionButtonListener(ActionButtonListener listener) {
        mListener = listener;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SLItemViewHolder(mInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        IShoppingListItem item = mShoppingListItems.get(position);
        SLItemViewHolder viewHolder = (SLItemViewHolder) holder;

        viewHolder.setName(item.getName());
        viewHolder.setAmount(item.getAmount());
        viewHolder.setChecked(item.getChecked());
        viewHolder.setUnit(item.getUnit() == null ? UnitType.Undefined.resource : item.getUnit().resource);
        viewHolder.setTag(position);
        viewHolder.setActionBtnListener(mListener);
    }

    @Override
    public int getItemCount() {
        return mShoppingListItems.size();
    }

    public interface ActionButtonListener {
        void onDeleteClicked(WearableListView.ViewHolder viewHolder);

        void onCheckClicked(WearableListView.ViewHolder viewHolder, boolean checked);
    }
}
