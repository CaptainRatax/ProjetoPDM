package com.example.projetopdmgrupo3.models;

public class IsDataSynced {
    int id;
    int isSynced;

    public IsDataSynced() {
    }

    public IsDataSynced(int id, int isSynced) {
        this.id = id;
        this.isSynced = isSynced;
    }

    public IsDataSynced(int isSynced) {
        this.isSynced = isSynced;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }
}
