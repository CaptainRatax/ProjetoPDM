package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testlogin.retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.example.testlogin.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    Api Api = new Api();
    Button login;
    boolean permissionsAccepted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this::login);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.INTERNET)
                .withListener(new PermissionListener() {
                                  @Override
                                  public void onPermissionGranted(PermissionGrantedResponse response) {
                                      permissionsAccepted=true;
                                  }

                                  @Override
                                  public void onPermissionDenied(PermissionDeniedResponse response) {
                                      Toast.makeText(MainActivity.this, "You must accept the Internet permission!", Toast.LENGTH_SHORT).show();
                                      permissionsAccepted=false;
                                  }

                                  @Override
                                  public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                  }
                              }
                ).check();

    }
    public void login(View view) {
        if(permissionsAccepted){
            String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
            String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
            if (email.length() > 0 && password.length() > 0) {
                String json = "{ \"Email\": \"" + email + "\", \"Password\": \"" + password + "\"}";
                Api.login(json,getApplicationContext());
            } else {
                Toast.makeText(getApplicationContext(), "Email ou Password errados", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "TEM QUE ACEITAR AS PERMISSÕES!!!!", Toast.LENGTH_LONG).show();
        }
    }

}