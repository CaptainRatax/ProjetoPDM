package com.example.projetopdmgrupo3.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.projetopdmgrupo3.Activities.AdicionarCasoActivity;
import com.example.projetopdmgrupo3.Activities.CasoViewActivity;
import com.example.projetopdmgrupo3.Activities.ConfirmaObraActivity;
import com.example.projetopdmgrupo3.Activities.FinalizarInspecaoActivity;
import com.example.projetopdmgrupo3.Activities.HomeActivity;
import com.example.projetopdmgrupo3.Activities.InspecaoHomeActivity;
import com.example.projetopdmgrupo3.models.Caso;
import com.example.projetopdmgrupo3.models.DeleteCasosSynced;
import com.example.projetopdmgrupo3.models.Fotografia;
import com.example.projetopdmgrupo3.models.InspecaoOnGoing;
import com.example.projetopdmgrupo3.models.RegistoHoras;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    BaseDadosLocal db = new BaseDadosLocal(this);
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        InspecaoOnGoing inspecaoOnGoing = db.getInspecaoOnGoing();
        if (!db.isDataSynced()){
            ArrayList<RegistoHoras> registoHoras = db.getTodosRegistosHoras();
            for (RegistoHoras registoHorasElement:registoHoras) {
                if (!registoHorasElement.getEntrada().equals("") && registoHorasElement.getSaida().equals("")){
                    String json = "{ " +
                            "\"IdObra\": \"" + registoHorasElement.getIdObra() + "\", " +
                            "\"IdInspecionador\": \"" + registoHorasElement.getIdInspecionador() + "\", " +
                            "\"DataHora\": \"" + registoHorasElement.getEntrada() + "\"}";
                    JsonObject body = new JsonParser().parse(json).getAsJsonObject();
                    Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().entradaNaObra(body);

                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.body() != null) {
                                if (response.body().get("Success").getAsBoolean()) {
                                    if (response.body().get("RequestCode").getAsInt() == 200) {
                                        registoHorasElement.setEntrada("");
                                        db.updateRegistoHoras(registoHorasElement);
                                    }
                                    else{
                                        try {
                                            Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                        }catch (Exception err){
                                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else{
                                    try {
                                        Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                    }catch (Exception err){
                                        Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (!registoHorasElement.getEntrada().equals("") && !registoHorasElement.getSaida().equals("")){
                    String json = "{ " +
                            "\"IdObra\": \"" + registoHorasElement.getIdObra() + "\", " +
                            "\"IdInspecionador\": \"" + registoHorasElement.getIdInspecionador() + "\", " +
                            "\"DataHora\": \"" + registoHorasElement.getEntrada() + "\"}";
                    JsonObject body = new JsonParser().parse(json).getAsJsonObject();
                    Call<JsonObject> call23 = RetrofitClient.getInstance().getMyApi().entradaNaObra(body);

                    call23.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.body() != null) {
                                if (response.body().get("Success").getAsBoolean()) {
                                    if (response.body().get("RequestCode").getAsInt() == 200) {
                                        String json = "{ " +
                                                "\"IdObra\": \"" + registoHorasElement.getIdObra() + "\", " +
                                                "\"IdInspecionador\": \"" + registoHorasElement.getIdInspecionador() + "\", " +
                                                "\"DataHora\": \"" + registoHorasElement.getSaida() + "\", " +
                                                "\"Nota\": \"" + registoHorasElement.getNota() + "\", " +
                                                "\"Observacoes\": \"" + registoHorasElement.getObservacoes() + "\"}";
                                        JsonObject body = new JsonParser().parse(json).getAsJsonObject();
                                        Call<JsonObject> call2 = RetrofitClient.getInstance().getMyApi().finalizarInspecao(body);
                                        call2.enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                if(response.body() != null) {
                                                    if (response.body().get("Success").getAsBoolean()) {
                                                        if (response.body().get("RequestCode").getAsInt() == 200) {
                                                            registoHorasElement.setSaida("");
                                                            db.deleteRegistoHoraById(registoHorasElement.getId());
                                                        }else{
                                                            try {
                                                                Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                                            }catch (Exception err){
                                                                Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }else {
                                                        try {
                                                            Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                                        }catch (Exception err){
                                                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }else{
                                                    Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    else{
                                        try {
                                            Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                        }catch (Exception err){
                                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else{
                                    try {
                                        Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                    }catch (Exception err){
                                        Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(!registoHorasElement.getSaida().equals("")){
                    String json = "{ " +
                            "\"IdObra\": \"" + registoHorasElement.getIdObra() + "\", " +
                            "\"IdInspecionador\": \"" + registoHorasElement.getIdInspecionador() + "\", " +
                            "\"DataHora\": \"" + registoHorasElement.getSaida() + "\", " +
                            "\"Nota\": \"" + registoHorasElement.getNota() + "\", " +
                            "\"Observacoes\": \"" + registoHorasElement.getObservacoes() + "\"}";
                    JsonObject body = new JsonParser().parse(json).getAsJsonObject();
                    Call<JsonObject> call2 = RetrofitClient.getInstance().getMyApi().finalizarInspecao(body);
                    call2.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.body() != null) {
                                if (response.body().get("Success").getAsBoolean()) {
                                    if (response.body().get("RequestCode").getAsInt() == 200) {
                                        db.deleteRegistoHoraById(registoHorasElement.getId());
                                    }else{
                                        try {
                                            Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                        }catch (Exception err){
                                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else {
                                    try {
                                        Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                    }catch (Exception err){
                                        Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            ArrayList<Caso> casos = db.getAllCasosNotSynced();
            for (Caso caso:casos) {
                if (caso.getIsSynced()==0){
                    ArrayList<Fotografia> imagens = new ArrayList<Fotografia>();
                    imagens = db.getAllFotografiasByIdCaso(caso.getId());
                    String json = "{ " +
                            "\"Titulo\": \"" + caso.getTitulo() + "\", " +
                            "\"Descricao\": \"" + caso.getDescricao() + "\", " +
                            "\"IdObra\": \"" + caso.getIdObra() + "\", " +
                            "\"IdInspecionador\": \"" + caso.getIdInspecionador() + "\", " +
                            "\"ListaDeImagens\":[";
                    if(imagens.size()!=0){
                        for (Fotografia imagem:imagens) {
                            json = json + "\"" + imagem.getFotografia() + "\", ";
                        }
                        json = json.substring(0, json.length()-2);
                    }
                    json = json + "]}";
                    JsonObject body = new JsonParser().parse(json).getAsJsonObject();
                    Call<JsonObject> call3 = RetrofitClient.getInstance().getMyApi().criarCaso(body);
                    call3.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.body() != null) {
                                if (response.body().get("Success").getAsBoolean()) {
                                    if (response.body().get("RequestCode").getAsInt() == 200) {
                                        if(inspecaoOnGoing.getIsInspecaoOnGoing()==0){
                                            db.deleteCasoById(caso.getId());
                                        }else{
                                            String str = response.body().get("Message").getAsString();
                                            str = str.substring(14, str.length());
                                            String[] splited = str.split("\\s+");
                                            caso.setIdIsSynced(Integer.parseInt(splited[0]));
                                            caso.setIsSynced(1);
                                            db.updateCaso(caso);
                                        }
                                    }else{
                                        try {
                                            Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                        }catch (Exception err){
                                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else {
                                    try {
                                        Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                    }catch (Exception err){
                                        Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            ArrayList<DeleteCasosSynced> deleteCasosSynceds = db.getAllDeleteCasosSynced();
            for (DeleteCasosSynced deleteCasoSynced:deleteCasosSynceds) {
                Call<JsonObject> call4 = RetrofitClient.getInstance().getMyApi().deleteCaso(deleteCasoSynced.getIdIsSynced());
                call4.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body() != null) {
                            if (response.body().get("Success").getAsBoolean()) {
                                if (response.body().get("RequestCode").getAsInt() == 200) {

                                }else{
                                    try {
                                        Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                    }catch (Exception err){
                                        Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                try {
                                    Toast.makeText(MyService.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                }catch (Exception err){
                                    Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(MyService.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        db.updateIsDataSynced(true);
        stopService(new Intent(this,MyService.class));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Dados Sincronizados!", Toast.LENGTH_SHORT).show();
    }
}
