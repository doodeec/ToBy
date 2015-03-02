package com.doodeec.toby.views.shoppinglist.edit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.doodeec.toby.R;
import com.doodeec.toby.objectmodel.ShoppingListItem;
import com.doodeec.tobycommon.model.UnitType;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author dusan.bartos
 */
public class AddProductDialog extends DialogFragment {

    public static final String CREATE_DIALOG_FRG_TAG = "createDlg";

    @InjectView(R.id.item_name)
    EditText mItemName;
    @InjectView(R.id.item_amount)
    EditText mItemAmount;
    @InjectView(R.id.item_amount_unit)
    Spinner mItemUnit;
    @InjectView(R.id.dismiss_item)
    Button mDismissBtn;
    @InjectView(R.id.submit_item)
    Button mSaveBtn;

    private IAddProductListener dialogListener;

    public static void showDialog(FragmentManager manager, IAddProductListener listener) {
        AddProductDialog myDialog = new AddProductDialog();
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
        View view = inflater.inflate(R.layout.add_product_dialog, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mItemUnit.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                UnitType.values()));
        
        mDismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToList();
            }
        });
    }

    private void addItemToList() {
        String name = this.mItemName.getText().toString();
        String amount = this.mItemAmount.getText().toString();

        if (name.length() == 0) {
            Toast.makeText(getActivity(), R.string.list_no_name, Toast.LENGTH_SHORT).show();
            name = "Unnamed";
        }

        ShoppingListItem item = new ShoppingListItem(name);
        item.setAmount(Double.valueOf(amount));
        item.setUnitType((UnitType) mItemUnit.getSelectedItem());

        if (dialogListener != null) {
            dialogListener.OnProductAdded(item);
        }

        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dialogListener != null) {
            dialogListener.OnDismissed();
        }
    }
}
