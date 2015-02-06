package com.doodeec.toby.views.shoppingitem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doodeec.toby.R;
import com.doodeec.toby.appstate.AppData;
import com.doodeec.toby.views.FloatingActionButton;

/**
 * @author dusan.bartos
 */
public class ShoppingItemsListFragment extends Fragment {

    public final static String TAG = "ShoppingItemsList";

    private ShoppingItemAdapter adapter;
    private int i = 0;
    private TextView warningText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ShoppingItemAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fab_layout, container, false);

        adapter.setData(AppData.getInstance().getShoppingListItems());

        warningText = (TextView) rootView.findViewById(R.id.warning);
        checkDataSize();

        FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.floatingButton);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AppData.getInstance().addShoppingListItem(new ShoppingListItem("Shopping item " + i++));
                adapter.notifyDataSetChanged();
                checkDataSize();*/
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void checkDataSize() {
        if (AppData.getInstance().getShoppingListItems().size() > 0) {
            warningText.setVisibility(View.GONE);
        } else {
            warningText.setVisibility(View.VISIBLE);
        }
    }
}
