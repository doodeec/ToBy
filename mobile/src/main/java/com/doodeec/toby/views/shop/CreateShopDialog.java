package com.doodeec.toby.views.shop;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.doodeec.toby.R;

/**
 * @author dusan.bartos
 */
public class CreateShopDialog extends DialogFragment {

    public static final String CREATE_DIALOG_FRG_TAG = "createShopDlg";

    private ICreateShopListener dialogListener;

    public static void showDialog(FragmentManager manager, ICreateShopListener listener) {
        CreateShopDialog myDialog = new CreateShopDialog();
        myDialog.dialogListener = listener;
        myDialog.show(manager, CREATE_DIALOG_FRG_TAG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_shop_dialog, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
