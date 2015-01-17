package com.doodeec.toby.Presentation.ShoppingItem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodeec.toby.ApplicationState.AppState;
import com.doodeec.toby.Model.ShoppingListItem;
import com.doodeec.toby.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dusan Bartos on 17.1.2015.
 */
public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemViewHolder> {

        List<ShoppingListItem> data;

        public ShoppingItemAdapter() {
            data = new ArrayList<>();
        }

        public void setData(List<ShoppingListItem> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        public void addItem(ShoppingListItem item) {
            this.data.add(item);
            notifyDataSetChanged();
        }

        @Override
        public ShoppingItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(AppState.getAppContext())
                    .inflate(R.layout.shopping_list_holder, parent, false);
            return new ShoppingItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ShoppingItemViewHolder holder, int position) {
            ShoppingListItem item = data.get(position);

            holder.setName(item.getName());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
}
