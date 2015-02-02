package com.doodeec.toby.objectmodel;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.toby.dbstorage.ListItemDBEntry;

/**
 * Created by Dusan Bartos on 12.1.2015.
 */
public class ShoppingListItem extends com.doodeec.tobycommon.model.ShoppingListItem implements DbSavable {

    public ShoppingListItem(String name) {
        super(name);
        this.name = name;
    }

    public ShoppingListItem(Cursor cursor) {
        super();
        this.name = cursor.getString(cursor.getColumnIndex(ListItemDBEntry.COL_name));
        this.id = cursor.getInt(cursor.getColumnIndex(ListItemDBEntry.COL_id));
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
