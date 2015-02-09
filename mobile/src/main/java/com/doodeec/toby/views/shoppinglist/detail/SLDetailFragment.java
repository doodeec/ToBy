package com.doodeec.toby.views.shoppinglist.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @InjectView(R.id.items_list)
    RecyclerView mItemsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_layout, container, false);

        ButterKnife.inject(this, rootView);

        if (savedInstanceState != null) {
            int slPosition = savedInstanceState.getInt(SLDetailActivity.SHOPPING_LIST_ID);
            mShoppingList = AppData.getInstance().getShoppingLists().get(slPosition);
        }

        //TODO not finished, add adapter + VH

        return rootView;
    }
}
