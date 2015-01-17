package com.doodeec.toby.Presentation.ShoppingItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodeec.toby.ApplicationState.AppData;
import com.doodeec.toby.Model.ShoppingListItem;
import com.doodeec.toby.Presentation.FloatingActionButton;
import com.doodeec.toby.R;

/**
 * Created by Dusan Bartos on 17.1.2015.
 */
public class ShoppingItemsListFragment extends Fragment {

    public final static String TAG = "ShoppingItemsList";

    private ShoppingItemAdapter adapter;
    private int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ShoppingItemAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fab_layout, container, false);

        adapter.setData(AppData.getInstance().getShoppingListItems());

        FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppData.getInstance().addShoppingListItem(new ShoppingListItem("Shopping item " + i++));
                adapter.notifyDataSetChanged();
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
