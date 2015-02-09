package com.doodeec.toby.views.shoppinglist.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;

/**
 * @author dusan.bartos
 */
public class SLDetailViewHolder extends RecyclerView.ViewHolder {

    TextView name;

    public SLDetailViewHolder(View view) {
        super(view);

        //TODO butterknife

        name = (TextView) view.findViewById(R.id.list_name);
    }

    public void setName(String name) {
        this.name.setText(name);
    }
}
