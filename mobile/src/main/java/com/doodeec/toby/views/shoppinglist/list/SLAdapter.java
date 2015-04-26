package com.doodeec.toby.views.shoppinglist.list;

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
 * @author dusan.bartos
 */
public class SLAdapter extends RecyclerView.Adapter<SLViewHolder> {

    List<ShoppingList> data;

    public SLAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<ShoppingList> data) {
        this.data.clear();
        if (data == null) {
            notifyDataSetChanged();
            return;
        }

        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addList(ShoppingList list) {
        this.data.add(list);
        notifyDataSetChanged();
    }

    @Override
    public SLViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(AppState.getAppContext())
                .inflate(R.layout.shopping_list_holder, parent, false);
        return new SLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SLViewHolder holder, int position) {
        ShoppingList list = data.get(position);

        holder.setName(list.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
