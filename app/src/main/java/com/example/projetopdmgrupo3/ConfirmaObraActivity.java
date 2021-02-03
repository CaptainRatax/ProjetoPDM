package com.example.projetopdmgrupo3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projetopdmgrupo3.models.Cliente;
import com.example.projetopdmgrupo3.models.Obra;

public class ConfirmaObraActivity extends AppCompatActivity {

    Cliente cliente;
    Obra obra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_obra);
        Bundle extras = getIntent().getExtras();
        constructClienteObra(extras);
        Toast.makeText(this, "Localidade da obra: " + obra.getLocalidade() + " do cliente " + cliente.getNome(), Toast.LENGTH_LONG).show();
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