package com.doodeec.toby.Model;

import java.util.List;
import java.util.Observable;

/**
 * Base observable wrapper for session dataLists
 *
 * @author Dusan Bartos
 */
public class BaseObservable<DataObject> extends Observable {

    private List<DataObject> dataList;

    /**
     * Sets data list without notifying observers
     * Used when reading list from database
     * Observers are used mainly to sync session and database lists, since we are reading
     * directly from database, sync is not necessary
     *
     * @param newList list
     */
    public void setData(List<DataObject> newList) {
        this.dataList = newList;
    }

    /**
     * Sets data list and notifies observers
     * Observers are meant to reflect current state into the database
     *
     * @param newList list
     */
    public void updateData(List<DataObject> newList) {
        this.dataList = newList;
        setChanged();
        notifyObservers();
    }

    public List<DataObject> getData() {
        return dataList;
    }
}
