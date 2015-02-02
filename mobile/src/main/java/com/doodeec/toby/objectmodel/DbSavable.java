package com.doodeec.toby.objectmodel;

import android.content.ContentValues;

/**
 * Provides interface for DB handling
 *
 * @author Dusan Bartos
 */
public interface DbSavable {
    String id_column = "id";

    String getTableName();

    Integer getId();

    void setId(int id);

    ContentValues getValues();
}
