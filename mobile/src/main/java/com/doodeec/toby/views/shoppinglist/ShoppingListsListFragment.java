package com.doodeec.toby.views.shoppinglist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doodeec.toby.R;
import com.doodeec.toby.appstate.AppData;
import com.doodeec.toby.objectmodel.ShoppingList;
import com.doodeec.toby.views.FloatingActionButton;
import com.doodeec.toby.views.shoppinglist.edit.CreateListDialog;
import com.doodeec.toby.views.shoppinglist.edit.ICreateListListener;

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

        FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.floatingButton);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateListDialog.showDialog(getActivity().getSupportFragmentManager(), new ICreateListListener() {
                    @Override
                    public void OnListCreated(ShoppingList shoppingList) {
                        Toast.makeText(getActivity(), shoppingList.getName(), Toast.LENGTH_SHORT).show();
                        AppData.getInstance().addShoppingList(shoppingList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void OnListDismissed() {

                    }
                });
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
