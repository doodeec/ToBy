package com.doodeec.toby.views.shoppinglist.edit;

import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;

/**
 * @author dusan.bartos
 */
public interface IAddProductListener {

    void OnProductAdded(IShoppingListItem shoppingListItem);

    void OnDismissed();
}
