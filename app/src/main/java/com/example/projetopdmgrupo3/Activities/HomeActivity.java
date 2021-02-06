package com.example.projetopdmgrupo3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetopdmgrupo3.server.BaseDadosLocal;
import com.example.projetopdmgrupo3.R;
import com.example.projetopdmgrupo3.server.RetrofitClient;
import com.example.projetopdmgrupo3.models.Cliente;
import com.example.projetopdmgrupo3.models.InspecaoOnGoing;
import com.example.projetopdmgrupo3.models.Obra;
import com.example.projetopdmgrupo3.models.UserLogged;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    UserLogged userLogged;
    InspecaoOnGoing inspecaoOnGoing;

    BaseDadosLocal db = new BaseDadosLocal(this);

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        inspecaoOnGoing = db.getInspecaoOnGoing();
        if(inspecaoOnGoing != null){
            if(inspecaoOnGoing.getIsInspecaoOnGoing() == 1){
                Intent intent = new Intent(getApplicationContext(), InspecaoHomeActivity.class);
                startActivity(intent);
            }
        }else{
            db.addInspecaoOnGoing(new InspecaoOnGoing(new Obra(), new Cliente(), 0));
            inspecaoOnGoing = db.getInspecaoOnGoing();
        }
        setContentView(R.layout.activity_home);
        Bundle extras = getIntent().getExtras();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        userLogged = db.getUserLogged();

        View headerView = navigationView.getHeaderView(0);
        TextView txt_userName = (TextView) headerView.findViewById(R.id.userName);
        TextView txt_userEmail = (TextView) headerView.findViewById(R.id.userEmail);
        CircleImageView img_userImage = (CircleImageView) headerView.findViewById(R.id.profile_image);
        TextView txt_BemVindo = (TextView) findViewById(R.id.txt_BemVindo);

        txt_userName.setText(userLogged.getNome());
        txt_userEmail.setText(userLogged.getEmail());
        String bemvindo = "Bem vindo(a) " + userLogged.getNome().split(" ")[0] + "!";
        txt_BemVindo.setText(bemvindo);

        byte[] decodedByte = Base64.decode(userLogged.getImagem(), 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        img_userImage.setImageBitmap(bitmap);
        navigationView.setCheckedItem(R.id.nav_home);
        db = new BaseDadosLocal(this);

        Button btn_lerCodigoQR = (Button) findViewById(R.id.btn_codigoQR);
        Button btn_inspecionar = (Button) findViewById(R.id.btn_inspecionar);

        btn_lerCodigoQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){ // método que verifica se tem permissão para usar a câmara
                    Intent intent = new Intent(getApplicationContext(), QrCodeReaderActivity.class);
                    startActivity(intent);
                }else{
                    requestPermission();
                    Toast.makeText(HomeActivity.this, "É necessário aceitar a permissão da câmara!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_inspecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_ObraId = (EditText) findViewById(R.id.edit_ObraId);
                int obraId = 0;
                try {
                    obraId = Integer.parseInt(edit_ObraId.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(HomeActivity.this, "Id da obra inválido!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (obraId==0){
                    Toast.makeText(HomeActivity.this, "Id da obra inválido!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isNetworkAvailable()){
                    Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().getObraById(obraId);
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
                                            Toast.makeText(HomeActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        startActivity(intent);
                                    }else if(response.body().get("RequestCode").getAsInt() == 404){
                                        try {
                                            Toast.makeText(HomeActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                        }catch (Exception err){
                                            Toast.makeText(HomeActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else{
                                    if(response.body().get("RequestCode").getAsInt() == 404) {
                                        try {
                                            Toast.makeText(HomeActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                        } catch (Exception err) {
                                            Toast.makeText(HomeActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(HomeActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(HomeActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(HomeActivity.this, "Sem conexão à internet :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
            {
                break;
            }
            case R.id.nav_qrcode:
            {
                if(checkPermission()){ // método que verifica se tem permissão para usar a câmara
                    Intent intent = new Intent(getApplicationContext(), QrCodeReaderActivity.class);
                    startActivity(intent);
                }else{
                    requestPermission();
                    Toast.makeText(HomeActivity.this, "É necessário aceitar a permissão da câmara!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.nav_profile:
            {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_logout:
            {
                db.userLogout();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getApplicationContext(), QrCodeReaderActivity.class);
                    startActivity(intent);
                } else {
                    NavigationView navigationView = findViewById(R.id.nav_view);
                    navigationView.setCheckedItem(R.id.nav_home);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("É necessário aceitar a permissão da câmara!",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage(message)
                .setPositiveButton("Aceitar", okListener)
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

}