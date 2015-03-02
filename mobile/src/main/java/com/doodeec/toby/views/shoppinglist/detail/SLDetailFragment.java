package com.doodeec.toby.views.shoppinglist.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
    @InjectView(R.id.detail_shop_category)
    Spinner mShopCategory;
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

        mShopCategory.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                AppData.getInstance().getCategories()));
        mShopCategory.setSelection(0);
        
        if (getArguments() != null) {
            boolean slIsNew = getArguments().getBoolean(SLDetailActivity.SHOPPING_LIST_NEW, false);
            if (!slIsNew) {
                int slPosition = getArguments().getInt(SLDetailActivity.SHOPPING_LIST_ID, -1);
                mShoppingList = AppData.getInstance().getShoppingLists().get(slPosition);

                mAdapter.setData(mShoppingList.getItems());
                mName.setText(mShoppingList.getName());
                mShopName.setText(mShoppingList.getShop() != null ? mShoppingList.getShop().getName() : null);
                //TODO selected category
//            mShopCategory.setSelection(0);
            } else {
                mShoppingList = new ShoppingList("Unnamed");
                mAdapter.setData(mShoppingList.getItems());
                AppData.getInstance().addShoppingList(mShoppingList);
            }
        }

        mAddProduct.setOnClickListener(new View.OnClickListener() {
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
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        mShoppingList.setShopCategory(AppData.getInstance().getCategories().get(mShopCategory.getSelectedItemPosition()));
        super.onDestroy();
    }

    public void completeShoppingList() {
        if (mShoppingList != null) {
            for (IShoppingListItem item: mShoppingList.getItems()) {
                item.setChecked(true);
            }
            mShoppingList.checkCompletion();
            mAdapter.notifyDataSetChanged();
        }
    }
}
