package com.example.projetopdmgrupo3.models;

public class DeleteCasosSynced {
    int id;
    int idIsSynced;

    public DeleteCasosSynced() {
    }

    public DeleteCasosSynced(int id, int idIsSynced) {
        this.id = id;
        this.idIsSynced = idIsSynced;
    }

    public DeleteCasosSynced(int idIsSynced) {
        this.idIsSynced = idIsSynced;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdIsSynced() {
        return idIsSynced;
    }

    public void setIdIsSynced(int idIsSynced) {
        this.idIsSynced = idIsSynced;
    }
}
