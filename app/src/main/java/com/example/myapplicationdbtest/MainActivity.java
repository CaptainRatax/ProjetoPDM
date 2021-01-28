package com.example.myapplicationdbtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editCodigo, editNome, editTelefone, editEmail;
    Button btnLimpar, btnSalvar, btnExcluir;
    ListView listViewClientes;

    LocalDataBase db = new LocalDataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNome = (EditText) findViewById(R.id.editNome);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        editEmail = (EditText) findViewById(R.id.editEmail);

        btnLimpar = (Button) findViewById(R.id.btnLimpar);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        listViewClientes = (ListView) findViewById(R.id.listViewClientes);

        /*TESTE DO CRUD*/

        db.addCliente(new Cliente("IIIRataxIII","920392834","someone@live.com"));
        db.addCliente(new Cliente("Carlos","9203928334","asdsadasd@live.com"));
        db.addCliente(new Cliente("Afonso","920392839243","someoasdasne@live.com"));
        db.addCliente(new Cliente("Tamagochi","92039283745","someaaaone@live.com"));
        db.addCliente(new Cliente("A minha pissa","920392834","somsdaseone@live.com"));
        Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_LONG).show();

    }
}