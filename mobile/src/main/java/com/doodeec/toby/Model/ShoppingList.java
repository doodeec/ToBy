package com.doodeec.toby.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.toby.ApplicationState.AppData;
import com.doodeec.toby.Storage.ShoppingListDBEntry;

import java.util.Date;

/**
 * Shopping list object
 *
 * @author Dusan Bartos
 */
public class ShoppingList extends com.doodeec.tobycommon.model.ShoppingList implements DbSavable {

    public ShoppingList(String name) {
        super(name);
        this.name = name;
    }

    public ShoppingList(Cursor cursor) {
        super();

        AppData appData = AppData.getInstance();

        this.name = cursor.getString(cursor.getColumnIndex(ShoppingListDBEntry.COL_name));
        this.id = cursor.getInt(cursor.getColumnIndex(ShoppingListDBEntry.COL_id));
        this.category = appData.getCategoryById(
                cursor.getInt(cursor.getColumnIndex(ShoppingListDBEntry.COL_categoryId)));
        this.shop = appData.getShopById(
                cursor.getInt(cursor.getColumnIndex(ShoppingListDBEntry.COL_shopId)));
        this.completed = cursor.getInt(cursor.getColumnIndex(ShoppingListDBEntry.COL_id)) == 1;
        this.items = deserializeItems(
                cursor.getString(cursor.getColumnIndex(ShoppingListDBEntry.COL_items)));
        this.created = new Date(cursor.getLong(
                cursor.getColumnIndex(ShoppingListDBEntry.COL_created)));
        this.edited = new Date(cursor.getLong(
                cursor.getColumnIndex(ShoppingListDBEntry.COL_edited)));
        this.dueDate = new Date(cursor.getLong(
                cursor.getColumnIndex(ShoppingListDBEntry.COL_due_date)));
        this.dateCompleted = new Date(cursor.getLong(
                cursor.getColumnIndex(ShoppingListDBEntry.COL_completed_date)));
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put(ShoppingListDBEntry.COL_id, id);
        values.put(ShoppingListDBEntry.COL_name, name);
        values.put(ShoppingListDBEntry.COL_categoryId, category.getId());
        values.put(ShoppingListDBEntry.COL_shopId, shop.getId());
        values.put(ShoppingListDBEntry.COL_completed, completed ? 1 : 0);
        values.put(ShoppingListDBEntry.COL_items, serializeItems(items));
        values.put(ShoppingListDBEntry.COL_created, created.getTime());
        values.put(ShoppingListDBEntry.COL_edited, edited.getTime());
        values.put(ShoppingListDBEntry.COL_due_date, dueDate.getTime());
        values.put(ShoppingListDBEntry.COL_completed_date, dateCompleted.getTime());
        return values;
    }

    @Override
    public String getTableName() {
        return ShoppingListDBEntry.TABLE_NAME;
    }
}
