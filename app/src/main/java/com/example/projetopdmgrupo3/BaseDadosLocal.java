package com.example.projetopdmgrupo3;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.projetopdmgrupo3.models.Cliente;
import com.example.projetopdmgrupo3.models.InspecaoOnGoing;
import com.example.projetopdmgrupo3.models.Obra;
import com.example.projetopdmgrupo3.models.RegistoHoras;
import com.example.projetopdmgrupo3.models.UserLogged;

import java.util.ArrayList;
import java.util.List;

public class BaseDadosLocal extends SQLiteOpenHelper {

    private static final int VERSAO_BD = 1;
    private static final String NOME_BD = "bd_PDMGrupo3";

    /**TABELAS (TABELA_<NOMETABELA>)*/
    private static final String TABELA_USERLOGGED = "tb_userLoged";
    private static final String TABELA_INSPECAOONGOING = "tb_inspecaoOnGoing";
    private static final String TABELA_REGISTOHORAS = "tb_registoHoras";

    /**CAMPOS (<NOMETABELA>_<NOMECAMPO>) */
    //Tabela userLoged
    private static final String USERLOGGED_ID = "ID";
    private static final String USERLOGGED_IDINSPECIONADOR = "IdInspecionador";
    private static final String USERLOGGED_EMAIL = "Email";
    private static final String USERLOGGED_TELEFONE = "Telefone";
    private static final String USERLOGGED_NOME = "Nome";
    private static final String USERLOGGED_DATANASCIMENTO = "DataNascimento";
    private static final String USERLOGGED_IMAGE = "Imagem";
    private static final String USERLOGGED_ISLOGGEDIN = "IsLoggedIn";
    //Tabela inspecaoOnGoing
    private static final String INSPECAOONGOING_ID = "ID";
    private static final String INSPECAOONGOING_OBRAID = "ObraId";
    private static final String INSPECAOONGOING_OBRALOCALIDADE = "ObraLocalidade";
    private static final String INSPECAOONGOING_OBRAMORADA = "ObraMorada";
    private static final String INSPECAOONGOING_OBRACODIGOPOSTAL = "ObraCodigoPostal";
    private static final String INSPECAOONGOING_OBRAIDCLIENTE = "ObraIdCliente";
    private static final String INSPECAOONGOING_OBRACREATEDAT = "ObraCreatedAt";
    private static final String INSPECAOONGOING_CLIENTEID = "ClienteId";
    private static final String INSPECAOONGOING_CLIENTENOME = "ClienteNome";
    private static final String INSPECAOONGOING_CLIENTEEMAIL = "ClienteEmail";
    private static final String INSPECAOONGOING_CLIENTETELEFONE = "ClienteTelefone";
    private static final String INSPECAOONGOING_CLIENTEDATANASCIMENTO = "ClienteDataNascimento";
    private static final String INSPECAOONGOING_CLIENTECREATEDAT = "ClienteCreatedAt";
    private static final String INSPECAOONGOING_INSPECAOONGOING = "InspecaoOnGoing";
    //Tabela registoHoras
    private static final String REGISTOHORAS_ID = "ID";
    private static final String REGISTOHORAS_ENTRADA = "Entrada";
    private static final String REGISTOHORAS_SAIDA = "Saida";
    private static final String REGISTOHORAS_IDOBRA = "IdObra";
    private static final String REGISTOHORAS_IDINSPECIONADOR = "IdInspecionador";

