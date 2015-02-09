package com.doodeec.toby.views.shoppinglist.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;

/**
 * @author dusan.bartos
 */
public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView shopName;

    public ShoppingListViewHolder(View view) {
        super(view);

        //TODO butterknife

        name = (TextView) view.findViewById(R.id.list_name);
        shopName = (TextView) view.findViewById(R.id.shop_name);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setShopName(String name) {
        this.shopName.setText(name);
    }
}
