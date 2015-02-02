package com.doodeec.toby.views.shoppinglist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodeec.toby.appstate.AppState;
import com.doodeec.toby.objectmodel.ShoppingList;
import com.doodeec.toby.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dusan Bartos on 14.1.2015.
 */
public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListViewHolder> {

    List<ShoppingList> data;

    public ShoppingListsAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<ShoppingList> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addList(ShoppingList list) {
        this.data.add(list);
        notifyDataSetChanged();
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(AppState.getAppContext())
                .inflate(R.layout.shopping_list_holder, parent, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        ShoppingList list = data.get(position);

        holder.setName(list.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
