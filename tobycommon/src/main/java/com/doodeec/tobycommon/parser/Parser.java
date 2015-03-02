package com.doodeec.tobycommon.parser;

import com.doodeec.tobycommon.model.ShoppingList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dusan.bartos
 */
public class Parser {

    /**
     * Parses json array to list of shopping lists
     * Only adds lists which have some items
     *
     * @param listsArray json definition
     * @return list of non-empty shopping lists
     * @throws JSONException
     */
    public static List<ShoppingList> parseShoppingLists(JSONArray listsArray) throws JSONException {
        List<ShoppingList> lists = new ArrayList<>();

        for (int i = 0; i < listsArray.length(); i++) {
            ShoppingList shoppingList = new ShoppingList(listsArray.getJSONObject(i));
            if (shoppingList.getItems().size() > 0) {
                lists.add(shoppingList);
            }
        }

        return lists;
    }

    public static ShoppingList parseShoppingList(JSONObject listDefinition) throws JSONException {
        return new ShoppingList(listDefinition);
    }
}
