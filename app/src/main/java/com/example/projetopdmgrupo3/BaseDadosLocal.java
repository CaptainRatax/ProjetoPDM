package com.example.projetopdmgrupo3;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.projetopdmgrupo3.models.InspecaoOnGoing;
import com.example.projetopdmgrupo3.models.UserLogged;

public class BaseDadosLocal extends SQLiteOpenHelper {

    private static final int VERSAO_BD = 1;
    private static final String NOME_BD = "bd_PDMGrupo3";

    /**TABELAS (TABELA_<NOMETABELA>)*/
    private static final String TABELA_USERLOGGED = "tb_userLoged";
    private static final String TABELA_INSPECAOONGOING = "tb_inspecaoOnGoing";

    /**CAMPOS (<NOMETABELA>_<NOMECAMPO>) */
    //Tabela userLoged
    private static final String USERLOGGED_ID = "ID";
    private static final String USERLOGGED_EMAIL = "Email";
    private static final String USERLOGGED_TELEFONE = "Telefone";
    private static final String USERLOGGED_NOME = "Nome";
    private static final String USERLOGGED_DATANASCIMENTO = "DataNascimento";
    private static final String USERLOGGED_IMAGE = "Imagem";
    private static final String USERLOGGED_ISLOGGEDIN = "IsLoggedIn";
    //Tabela inspecaoOnGoing
    private static final String INSPECAOONGOING_ID = "ID";
    private static final String INSPECAOONGOING_LOCALIDADE = "Localidade";
    private static final String INSPECAOONGOING_MORADA = "Morada";
    private static final String INSPECAOONGOING_CODIGOPOSTAL = "CodigoPostal";
    private static final String INSPECAOONGOING_ENTRADA = "Entrada";
    private static final String INSPECAOONGOING_INSPECAOONGOING = "InspecaoOnGoing";

