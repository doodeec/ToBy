package com.doodeec.toby.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.toby.Storage.ListItemDBEntry;

/**
 * Created by Dusan Bartos on 12.1.2015.
 */
public class ShoppingListItem implements DbSavable {

    private Integer id;
    private String name;

    public ShoppingListItem(String name) {
        this.name = name;
    }

    public ShoppingListItem(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex(ListItemDBEntry.COL_name));
        this.id = cursor.getInt(cursor.getColumnIndex(ListItemDBEntry.COL_id));
    }

    public String getName() {
        return name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put(ListItemDBEntry.COL_id, id);
        values.put(ListItemDBEntry.COL_name, name);
        return values;
    }

    @Override
    public String getTableName() {
        return ListItemDBEntry.TABLE_NAME;
    }
}
