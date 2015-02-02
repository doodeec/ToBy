package com.doodeec.toby.dbstorage;

/**
 * DB entry for Shop object
 *
 * properties:
 * {id} - identifier {@link Integer}
 * {name} - shop name {@link String}
 *
 * @author Dusan Bartos
 */
public abstract class ShopDBEntry {

    public static final String TABLE_NAME = "shop";
    public static final String COL_id = "id";
    public static final String COL_name = "name";
    public static final String COL_category = "category";

    //TODO longitude, latitude

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_id + DBStorageStatic.TYPE_INT + " PRIMARY KEY," +
                    COL_name + DBStorageStatic.TYPE_TEXT + "," +
                    COL_category + DBStorageStatic.TYPE_INT + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
