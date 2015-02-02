package com.doodeec.toby.views.shoppinglist.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodeec.toby.R;

/**
 * @author dusan.bartos
 */
public class ShoppingListDetailFragment extends Fragment {

    public final static String TAG = "ShoppingListDetail";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_layout, container, false);

        return rootView;
    }
}
