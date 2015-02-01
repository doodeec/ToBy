package com.doodeec.toby.Storage;

/**
 * DB entry for item object entry in shopping list
 *
 * properties:
 * {id} - identifier {@link java.lang.Integer}
 * {amount} - amount of item units {@link java.lang.Float}
 * {unit} - string representation of Unit enum {@link com.doodeec.tobycommon.model.UnitType}
 *
 * @author Dusan Bartos
 */
public abstract class ItemEntryDBEntry {

    public static final String TABLE_NAME = "itemEntry";
    public static final String COL_id = "id";
    public static final String COL_amount = "amount";
    public static final String COL_unit = "unit";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_id + DBStorageStatic.TYPE_INT + " PRIMARY KEY," +
                    COL_unit + DBStorageStatic.TYPE_TEXT + "," +
                    COL_amount + DBStorageStatic.TYPE_NUMERIC + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
