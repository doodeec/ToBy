package com.doodeec.toby.dbstorage;

/**
 * DB entry for Shop Category object
 *
 * properties:
 * {id} - identifier {@link Integer}
 * {name} - category name {@link String}
 * {usage} - relative category usage (tracked for whisperer) {@link Integer}
 *
 * @author Dusan Bartos
 */
public abstract class ShopCategoryDBEntry {

    public static final String TABLE_NAME = "shopCategory";
    public static final String COL_id = "id";
    public static final String COL_name = "name";
    public static final String COL_usage = "usage";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_id + DBStorageStatic.TYPE_INT + " PRIMARY KEY," +
                    COL_name + DBStorageStatic.TYPE_TEXT + "," +
                    COL_usage + DBStorageStatic.TYPE_INT + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
