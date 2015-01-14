package com.doodeec.toby.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.toby.Storage.ShopCategoryDBEntry;

/**
 * Shop Category object
 *
 * @author Dusan Bartos
 */
public class ShopCategory implements DbSavable {

    private Integer id;
    private String name;
    private Integer usage;

    public ShopCategory(String name) {
        this.name = name;
        this.usage = 0;
    }

    public ShopCategory(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex(ShopCategoryDBEntry.COL_name));
        this.id = cursor.getInt(cursor.getColumnIndex(ShopCategoryDBEntry.COL_id));
        this.usage = cursor.getInt(cursor.getColumnIndex(ShopCategoryDBEntry.COL_usage));
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
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
