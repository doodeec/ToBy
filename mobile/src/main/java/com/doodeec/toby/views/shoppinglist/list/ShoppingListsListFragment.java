package com.doodeec.toby.views.shoppinglist.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.doodeec.toby.R;
import com.doodeec.toby.appstate.AppData;
import com.doodeec.toby.objectmodel.ShoppingList;
import com.doodeec.toby.views.FloatingActionButton;
import com.doodeec.toby.views.RecyclerItemClickListener;
import com.doodeec.toby.views.shoppinglist.detail.SLDetailActivity;
import com.doodeec.toby.views.shoppinglist.edit.CreateListDialog;
import com.doodeec.toby.views.shoppinglist.edit.ICreateListListener;

/**
 * @author dusan.bartos
 */
public class ShoppingListsListFragment extends Fragment {

    public final static String TAG = "ShoppingListsList";

    private ShoppingListsAdapter mAdapter;
    private TextView mWarningText;

    public static ShoppingListsListFragment newInstance(Bundle extras) {
        ShoppingListsListFragment fragment = new ShoppingListsListFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ShoppingListsAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fab_layout, container, false);

        mAdapter.setData(AppData.getInstance().getShoppingLists());

        mWarningText = (TextView) rootView.findViewById(R.id.warning);
        checkDataSize();

        FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.floatingButton);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateListDialog.showDialog(getActivity().getSupportFragmentManager(), new ICreateListListener() {
                    @Override
                    public void OnListCreated(ShoppingList shoppingList) {
                        Toast.makeText(getActivity(), shoppingList.getName(), Toast.LENGTH_SHORT).show();
                        AppData.getInstance().addShoppingList(shoppingList);
                        mAdapter.notifyDataSetChanged();
                        checkDataSize();
                    }

                    @Override
                    public void OnListDismissed() {
                        checkDataSize();
                    }
                });
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent detailIntent = new Intent(getActivity(), SLDetailActivity.class);
                detailIntent.putExtra(SLDetailActivity.SHOPPING_LIST_ID, position);
                startActivity(detailIntent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "Edit shopping list", Toast.LENGTH_SHORT).show();
            }
        }));

        return rootView;
    }

    private void checkDataSize() {
        if (AppData.getInstance().getShoppingLists().size() > 0) {
            mWarningText.setVisibility(View.GONE);
        } else {
            mWarningText.setVisibility(View.VISIBLE);
        }
    }
}
