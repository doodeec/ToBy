package com.doodeec.toby.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.toby.ApplicationState.AppData;
import com.doodeec.toby.Storage.ShopDBEntry;

/**
 * Shop object
 *
 * @author Dusan Bartos
 */
public class Shop implements DbSavable {

    private Integer id;
    private String name;
    private ShopCategory category;

    public Shop(String name) {
        this.name = name;
    }

    public Shop(String name, ShopCategory category) {
        this.name = name;
        this.category = category;
    }

    public Shop(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex(ShopDBEntry.COL_name));
        this.id = cursor.getInt(cursor.getColumnIndex(ShopDBEntry.COL_id));
        this.category = AppData.getInstance().getCategoryById(cursor.getInt(
                cursor.getColumnIndex(ShopDBEntry.COL_category)));
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
        values.put(ShopDBEntry.COL_id, id);
        values.put(ShopDBEntry.COL_name, name);
        values.put(ShopDBEntry.COL_category, category.getId());
        return values;
    }

    @Override
    public String getTableName() {
        return ShopDBEntry.TABLE_NAME;
    }
}
