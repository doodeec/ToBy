package com.doodeec.toby.views.shoppinglist.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodeec.toby.R;
import com.doodeec.toby.appstate.AppState;
import com.doodeec.toby.objectmodel.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dusan.bartos
 */
public class SLDetailAdapter extends RecyclerView.Adapter<SLDetailViewHolder> {

    List<ShoppingListItem> data;

    public SLDetailAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<ShoppingListItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addList(ShoppingListItem list) {
        this.data.add(list);
        notifyDataSetChanged();
    }

    @Override
    public SLDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(AppState.getAppContext())
                .inflate(R.layout.shopping_list_holder, parent, false);
        return new SLDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SLDetailViewHolder holder, int position) {
        ShoppingListItem item = data.get(position);

        holder.setName(item.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
