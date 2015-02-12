package com.doodeec.toby.views.shoppinglist.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.toby.R;
import com.doodeec.toby.appstate.AppState;
import com.doodeec.tobycommon.model.UnitType;
import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dusan.bartos
 */
public class SLDetailAdapter extends RecyclerView.Adapter<SLDetailViewHolder> {

    List<IShoppingListItem> data;

    public SLDetailAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<IShoppingListItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addList(IShoppingListItem list) {
        this.data.add(list);
        notifyDataSetChanged();
    }

    @Override
    public SLDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SLDetailViewHolder(
                LayoutInflater
                        .from(AppState.getAppContext())
                        .inflate(R.layout.list_item_holder, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(SLDetailViewHolder holder, int position) {
        IShoppingListItem item = data.get(position);

        holder.setName(item.getName());
        holder.setAmount(item.getAmount());
        holder.setChecked(item.getChecked());
        holder.setUnit(item.getUnit() == null ? UnitType.Undefined.resource : item.getUnit().resource);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
