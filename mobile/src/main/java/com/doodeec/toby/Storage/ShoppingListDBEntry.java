package com.doodeec.toby.Storage;

/**
 * DB entry for Shopping list object
 *
 * properties:
 * {id} - identifier {@link java.lang.Integer}
 * {name} - shopping list name {@link java.lang.String}
 * {unit} - string representation of Unit enum {@link com.doodeec.tobycommon.model.UnitType}
 *
 * @author Dusan Bartos
 */
public abstract class ShoppingListDBEntry {

    public static final String TABLE_NAME = "shoppingList";
    public static final String COL_name = "name";
    public static final String COL_categoryId = "catId";
    public static final String COL_shopId = "shopId";
    public static final String COL_items = "items";
    public static final String COL_completed = "completed";
    public static final String COL_id = "id";

    public static final String COL_created = "created";
    public static final String COL_edited = "edited";
    public static final String COL_due_date = "dueDate";
    public static final String COL_completed_date = "completedDate";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_id + DBStorageStatic.TYPE_INT + " PRIMARY KEY," +
                    COL_name + DBStorageStatic.TYPE_TEXT + "," +
                    COL_categoryId + DBStorageStatic.TYPE_INT + "," +
                    COL_shopId + DBStorageStatic.TYPE_INT + "," +
                    COL_items + DBStorageStatic.TYPE_TEXT + "," +
                    COL_completed + DBStorageStatic.TYPE_INT + "," +
                    COL_completed_date + DBStorageStatic.TYPE_INT + "," +
                    COL_created + DBStorageStatic.TYPE_TEXT + "," +
                    COL_edited + DBStorageStatic.TYPE_TEXT + "," +
                    COL_due_date + DBStorageStatic.TYPE_TEXT + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
