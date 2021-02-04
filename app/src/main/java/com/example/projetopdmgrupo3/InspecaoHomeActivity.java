package com.example.projetopdmgrupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.projetopdmgrupo3.models.Cliente;
import com.example.projetopdmgrupo3.models.InspecaoOnGoing;
import com.example.projetopdmgrupo3.models.Obra;
import com.example.projetopdmgrupo3.models.RegistoHoras;

public class InspecaoHomeActivity extends AppCompatActivity {

    BaseDadosLocal db = new BaseDadosLocal(this);

    InspecaoOnGoing inspecaoOnGoing;
    Obra obra;
    Cliente cliente;
    RegistoHoras registoHoras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspecao_home);
        inspecaoOnGoing = db.getInspecaoOnGoing();
        if(inspecaoOnGoing != null){
            if(inspecaoOnGoing.getIsInspecaoOnGoing() == 0){
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
        obra = inspecaoOnGoing.getObra();
        cliente = inspecaoOnGoing.getCliente();
        registoHoras = db.getRegistoHorasByClienteIdObraId(obra.getId(), cliente.getId());
    }

    @Override
    public void onBackPressed() {

    }
}