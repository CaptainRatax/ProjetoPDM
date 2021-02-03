package com.example.projetopdmgrupo3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetopdmgrupo3.models.Cliente;
import com.example.projetopdmgrupo3.models.Obra;

public class ConfirmaObraActivity extends AppCompatActivity {

    Cliente cliente;
    Obra obra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_confirma_obra);
        Bundle extras = getIntent().getExtras();
        constructClienteObra(extras);

        TextView txt_localidade = findViewById(R.id.txt_localidade);
        TextView txt_morada = findViewById(R.id.txt_morada);
        TextView txt_codigoPostal = findViewById(R.id.txt_codigoPostal);
        TextView txt_nome = findViewById(R.id.txt_nome);
        TextView txt_email = findViewById(R.id.txt_email);
        TextView txt_telefone = findViewById(R.id.txt_telefone);
        txt_localidade.setText(obra.getLocalidade());
        txt_morada.setText(obra.getMorada());
        txt_codigoPostal.setText(obra.getCodigoPostal());
        txt_nome.setText(cliente.getNome());
        txt_email.setText(cliente.getEmail());
        txt_telefone.setText(cliente.getTelefone());

        Button btn_cancelar = findViewById(R.id.btn_cancelar);
        Button btn_confirmar = findViewById(R.id.btn_confirmar);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmaObraActivity.this, "Confirmado!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void constructClienteObra(Bundle extras) {
        obra = new Obra(
                extras.getInt("OBRAID"),
                extras.getString("OBRALOCALIDADE"),
                extras.getString("OBRAMORADA"),
                extras.getString("OBRACODIGOPOSTAL"),
                extras.getInt("OBRAIDCLIENTE"),
                extras.getString("OBRACREATEDAT")
        );
        cliente = new Cliente(
                extras.getInt("CLIENTEID"),
                extras.getString("CLIENTENOME"),
                extras.getString("CLIENTEEMAIL"),
                extras.getString("CLIENTETELEFONE"),
                extras.getString("CLIENTEDATANASCIMENTO"),
                extras.getString("CLIENTECREATEDAT")
        );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

}