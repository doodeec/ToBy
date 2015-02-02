package com.doodeec.toby.views.shop;

import com.doodeec.toby.objectmodel.Shop;

/**
 * @author dusan.bartos
 */
public interface ICreateShopListener {

    void OnShopCreated(Shop shop);

    void OnShopDismissed();
}
