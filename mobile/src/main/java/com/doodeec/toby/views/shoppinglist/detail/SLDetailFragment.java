package com.doodeec.toby.views.shoppinglist.detail;

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
import com.doodeec.toby.objectmodel.ShoppingList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author dusan.bartos
 */
public class SLDetailFragment extends Fragment {

    public final static String TAG = "ShoppingListDetail";

    public static SLDetailFragment newInstance(Bundle extras) {
        SLDetailFragment fragment = new SLDetailFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    private ShoppingList mShoppingList;
    private SLDetailAdapter mAdapter;

    @InjectView(R.id.detail_item_list)
    RecyclerView mItemsList;
    @InjectView(R.id.detail_name)
    TextView mName;
    @InjectView(R.id.detail_shop)
    TextView mShopName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_layout, container, false);

        ButterKnife.inject(this, rootView);

        mAdapter = new SLDetailAdapter();
        mItemsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mItemsList.setHasFixedSize(true);
        mItemsList.setAdapter(mAdapter);

        if (getArguments() != null) {
            int slPosition = getArguments().getInt(SLDetailActivity.SHOPPING_LIST_ID);
            mShoppingList = AppData.getInstance().getShoppingLists().get(slPosition);

            mAdapter.setData(mShoppingList.getItems());
            mName.setText(mShoppingList.getName());
            mShopName.setText(mShoppingList.getShop() != null ? mShoppingList.getShop().getName() : null);
        }

        return rootView;
    }
}
