package com.doodeec.toby.Presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doodeec.toby.Model.ShoppingList;
import com.doodeec.toby.R;

public class ShoppingListsListFragment extends Fragment {

    public final static String TAG = "ShoppingListsList";

    private ShoppingListsAdapter adapter;
    private int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fab_layout, container, false);

        FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "FAB clicked", Toast.LENGTH_SHORT).show();
                adapter.addList(new ShoppingList("Shopping list " + i++));
            }
        });

        adapter = new ShoppingListsAdapter();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
