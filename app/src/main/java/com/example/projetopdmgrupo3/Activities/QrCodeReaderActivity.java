package com.example.projetopdmgrupo3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projetopdmgrupo3.R;
import com.example.projetopdmgrupo3.server.RetrofitClient;
import com.google.gson.JsonObject;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrCodeReaderActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_qr_code_reader);
        scannerView = findViewById(R.id.zxscan);
        scannerView.setResultHandler(QrCodeReaderActivity.this);
        scannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String result = rawResult.getText();
        String[] resultSplit = result.split(":");

        try {
            int id = Integer.parseInt(resultSplit[1]);

            if (resultSplit[0].equals("obraId") && id > 0) {
                try {

                    if(isNetworkAvailable()){
                        Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().getObraById(id);
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if(response.body() != null) {
                                    if (response.body().get("Success").getAsBoolean()) {
                                        if (response.body().get("RequestCode").getAsInt() == 200) {
                                            JsonObject obraJson = response.body().get("Obra").getAsJsonObject();
                                            JsonObject clienteJson = response.body().get("Cliente").getAsJsonObject();
                                            Intent intent = new Intent(getApplicationContext(), ConfirmaObraActivity.class);
                                            try {
                                                //passar todos os dados da obra pelo intent
                                                intent.putExtra("OBRAID", obraJson.get("Id").getAsInt());
                                                intent.putExtra("OBRALOCALIDADE", obraJson.get("Localidade").getAsString());
                                                intent.putExtra("OBRAMORADA", obraJson.get("Morada").getAsString());
                                                intent.putExtra("OBRACODIGOPOSTAL", obraJson.get("CodigoPostal").getAsString());
                                                intent.putExtra("OBRAIDCLIENTE", obraJson.get("IdCliente").getAsInt());
                                                intent.putExtra("OBRACREATEDAT", obraJson.get("CreatedAt").getAsString());

                                                //passar todos os dados do cliente pelo intent
                                                intent.putExtra("CLIENTEID", clienteJson.get("Id").getAsInt());
                                                intent.putExtra("CLIENTENOME", clienteJson.get("Nome").getAsString());
                                                intent.putExtra("CLIENTEEMAIL", clienteJson.get("Email").getAsString());
                                                intent.putExtra("CLIENTETELEFONE", clienteJson.get("Telefone").getAsString());
                                                intent.putExtra("CLIENTEDATANASCIMENTO", clienteJson.get("DataNascimento").getAsString());
                                                intent.putExtra("CLIENTECREATEDAT", clienteJson.get("CreatedAt").getAsString());
                                            }catch(Exception e){
                                                Toast.makeText(QrCodeReaderActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_LONG).show();
                                                Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
                                                startActivity(intent1);
                                                return;
                                            }
                                            startActivity(intent);
                                        }else if(response.body().get("RequestCode").getAsInt() == 404){
                                            try {
                                                Toast.makeText(QrCodeReaderActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                                scannerView.setResultHandler(QrCodeReaderActivity.this);
                                                scannerView.startCamera();
                                            }catch (Exception err){
                                                Toast.makeText(QrCodeReaderActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }else{
                                        if(response.body().get("RequestCode").getAsInt() == 404) {
                                            try {
                                                Toast.makeText(QrCodeReaderActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                                scannerView.setResultHandler(QrCodeReaderActivity.this);
                                                scannerView.startCamera();
                                            } catch (Exception err) {
                                                Toast.makeText(QrCodeReaderActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }else{
                                    Toast.makeText(QrCodeReaderActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Toast.makeText(QrCodeReaderActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else{
                        Toast.makeText(QrCodeReaderActivity.this, "Sem conexão à internet :(", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception ex) {
                    Toast.makeText(QrCodeReaderActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Código de obra inválido", Toast.LENGTH_SHORT).show();
                scannerView.setResultHandler(QrCodeReaderActivity.this);
                scannerView.startCamera();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Código de obra inválido!", Toast.LENGTH_SHORT).show();
            scannerView.setResultHandler(QrCodeReaderActivity.this);
            scannerView.startCamera();
        }
    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}