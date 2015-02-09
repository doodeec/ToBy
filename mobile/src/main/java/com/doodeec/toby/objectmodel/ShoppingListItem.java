package com.doodeec.toby.objectmodel;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.toby.dbstorage.ListItemDBEntry;

/**
 * @author dusan.bartos
 */
public class ShoppingListItem extends com.doodeec.tobycommon.model.ShoppingListItem implements IDbSavable {

    public ShoppingListItem(String name) {
        super(name);
    }

    public ShoppingListItem(Cursor cursor) {
        this(cursor.getString(cursor.getColumnIndex(ListItemDBEntry.COL_name)));
        this.id = cursor.getInt(cursor.getColumnIndex(ListItemDBEntry.COL_id));
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
