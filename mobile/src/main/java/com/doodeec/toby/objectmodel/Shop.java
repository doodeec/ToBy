package com.doodeec.toby.objectmodel;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.toby.appstate.AppData;
import com.doodeec.toby.dbstorage.ShopDBEntry;

/**
 * Shop object
 *
 * @author Dusan Bartos
 */
public class Shop extends com.doodeec.tobycommon.model.Shop implements DbSavable {

    public Shop(String name) {
        super(name);
        this.name = name;
    }

    public Shop(String name, com.doodeec.tobycommon.model.ShopCategory category) {
        super(name, category);
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
