package com.doodeec.toby.views.shoppingitem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;

/**
 * Created by Dusan Bartos on 17.1.2015.
 */
public class ShoppingItemViewHolder extends RecyclerView.ViewHolder {

    TextView name;

    public ShoppingItemViewHolder(View view) {
        super(view);

        name = (TextView) view.findViewById(R.id.list_name);
    }

    public void setName(String name) {
        this.name.setText(name);
    }
}
