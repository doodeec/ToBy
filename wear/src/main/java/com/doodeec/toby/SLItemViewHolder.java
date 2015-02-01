package com.doodeec.toby;

import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Dusan Bartos on 1.2.2015.
 */
public class SLItemViewHolder extends WearableListView.ViewHolder {

    TextView mName;

    public SLItemViewHolder(View v) {
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
