package com.doodeec.toby.views.shoppinglist.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.doodeec.toby.R;
import com.doodeec.toby.appstate.AppData;
import com.doodeec.toby.objectmodel.ShoppingList;
import com.doodeec.toby.views.shoppinglist.edit.AddProductDialog;
import com.doodeec.toby.views.shoppinglist.edit.IAddProductListener;
import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;
import com.getbase.floatingactionbutton.AddFloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author dusan.bartos
 */
public class SLDetailFragment extends Fragment implements View.OnClickListener{

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
    EditText mName;
    @InjectView(R.id.add_product_button)
    AddFloatingActionButton mAddProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_layout, container, false);

        ButterKnife.inject(this, rootView);

        mAdapter = new SLDetailAdapter();
        mItemsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mItemsList.setHasFixedSize(true);
        mItemsList.setAdapter(mAdapter);

        if (getArguments() != null) {
            boolean slIsNew = getArguments().getBoolean(SLDetailActivity.SHOPPING_LIST_NEW, false);
            if (!slIsNew) {
                int slPosition = getArguments().getInt(SLDetailActivity.SHOPPING_LIST_ID, -1);
                mShoppingList = AppData.getInstance().getShoppingLists().get(slPosition);
            } else {
                mShoppingList = new ShoppingList("Unnamed");
                AppData.getInstance().addShoppingList(mShoppingList);
            }

            mAdapter.setData(mShoppingList.getItems());
            mName.setText(mShoppingList.getName());
        }

        mAddProduct.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onPause() {
        mShoppingList.setName(mName.getText().toString());
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        AddProductDialog.showDialog(getActivity().getSupportFragmentManager(), new IAddProductListener() {
            @Override
            public void OnProductAdded(IShoppingListItem shoppingListItem) {
                mShoppingList.addItem(shoppingListItem);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnDismissed() {
            }
        });
    }

    public void completeShoppingList() {
        if (mShoppingList != null) {
            for (IShoppingListItem item : mShoppingList.getItems()) {
                item.setChecked(true);
            }
            mShoppingList.checkCompletion();
            mAdapter.notifyDataSetChanged();
        }
    }
}
