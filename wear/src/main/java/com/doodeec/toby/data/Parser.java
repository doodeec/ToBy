package com.doodeec.toby.data;

import android.util.Log;

import com.doodeec.tobycommon.model.ShoppingList;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dusan.bartos
 */
public class Parser {

    private static final String TAG = "WEARABLE_TOBY_PARSER";

    public static List<ShoppingList> parseShoppingLists(JSONArray listsArray) {
        List<ShoppingList> lists = new ArrayList<>();

        try {
            for (int i = 0; i < listsArray.length(); i++) {
                lists.add(new ShoppingList(listsArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Parsing shopping list failed: " + e.getMessage());
        }

        return lists;
    }
}
