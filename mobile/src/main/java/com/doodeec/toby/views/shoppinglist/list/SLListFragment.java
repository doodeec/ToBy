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

import com.doodeec.toby.R;
import com.doodeec.toby.appstate.AppData;
import com.doodeec.toby.views.RecyclerItemClickListener;
import com.doodeec.toby.views.shoppinglist.detail.SLDetailActivity;
import com.getbase.floatingactionbutton.AddFloatingActionButton;

/**
 * @author dusan.bartos
 */
public class SLListFragment extends Fragment implements View.OnClickListener {

    public final static String TAG = "ShoppingListsList";

    private SLAdapter mAdapter;
    private TextView mWarningText;

    public static SLListFragment newInstance(Bundle extras) {
        SLListFragment fragment = new SLListFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fab_layout, container, false);

        mAdapter = new SLAdapter();
        mAdapter.setData(AppData.getInstance().getShoppingLists());

        mWarningText = (TextView) rootView.findViewById(R.id.warning);
        checkDataSize();

        AddFloatingActionButton fab1 = (AddFloatingActionButton) rootView.findViewById(R.id.floatingButton);
        fab1.setOnClickListener(this);

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
                //do nothing
            }
        }));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Intent detailIntent = new Intent(getActivity(), SLDetailActivity.class);
        detailIntent.putExtra(SLDetailActivity.SHOPPING_LIST_NEW, true);
        startActivity(detailIntent);
    }

    private void checkDataSize() {
        if (AppData.getInstance().getShoppingLists() != null &&
                AppData.getInstance().getShoppingLists().size() > 0) {
            mWarningText.setVisibility(View.GONE);
        } else {
            mWarningText.setVisibility(View.VISIBLE);
        }
    }
}
