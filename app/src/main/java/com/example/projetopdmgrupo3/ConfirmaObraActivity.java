package com.example.projetopdmgrupo3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetopdmgrupo3.models.Cliente;
import com.example.projetopdmgrupo3.models.InspecaoOnGoing;
import com.example.projetopdmgrupo3.models.Obra;
import com.example.projetopdmgrupo3.models.RegistoHoras;
import com.example.projetopdmgrupo3.models.UserLogged;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmaObraActivity extends AppCompatActivity {

    BaseDadosLocal db = new BaseDadosLocal(this);

    RegistoHoras registoHoras;
    InspecaoOnGoing inspecaoOnGoing;
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
                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                String dataAtual = localDateTime.format(formatter);
                UserLogged userLogged = db.getUserLogged();

                if(isNetworkAvailable())
                {
                    String json = "{ " +
                            "\"IdObra\": \"" + obra.getId() + "\", " +
                            "\"IdInspecionador\": \"" + userLogged.getIdInspecionador() + "\", " +
                            "\"DataHora\": \"" + dataAtual + "\"}";
                    JsonObject body = new JsonParser().parse(json).getAsJsonObject();
                    Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().entradaNaObra(body);

                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.body() != null) {
                                if (response.body().get("Success").getAsBoolean()) {
                                    if (response.body().get("RequestCode").getAsInt() == 200) {
                                        //Se a entrada já tiver sido registada na base de dados online tanto a entrada como a saida vão vazias
                                        registoHoras = new RegistoHoras("", "", obra.getId(), userLogged.getIdInspecionador());
                                        registoHoras.setId(db.addRegistoHora(registoHoras));

                                        InspecaoOnGoing inspecaoOnGoing = new InspecaoOnGoing(1, obra, cliente, 1);
                                        db.comecarInspecao(inspecaoOnGoing);

                                        Intent intent = new Intent(getApplicationContext(), InspecaoHomeActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        try {
                                            Toast.makeText(ConfirmaObraActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                        }catch (Exception err){
                                            Toast.makeText(ConfirmaObraActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else{
                                    try {
                                        Toast.makeText(ConfirmaObraActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                    }catch (Exception err){
                                        Toast.makeText(ConfirmaObraActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(ConfirmaObraActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(ConfirmaObraActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    //Se a entrada não tiver sido registada na base de dados online então ele guarda a data atual da entrada e envia a saida vazia
                    registoHoras = new RegistoHoras(dataAtual, "", obra.getId(), userLogged.getIdInspecionador());
                    registoHoras.setId(db.addRegistoHora(registoHoras));

                    InspecaoOnGoing inspecaoOnGoing = new InspecaoOnGoing(1, obra, cliente, 1);
                    db.comecarInspecao(inspecaoOnGoing);

                    Intent intent = new Intent(getApplicationContext(), InspecaoHomeActivity.class);
                    startActivity(intent);

                }
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

}