package com.doodeec.toby.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.doodeec.toby.Storage.ShopCategoryDBEntry;

/**
 * Created by Dusan Bartos on 14.1.2015.
 */
public class ShopCategory {

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

    public Integer getId() {
        return id;
    }

    /**
     * Saves object to the SQLite DB
     * @param db database instance
     * @return row id
     */
    public long saveToDb(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ShopCategoryDBEntry.COL_id, id);
        values.put(ShopCategoryDBEntry.COL_name, name);
        values.put(ShopCategoryDBEntry.COL_usage, usage);

        long newRowId = -1;
        String[] column = {ShopCategoryDBEntry.COL_id};
        String[] args = {String.valueOf(id)};

        Cursor c = db.query(ShopCategoryDBEntry.TABLE_NAME, column, "id = ?", args, null, null, null);

        if (c.moveToFirst() && id != null) {
            newRowId = db.update(ShopCategoryDBEntry.TABLE_NAME, values, "id = ?", args);

            if (newRowId == 0) {
                newRowId = -1;
            }

            Log.d("TOBY", "Update shop category with id: " + args[0]);
        } else {
            newRowId = db.insert(ShopCategoryDBEntry.TABLE_NAME, null, values);
            this.id = (int) newRowId;

            Log.d("TOBY", "Inserted shop category: " + newRowId);
        }
        c.close();
        return newRowId;
    }
}
