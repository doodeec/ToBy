package com.doodeec.toby.Storage;

/**
 * DB entry for Shopping list item object
 *
 * properties:
 * {id} - identifier {@link java.lang.Integer}
 * {name} - list item name {@link java.lang.String}
 *
 * @author Dusan Bartos
 */
public abstract class ListItemDBEntry {

    public static final String TABLE_NAME = "listItem";
    public static final String COL_name = "name";
    public static final String COL_id = "id";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_id + DBStorageStatic.TYPE_INT + " PRIMARY KEY," +
                    COL_name + DBStorageStatic.TYPE_TEXT + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
