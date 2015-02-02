package com.doodeec.toby.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.toby.Storage.ShopCategoryDBEntry;

/**
 * Shop Category object
 *
 * @author Dusan Bartos
 */
public class ShopCategory extends com.doodeec.tobycommon.model.ShopCategory implements DbSavable {

    public ShopCategory(String name) {
        super(name);
    }

    public ShopCategory(Cursor cursor) {
        super();

        this.name = cursor.getString(cursor.getColumnIndex(ShopCategoryDBEntry.COL_name));
        this.id = cursor.getInt(cursor.getColumnIndex(ShopCategoryDBEntry.COL_id));
        this.usage = cursor.getInt(cursor.getColumnIndex(ShopCategoryDBEntry.COL_usage));
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put(ShopCategoryDBEntry.COL_id, id);
        values.put(ShopCategoryDBEntry.COL_name, name);
        values.put(ShopCategoryDBEntry.COL_usage, usage);
        return values;
    }

    @Override
    public String getTableName() {
        return ShopCategoryDBEntry.TABLE_NAME;
    }
}
