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

    public static List<ShoppingList> parseShoppingLists(JSONArray listsArray) throws JSONException {
        List<ShoppingList> lists = new ArrayList<>();

        for (int i = 0; i < listsArray.length(); i++) {
            lists.add(new ShoppingList(listsArray.getJSONObject(i)));
        }

        return lists;
    }

    public static ShoppingList parseShoppingList(JSONObject listDefinition) throws JSONException {
        return new ShoppingList(listDefinition);
    }
}
