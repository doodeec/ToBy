package com.doodeec.toby.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.doodeec.toby.ApplicationState.AppData;
import com.doodeec.toby.Storage.ShopDBEntry;

/**
 * Created by Dusan Bartos on 14.1.2015.
 */
public class Shop {

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

    public long saveToDb(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ShopDBEntry.COL_id, id);
        values.put(ShopDBEntry.COL_name, name);
        values.put(ShopDBEntry.COL_category, category.getId());

        long newRowId = -1;
        String[] column = {ShopDBEntry.COL_id};
        String[] args = {String.valueOf(id)};

        Cursor c = db.query(ShopDBEntry.TABLE_NAME, column, "id = ?", args, null, null, null);

        if (c.moveToFirst() && id != null) {
            newRowId = db.update(ShopDBEntry.TABLE_NAME, values, "id = ?", args);

            if (newRowId == 0) {
                newRowId = -1;
            }

            Log.d("TOBY", "Update shop with id: " + args[0]);
        } else {
            newRowId = db.insert(ShopDBEntry.TABLE_NAME, null, values);
            this.id = (int) newRowId;

            Log.d("TOBY", "Inserted shop: " + newRowId);
        }
        c.close();
        return newRowId;
    }
}
