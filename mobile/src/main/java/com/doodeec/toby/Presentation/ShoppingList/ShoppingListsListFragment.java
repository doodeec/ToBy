package com.doodeec.toby.Presentation.ShoppingList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodeec.toby.ApplicationState.AppData;
import com.doodeec.toby.Model.ShoppingList;
import com.doodeec.toby.Presentation.FloatingActionButton;
import com.doodeec.toby.R;

public class ShoppingListsListFragment extends Fragment {

    public final static String TAG = "ShoppingListsList";

    private ShoppingListsAdapter adapter;
    private int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ShoppingListsAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fab_layout, container, false);

        adapter.setData(AppData.getInstance().getShoppingLists());

        FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppData.getInstance().addShoppingList(new ShoppingList("Shopping list " + i++));
                adapter.notifyDataSetChanged();
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
