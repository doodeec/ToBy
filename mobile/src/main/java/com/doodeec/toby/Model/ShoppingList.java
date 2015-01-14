package com.doodeec.toby.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.doodeec.toby.Storage.ShoppingListDBEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dusan Bartos on 14.1.2015.
 */
public class ShoppingList {

    private Integer id;
    private ShopCategory category;
    private Shop shopId;
    private String name;
    private Boolean completed = false;
    private List<ShoppingListItem> items = new ArrayList<>();
    private Date created;
    private Date edited;
    private Date dueDate;
    private Date dateCompleted;

    public ShoppingList(String name) {
        this.name = name;
    }

    public ShoppingList(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex(ShoppingListDBEntry.COL_name));
        this.id = cursor.getInt(cursor.getColumnIndex(ShoppingListDBEntry.COL_id));
    }

    public long saveToDb(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListDBEntry.COL_id, id);
        values.put(ShoppingListDBEntry.COL_name, name);

        long newRowId = -1;
        String[] column = {ShoppingListDBEntry.COL_id};
        String[] args = {String.valueOf(id)};

        Cursor c = db.query(ShoppingListDBEntry.TABLE_NAME, column, "id = ?", args, null, null, null);

        if (c.moveToFirst() && id != null) {
            newRowId = db.update(ShoppingListDBEntry.TABLE_NAME, values, "id = ?", args);

            if (newRowId == 0) {
                newRowId = -1;
            }

            Log.d("TOBY", "Update shopping list with id: " + args[0]);
        } else {
            newRowId = db.insert(ShoppingListDBEntry.TABLE_NAME, null, values);
            id = (int) newRowId;

            Log.d("TOBY", "Inserted shopping list newRowId: " + newRowId);
        }
        c.close();
        return newRowId;
    }
}
