package com.doodeec.toby.views.shoppinglist.edit;

import com.doodeec.toby.objectmodel.ShoppingList;

/**
 * @author dusan.bartos
 */
public interface ICreateListListener {

    void OnListCreated(ShoppingList shoppingList);

    void OnListDismissed();
}
