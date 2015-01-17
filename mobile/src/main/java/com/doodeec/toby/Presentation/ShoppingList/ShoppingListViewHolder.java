package com.doodeec.toby.Presentation.ShoppingList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;

/**
 * Created by Dusan Bartos on 14.1.2015.
 */
public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

    TextView name;

    public ShoppingListViewHolder(View view) {
        super(view);

        name = (TextView) view.findViewById(R.id.list_name);
    }

    public void setName(String name) {
        this.name.setText(name);
    }
}
