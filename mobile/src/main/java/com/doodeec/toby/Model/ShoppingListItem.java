package com.doodeec.toby.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.doodeec.toby.Storage.ListItemDBEntry;

/**
 * Created by Dusan Bartos on 12.1.2015.
 */
public class ShoppingListItem {

    //TODO implements dbSavable

    private Integer id;
    private String name;

    public ShoppingListItem(String name) {
        this.name = name;
    }

    public ShoppingListItem(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex(ListItemDBEntry.COL_name));
        this.id = cursor.getInt(cursor.getColumnIndex(ListItemDBEntry.COL_id));
    }

    public long saveToDb(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ListItemDBEntry.COL_id, id);
        values.put(ListItemDBEntry.COL_name, name);

        long newRowId = -1;
        String[] column = {ListItemDBEntry.COL_id};
        String[] args = {String.valueOf(id)};

        Cursor c = db.query(ListItemDBEntry.TABLE_NAME, column, "id = ?", args, null, null, null);

        if (c.moveToFirst() && id != null) {
            newRowId = db.update(ListItemDBEntry.TABLE_NAME, values, "id = ?", args);

            if (newRowId == 0) {
                newRowId = -1;
            }

            Log.d("TOBY", "Update list item with id: " + args[0]);
        } else {
            newRowId = db.insert(ListItemDBEntry.TABLE_NAME, null, values);
            //TODO save the instance ID
            this.id = (int) newRowId;

            Log.d("TOBY", "Inserted company newRowId: " + newRowId);
        }
        c.close();
        return newRowId;
    }
}
