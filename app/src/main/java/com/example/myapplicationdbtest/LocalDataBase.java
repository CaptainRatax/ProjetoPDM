package com.example.myapplicationdbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocalDataBase extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME="bd_clientes";

    private static final String TABELA_CLIENTE="tb_clientes";

    private static final String COLUNA_CODIGO="codigo";
    private static final String CLOUNA_NOME="nome";
    private static final String CLOUNA_TELEFONE="telefone";
    private static final String CLOUNA_EMAIL="email";

    public LocalDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_TABELA_CLIENTES = "CREATE TABLE " + TABELA_CLIENTE + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY,"
                + CLOUNA_NOME + " TEXT,"
                + CLOUNA_TELEFONE + " TEXT,"
                + CLOUNA_EMAIL + " TEXT)";
        db.execSQL(QUERY_TABELA_CLIENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /* CRUD ABAIXO */

    void addCliente(Cliente cliente){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CLOUNA_NOME, cliente.getNome());
        values.put(CLOUNA_TELEFONE, cliente.getTelefone());
        values.put(CLOUNA_EMAIL, cliente.getEmail());

        db.insert(TABELA_CLIENTE, null, values);
        db.close();

    }

}
