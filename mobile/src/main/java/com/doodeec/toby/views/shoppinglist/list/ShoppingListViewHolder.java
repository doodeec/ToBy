package com.doodeec.toby.views.shoppinglist.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author dusan.bartos
 */
public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.list_name)
    TextView name;
    @InjectView(R.id.shop_name)
    TextView shopName;

    public ShoppingListViewHolder(View view) {
        super(view);

        ButterKnife.inject(this, view);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setShopName(String name) {
        this.shopName.setText(name);
    }
}
