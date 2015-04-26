package com.doodeec.toby.objectmodel;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.toby.dbstorage.ShoppingListDBEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Shopping list object
 *
 * @author Dusan Bartos
 */
public class ShoppingList extends com.doodeec.tobycommon.model.ShoppingList implements IDbSavable {

    public ShoppingList(String name) {
        super(name);
        this.name = name;

        this.created = new Date(System.currentTimeMillis());
        this.edited = new Date(System.currentTimeMillis());
    }

    public ShoppingList(Cursor cursor) {
        this(cursor.getString(cursor.getColumnIndex(ShoppingListDBEntry.COL_name)));

        this.id = cursor.getInt(cursor.getColumnIndex(ShoppingListDBEntry.COL_id));
        /*this.category = appData.getCategoryById(
                cursor.getInt(cursor.getColumnIndex(ShoppingListDBEntry.COL_categoryId)));
        this.shop = appData.getShopById(
                cursor.getInt(cursor.getColumnIndex(ShoppingListDBEntry.COL_shopId)));*/
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

    public ShoppingList(JSONObject jsonDefinition) throws JSONException {
        super(jsonDefinition);
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put(ShoppingListDBEntry.COL_id, id);
        values.put(ShoppingListDBEntry.COL_name, name);
        values.put(ShoppingListDBEntry.COL_completed, completed ? 1 : 0);
        values.put(ShoppingListDBEntry.COL_items, serializeItems(items));
        values.put(ShoppingListDBEntry.COL_created, created.getTime());
        values.put(ShoppingListDBEntry.COL_edited, edited.getTime());
        values.put(ShoppingListDBEntry.COL_due_date, dueDate != null ? dueDate.getTime() : null);
        values.put(ShoppingListDBEntry.COL_completed_date, dateCompleted != null ?
                dateCompleted.getTime() : null);
        return values;
    }

    @Override
    public String getTableName() {
        return ShoppingListDBEntry.TABLE_NAME;
    }
}
