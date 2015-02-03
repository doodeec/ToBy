package com.doodeec.toby.list;

import android.graphics.Paint;
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

    public void setCompleted(boolean isCompleted) {
        if (isCompleted) {
            mName.setPaintFlags(mName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            mName.setPaintFlags(mName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public void setTag(Integer tag) {
        itemView.setTag(tag);
    }

    public Integer getTag() {
        return (Integer) itemView.getTag();
    }
}