    public BaseDadosLocal(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_CREATE_TABELA_USERLOGGED = "CREATE TABLE " + TABELA_USERLOGGED + "("
                + USERLOGGED_ID + " INTEGER PRIMARY KEY, "
                + USERLOGGED_IDINSPECIONADOR + " INTEGER, " + USERLOGGED_EMAIL + " TEXT, "
                + USERLOGGED_TELEFONE + " TEXT, " + USERLOGGED_NOME + " TEXT, "
                + USERLOGGED_DATANASCIMENTO + " TEXT, " + USERLOGGED_IMAGE + " TEXT, " + USERLOGGED_ISLOGGEDIN + " INTEGER)";
        String QUERY_CREATE_TABELA_INSPECAOONGOING = "CREATE TABLE " + TABELA_INSPECAOONGOING + "("
                + INSPECAOONGOING_ID + " INTEGER PRIMARY KEY, " + INSPECAOONGOING_OBRAID + " INTEGER, "
                + INSPECAOONGOING_OBRALOCALIDADE + " TEXT, " + INSPECAOONGOING_OBRAMORADA + " TEXT, "
                + INSPECAOONGOING_OBRACODIGOPOSTAL + " TEXT, " + INSPECAOONGOING_OBRAIDCLIENTE + " INTEGER, "
                + INSPECAOONGOING_OBRACREATEDAT + " TEXT, " + INSPECAOONGOING_CLIENTEID + " INTEGER, "
                + INSPECAOONGOING_CLIENTENOME + " TEXT, " + INSPECAOONGOING_CLIENTEEMAIL + " TEXT, "
                + INSPECAOONGOING_CLIENTETELEFONE + " TEXT, " + INSPECAOONGOING_CLIENTEDATANASCIMENTO + " TEXT, "
                + INSPECAOONGOING_CLIENTECREATEDAT + " TEXT, " + INSPECAOONGOING_INSPECAOONGOING + " INTEGER)";
        String QUERY_CREATE_TABLE_REGISTOHORAS = "CREATE TABLE " + TABELA_REGISTOHORAS + "("
                + REGISTOHORAS_ID + " INTEGER PRIMARY KEY, "
                + REGISTOHORAS_ENTRADA + " TEXT, " + REGISTOHORAS_SAIDA + " TEXT, "
                + REGISTOHORAS_IDOBRA + " INTEGER, " + REGISTOHORAS_IDINSPECIONADOR + " INTEGER)";
        db.execSQL(QUERY_CREATE_TABELA_USERLOGGED);
        db.execSQL(QUERY_CREATE_TABELA_INSPECAOONGOING);
        db.execSQL(QUERY_CREATE_TABLE_REGISTOHORAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /** CRUD USERLOGGED */
    //ADICIONAR USERLOGGED
    void addUserLogged(UserLogged userLogged){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USERLOGGED_IDINSPECIONADOR, userLogged.getIdInspecionador());
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
        Cursor cursor = db.query(TABELA_USERLOGGED, new String[] {USERLOGGED_ID, USERLOGGED_IDINSPECIONADOR, USERLOGGED_EMAIL,
                USERLOGGED_TELEFONE, USERLOGGED_NOME, USERLOGGED_DATANASCIMENTO, USERLOGGED_IMAGE, USERLOGGED_ISLOGGEDIN}, USERLOGGED_ID + " = ?",
                new String[]{String.valueOf(1)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            try {
                UserLogged userLogged1 = new UserLogged(Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6),
                        Integer.parseInt(cursor.getString(7)));
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
        values.put(USERLOGGED_IDINSPECIONADOR, userLogged.getIdInspecionador());
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
        values.put(USERLOGGED_IDINSPECIONADOR, "None");
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

        values.put(INSPECAOONGOING_OBRAID, inspecaoOnGoing.getObra().getId());
        values.put(INSPECAOONGOING_OBRALOCALIDADE, inspecaoOnGoing.getObra().getLocalidade());
        values.put(INSPECAOONGOING_OBRAMORADA, inspecaoOnGoing.getObra().getMorada());
        values.put(INSPECAOONGOING_OBRACODIGOPOSTAL, inspecaoOnGoing.getObra().getCodigoPostal());
        values.put(INSPECAOONGOING_OBRAIDCLIENTE, inspecaoOnGoing.getObra().getIdCliente());
        values.put(INSPECAOONGOING_OBRACREATEDAT, inspecaoOnGoing.getObra().getCreatedAt());
        values.put(INSPECAOONGOING_CLIENTEID, inspecaoOnGoing.getCliente().getId());
        values.put(INSPECAOONGOING_CLIENTENOME, inspecaoOnGoing.getCliente().getNome());
        values.put(INSPECAOONGOING_CLIENTEEMAIL, inspecaoOnGoing.getCliente().getEmail());
        values.put(INSPECAOONGOING_CLIENTETELEFONE, inspecaoOnGoing.getCliente().getTelefone());
        values.put(INSPECAOONGOING_CLIENTEDATANASCIMENTO, inspecaoOnGoing.getCliente().getDataNascimento());
        values.put(INSPECAOONGOING_CLIENTECREATEDAT, inspecaoOnGoing.getCliente().getCreatedAt());
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
        Cursor cursor = db.query(TABELA_INSPECAOONGOING, new String[] {INSPECAOONGOING_ID, INSPECAOONGOING_OBRAID,
                        INSPECAOONGOING_OBRALOCALIDADE, INSPECAOONGOING_OBRAMORADA, INSPECAOONGOING_OBRACODIGOPOSTAL,
                        INSPECAOONGOING_OBRAIDCLIENTE, INSPECAOONGOING_OBRACREATEDAT, INSPECAOONGOING_CLIENTEID,
                        INSPECAOONGOING_CLIENTENOME, INSPECAOONGOING_CLIENTEEMAIL, INSPECAOONGOING_CLIENTETELEFONE,
                        INSPECAOONGOING_CLIENTEDATANASCIMENTO, INSPECAOONGOING_CLIENTECREATEDAT, INSPECAOONGOING_INSPECAOONGOING}, INSPECAOONGOING_ID + " = ?",
                new String[]{String.valueOf(1)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            try {
                Obra obra = new Obra(
                        Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        Integer.parseInt(cursor.getString(5)),
                        cursor.getString(6)
                );
                Cliente cliente = new Cliente(
                        Integer.parseInt(cursor.getString(7)),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12)
                );
                InspecaoOnGoing inspecaoOnGoing1 = new InspecaoOnGoing(
                        Integer.parseInt(cursor.getString(0)),
                        obra,
                        cliente,
                        Integer.parseInt(cursor.getString(13))
                        );
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
        values.put(INSPECAOONGOING_OBRAID, inspecaoOnGoing.getObra().getId());
        values.put(INSPECAOONGOING_OBRALOCALIDADE, inspecaoOnGoing.getObra().getLocalidade());
        values.put(INSPECAOONGOING_OBRAMORADA, inspecaoOnGoing.getObra().getMorada());
        values.put(INSPECAOONGOING_OBRACODIGOPOSTAL, inspecaoOnGoing.getObra().getCodigoPostal());
        values.put(INSPECAOONGOING_OBRAIDCLIENTE, inspecaoOnGoing.getObra().getIdCliente());
        values.put(INSPECAOONGOING_OBRACREATEDAT, inspecaoOnGoing.getObra().getCreatedAt());
        values.put(INSPECAOONGOING_CLIENTEID, inspecaoOnGoing.getCliente().getId());
        values.put(INSPECAOONGOING_CLIENTENOME, inspecaoOnGoing.getCliente().getNome());
        values.put(INSPECAOONGOING_CLIENTEEMAIL, inspecaoOnGoing.getCliente().getEmail());
        values.put(INSPECAOONGOING_CLIENTETELEFONE, inspecaoOnGoing.getCliente().getTelefone());
        values.put(INSPECAOONGOING_CLIENTEDATANASCIMENTO, inspecaoOnGoing.getCliente().getDataNascimento());
        values.put(INSPECAOONGOING_CLIENTECREATEDAT, inspecaoOnGoing.getCliente().getCreatedAt());
        values.put(INSPECAOONGOING_INSPECAOONGOING, 1);

        db.update(TABELA_INSPECAOONGOING, values, INSPECAOONGOING_ID + " = ?",
                new String[] {String.valueOf(1)});
    }
    //UPDATE INSPECAOONGOING (acabar inspeção)
    void acabarInspecao(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INSPECAOONGOING_OBRAID, 0);
        values.put(INSPECAOONGOING_OBRALOCALIDADE, "None");
        values.put(INSPECAOONGOING_OBRAMORADA, "None");
        values.put(INSPECAOONGOING_OBRACODIGOPOSTAL, "None");
        values.put(INSPECAOONGOING_OBRAIDCLIENTE, 0);
        values.put(INSPECAOONGOING_OBRACREATEDAT, "None");
        values.put(INSPECAOONGOING_CLIENTEID, 0);
        values.put(INSPECAOONGOING_CLIENTENOME, "None");
        values.put(INSPECAOONGOING_CLIENTEEMAIL, "None");
        values.put(INSPECAOONGOING_CLIENTETELEFONE, "None");
        values.put(INSPECAOONGOING_CLIENTEDATANASCIMENTO, "None");
        values.put(INSPECAOONGOING_CLIENTECREATEDAT, "None");
        values.put(INSPECAOONGOING_INSPECAOONGOING, 0);

        db.update(TABELA_INSPECAOONGOING, values, INSPECAOONGOING_ID + " = ?",
                new String[] {String.valueOf(1)});
    }

    /** CRUD REGISTOHORAS */
    //ADICIONAR REGISTOHORAS
    int addRegistoHora(RegistoHoras registoHoras){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(REGISTOHORAS_ENTRADA, registoHoras.getEntrada());
        values.put(REGISTOHORAS_SAIDA, registoHoras.getSaida());
        values.put(REGISTOHORAS_IDOBRA, registoHoras.getIdObra());
        values.put(REGISTOHORAS_IDINSPECIONADOR, registoHoras.getEntrada());

        long id = db.insert(TABELA_REGISTOHORAS, null, values);
        db.close();
        return (int) id;
    }
    //DELETE REGISTOHORAS
    void deleteRegistoHoraById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_REGISTOHORAS, REGISTOHORAS_ID + " = ?", new String[]{String.valueOf(id)});

        db.close();
    }
    //GET REGISTOHORAS By Id
    RegistoHoras getRegistoHorasById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_REGISTOHORAS, new String[] {REGISTOHORAS_ID, REGISTOHORAS_ENTRADA, REGISTOHORAS_SAIDA,
                        REGISTOHORAS_IDOBRA, REGISTOHORAS_IDINSPECIONADOR}, REGISTOHORAS_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            try {
                RegistoHoras registoHoras1 = new RegistoHoras(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)));
                return registoHoras1;
            }catch (Exception e){
                return null;
            }
        }else{
            return null;
        }
    }
    //GET REGISTOHORAS By ClienteId e ObraId
    RegistoHoras getRegistoHorasByClienteIdObraId(int obraId, int clienteId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_REGISTOHORAS, new String[] {REGISTOHORAS_ID, REGISTOHORAS_ENTRADA, REGISTOHORAS_SAIDA,
                        REGISTOHORAS_IDOBRA, REGISTOHORAS_IDINSPECIONADOR}, REGISTOHORAS_IDOBRA + " = ? AND " + REGISTOHORAS_IDINSPECIONADOR + " = ?",
                new String[]{String.valueOf(obraId), String.valueOf(clienteId)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            try {
                RegistoHoras registoHoras1 = new RegistoHoras(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)));
                return registoHoras1;
            }catch (Exception e){
                return null;
            }
        }else{
            return null;
        }
    }
    //GET ALL REGISTOHORAS
    public List<RegistoHoras> getTodosRegistosHoras(){
        List<RegistoHoras> listaRegistoHoras = new ArrayList<RegistoHoras>();

        String query = "SELECT * FROM " + TABELA_REGISTOHORAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                RegistoHoras registoHoras = new RegistoHoras();
                registoHoras.setId(Integer.parseInt(cursor.getString(0)));
                registoHoras.setEntrada(cursor.getString(1));
                registoHoras.setSaida(cursor.getString(2));
                registoHoras.setIdObra(Integer.parseInt(cursor.getString(3)));
                registoHoras.setIdInspecionador(Integer.parseInt(cursor.getString(4)));

                listaRegistoHoras.add(registoHoras);
            }while(cursor.moveToNext());
        }
        return listaRegistoHoras;
    }
    //UPDATE REGISTOHORAS
    void updateRegistoHoras(RegistoHoras registoHoras){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REGISTOHORAS_ENTRADA, registoHoras.getEntrada());
        values.put(REGISTOHORAS_SAIDA, registoHoras.getSaida());
        values.put(REGISTOHORAS_IDOBRA, registoHoras.getIdObra());
        values.put(REGISTOHORAS_IDINSPECIONADOR, registoHoras.getEntrada());

        db.update(TABELA_REGISTOHORAS, values, REGISTOHORAS_ID + " = ?",
                new String[] {String.valueOf(registoHoras.getId())});
    }
}
