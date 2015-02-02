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
import com.doodeec.toby.appstate.AppData;
import com.doodeec.toby.objectmodel.Shop;
import com.doodeec.toby.objectmodel.ShopCategory;
import com.doodeec.toby.objectmodel.ShoppingList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author dusan.bartos
 */
public class CreateListDialog extends DialogFragment {

    public static final String CREATE_DIALOG_FRG_TAG = "createDlg";

    @InjectView(R.id.shopping_list_name)
    EditText listName;
    @InjectView(R.id.shopping_list_shop)
    Spinner shop;
    @InjectView(R.id.shopping_list_shop_category)
    Spinner shopCategory;
    @InjectView(R.id.dismiss_list)
    Button dismissBtn;
    @InjectView(R.id.submit_list)
    Button saveBtn;

    private ICreateListListener dialogListener;

    public static void showDialog(FragmentManager manager, ICreateListListener listener) {
        CreateListDialog myDialog = new CreateListDialog();
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
        View view = inflater.inflate(R.layout.create_list_dialog, container, false);

        ButterKnife.inject(this, view);

        shopCategory.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                AppData.getInstance().getCategories()));

        shop.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                AppData.getInstance().getShops()));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShoppingList();
            }
        });
    }

    private void createShoppingList() {
        String name = this.listName.getText().toString();
        Shop shop = AppData.getInstance().getShops().get(this.shop.getSelectedItemPosition());
        ShopCategory category = AppData.getInstance().getCategories().get(this.shopCategory.getSelectedItemPosition());

        if (name.length() == 0) {
            Toast.makeText(getActivity(), R.string.list_no_name, Toast.LENGTH_SHORT).show();
            name = "Unnamed";
        }

        ShoppingList shoppingList = new ShoppingList(name);
        shoppingList.setShop(shop);
        shoppingList.setShopCategory(category);

        if (dialogListener != null) {
            dialogListener.OnListCreated(shoppingList);
        }

        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dialogListener != null) {
            dialogListener.OnListDismissed();
        }
    }
}