    public BaseDadosLocal(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_CREATE_TABELA_USERLOGGED = "CREATE TABLE " + TABELA_USERLOGGED + "("
                + USERLOGGED_ID + " INTEGER PRIMARY KEY, " + USERLOGGED_EMAIL + " TEXT, "
                + USERLOGGED_TELEFONE + " TEXT, " + USERLOGGED_NOME + " TEXT, "
                + USERLOGGED_DATANASCIMENTO + " TEXT, " + USERLOGGED_IMAGE + " TEXT, " + USERLOGGED_ISLOGGEDIN + " INTEGER)";
        String QUERY_CREATE_TABELA_INSPECAOONGOING = "CREATE TABLE " + TABELA_INSPECAOONGOING + "("
                + INSPECAOONGOING_ID + " INTEGER PRIMARY KEY, " + INSPECAOONGOING_LOCALIDADE + " TEXT, "
                + INSPECAOONGOING_MORADA + " TEXT, " + INSPECAOONGOING_CODIGOPOSTAL + " TEXT, "
                + INSPECAOONGOING_ENTRADA + " TEXT, " + INSPECAOONGOING_INSPECAOONGOING + " INTEGER)";
        db.execSQL(QUERY_CREATE_TABELA_USERLOGGED);
        db.execSQL(QUERY_CREATE_TABELA_INSPECAOONGOING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /** CRUD USERLOGGED */
    //ADICIONAR USERLOGGED
    void addUserLogged(UserLogged userLogged){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USERLOGGED_EMAIL, userLogged.getEmail());
        values.put(USERLOGGED_TELEFONE, userLogged.getTelefone());
        values.put(USERLOGGED_NOME, userLogged.getNome());
        values.put(USERLOGGED_DATANASCIMENTO, userLogged.getDataNascimento());
        values.put(USERLOGGED_IMAGE, userLogged.getImagem());
        values.put(USERLOGGED_ISLOGGEDIN, userLogged.getIsLoggedIn());

        db.insert(TABELA_USERLOGGED, null, values);
        db.close();
    }
    //DELETE USERLOGGED
    void deleteUserLogged(UserLogged userLogged){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_USERLOGGED, USERLOGGED_ID + " = ?", new String[]{String.valueOf(userLogged.getId())});

        db.close();
    }
    //GET USERLOGGED
    UserLogged getUserLogged(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_USERLOGGED, new String[] {USERLOGGED_ID, USERLOGGED_EMAIL,
                USERLOGGED_TELEFONE, USERLOGGED_NOME, USERLOGGED_DATANASCIMENTO, USERLOGGED_IMAGE, USERLOGGED_ISLOGGEDIN}, USERLOGGED_ID + " = ?",
                new String[]{String.valueOf(1)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            try {
                UserLogged userLogged1 = new UserLogged(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5),
                        Integer.parseInt(cursor.getString(6)));
                return userLogged1;
            }catch (Exception e){
               return null;
            }
        }else{
            return null;
        }
    }
    //UPDATE USERLOGGED (login)
    void userLogin(UserLogged userLogged){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERLOGGED_EMAIL, userLogged.getEmail());
        values.put(USERLOGGED_TELEFONE, userLogged.getTelefone());
        values.put(USERLOGGED_NOME, userLogged.getNome());
        values.put(USERLOGGED_DATANASCIMENTO, userLogged.getDataNascimento());
        values.put(USERLOGGED_IMAGE, userLogged.getImagem());
        values.put(USERLOGGED_ISLOGGEDIN, 1);

        db.update(TABELA_USERLOGGED, values, USERLOGGED_ID + " = ?",
                new String[] {String.valueOf(1)});
    }
    //UPDATE USERLOGGED (logout)
    void userLogout(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERLOGGED_EMAIL, "None");
        values.put(USERLOGGED_TELEFONE, "None");
        values.put(USERLOGGED_NOME, "None");
        values.put(USERLOGGED_DATANASCIMENTO, "None");
        values.put(USERLOGGED_IMAGE, "None");
        values.put(USERLOGGED_ISLOGGEDIN, "0");

        db.update(TABELA_USERLOGGED, values, USERLOGGED_ID + " = ?",
                new String[] {String.valueOf(1)});
    }

    /** CRUD INSPECAOONGOING */
    //ADICIONAR INSPECAOONGOING
    void addInspecaoOnGoing(InspecaoOnGoing inspecaoOnGoing){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(INSPECAOONGOING_LOCALIDADE, inspecaoOnGoing.getLocalidade());
        values.put(INSPECAOONGOING_MORADA, inspecaoOnGoing.getMorada());
        values.put(INSPECAOONGOING_CODIGOPOSTAL, inspecaoOnGoing.getCodigoPostal());
        values.put(INSPECAOONGOING_ENTRADA, inspecaoOnGoing.getEntrada());
        values.put(INSPECAOONGOING_INSPECAOONGOING, inspecaoOnGoing.getIsInspecaoOnGoing());

        db.insert(TABELA_INSPECAOONGOING, null, values);
        db.close();
    }
    //DELETE INSPECAOONGOING
    void deleteInspecaoOnGoing(InspecaoOnGoing inspecaoOnGoing){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_INSPECAOONGOING, INSPECAOONGOING_ID + " = ?", new String[]{String.valueOf(inspecaoOnGoing.getId())});
        db.close();
    }
    //GET INSPECAOONGOING
    InspecaoOnGoing getInspecaoOnGoing(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_INSPECAOONGOING, new String[] {INSPECAOONGOING_ID, INSPECAOONGOING_LOCALIDADE,
                        INSPECAOONGOING_MORADA, INSPECAOONGOING_CODIGOPOSTAL, INSPECAOONGOING_INSPECAOONGOING}, INSPECAOONGOING_ID + " = ?",
                new String[]{String.valueOf(1)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            try {
                InspecaoOnGoing inspecaoOnGoing1 = new InspecaoOnGoing(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        Integer.parseInt(cursor.getString(5)));
                return inspecaoOnGoing1;
            }catch (Exception e){
                return null;
            }
        }else{
            return null;
        }
    }
    //UPDATE INSPECAOONGOING (começar a inspeção)
    void comecarInspecao(InspecaoOnGoing inspecaoOnGoing){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INSPECAOONGOING_LOCALIDADE, inspecaoOnGoing.getLocalidade());
        values.put(INSPECAOONGOING_MORADA, inspecaoOnGoing.getMorada());
        values.put(INSPECAOONGOING_CODIGOPOSTAL, inspecaoOnGoing.getCodigoPostal());
        values.put(INSPECAOONGOING_ENTRADA, inspecaoOnGoing.getEntrada());
        values.put(INSPECAOONGOING_INSPECAOONGOING, 1);

        db.update(TABELA_INSPECAOONGOING, values, INSPECAOONGOING_ID + " = ?",
                new String[] {String.valueOf(1)});
    }
    //UPDATE USERLOGGED (logout)
    void acabarInspecao(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INSPECAOONGOING_LOCALIDADE, "None");
        values.put(INSPECAOONGOING_MORADA, "None");
        values.put(INSPECAOONGOING_CODIGOPOSTAL, "None");
        values.put(INSPECAOONGOING_ENTRADA, "None");
        values.put(INSPECAOONGOING_INSPECAOONGOING, "0");

        db.update(TABELA_INSPECAOONGOING, values, INSPECAOONGOING_ID + " = ?",
                new String[] {String.valueOf(1)});
    }
}
