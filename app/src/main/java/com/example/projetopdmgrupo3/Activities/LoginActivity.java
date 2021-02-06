package com.example.projetopdmgrupo3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetopdmgrupo3.server.BaseDadosLocal;
import com.example.projetopdmgrupo3.R;
import com.example.projetopdmgrupo3.server.RetrofitClient;
import com.example.projetopdmgrupo3.models.UserLogged;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    BaseDadosLocal db = new BaseDadosLocal(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        UserLogged userLogged;
        userLogged = db.getUserLogged();
        if(userLogged != null){
            if(userLogged.getIsLoggedIn() == 1){
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        }else{
            db.addUserLogged(new UserLogged(0,"None", "None", "None", "None", "None", 0));
        }
        setContentView(R.layout.activity_login);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        EditText edit_email = (EditText) findViewById(R.id.edit_email_login);
        EditText edit_password = (EditText) findViewById(R.id.edit_password_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edit_email.getText().toString();
                String password = edit_password.getText().toString();
                if(isNetworkAvailable()){
                    if(isValidEmail(email)){
                        if(!password.equals("")){
                            String json = "{ \"Email\": \"" + email + "\", \"Password\": \"" + password + "\"}";
                            JsonObject body = new JsonParser().parse(json).getAsJsonObject();
                            Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().login(body);
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if(response.body() != null){
                                        if(response.body().get("Success").getAsBoolean()){
                                            if(response.body().get("RequestCode").getAsInt() == 200){
                                                JsonObject inspecionador = response.body().get("Inspecionador").getAsJsonObject();
                                                UserLogged userLogged = new UserLogged(
                                                        1,
                                                        inspecionador.get("Id").getAsInt(),
                                                        inspecionador.get("Email").getAsString(),
                                                        inspecionador.get("Telefone").getAsString(),
                                                        inspecionador.get("Nome").getAsString(),
                                                        inspecionador.get("DataNascimento").getAsString(),
                                                        inspecionador.get("Imagem").getAsString(),
                                                        1
                                                );
                                                db.userLogin(userLogged);
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(LoginActivity.this, "Email ou Password incorretos!", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(LoginActivity.this, "Email ou Password incorretos!", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Email ou Password incorretos!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{
                            Toast.makeText(LoginActivity.this, "A password não pode estar vazia!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Email inválido!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Sem conexão à internet :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}