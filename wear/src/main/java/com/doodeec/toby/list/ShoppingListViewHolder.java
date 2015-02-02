package com.doodeec.toby.list;

import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;

/**
 * @author dusan.bartos
 */
public class ShoppingListViewHolder extends WearableListView.ViewHolder {

    TextView mName;

    public ShoppingListViewHolder(View v) {
        super(v);

        mName = (TextView) v.findViewById(R.id.name);
    }

    public void setName(String name) {
        mName.setText(name);
    }

    public void setTag(Integer tag) {
        itemView.setTag(tag);
    }

    public Integer getTag() {
        return (Integer) itemView.getTag();
    }
}
