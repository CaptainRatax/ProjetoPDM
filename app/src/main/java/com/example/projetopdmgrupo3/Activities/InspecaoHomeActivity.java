package com.example.projetopdmgrupo3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetopdmgrupo3.server.BaseDadosLocal;
import com.example.projetopdmgrupo3.R;
import com.example.projetopdmgrupo3.models.Caso;
import com.example.projetopdmgrupo3.models.Cliente;
import com.example.projetopdmgrupo3.models.CustomAdapter;
import com.example.projetopdmgrupo3.models.InspecaoOnGoing;
import com.example.projetopdmgrupo3.models.Obra;
import com.example.projetopdmgrupo3.models.RegistoHoras;
import com.example.projetopdmgrupo3.models.UserLogged;
import com.example.projetopdmgrupo3.server.MyService;
import com.example.projetopdmgrupo3.server.RetrofitClient;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InspecaoHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    BaseDadosLocal db = new BaseDadosLocal(this);

    UserLogged userLogged;
    InspecaoOnGoing inspecaoOnGoing;
    Obra obra;
    Cliente cliente;
    RegistoHoras registoHoras;
    ArrayList<Caso> casosArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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

        userLogged = db.getUserLogged();

        obra = inspecaoOnGoing.getObra();
        cliente = inspecaoOnGoing.getCliente();
        registoHoras = db.getRegistoHorasByClienteIdObraId(obra.getId(), userLogged.getIdInspecionador());
        Toolbar toolbar = findViewById(R.id.toolbar_inspecao_casoView);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_inspecao);
        NavigationView navigationView= findViewById(R.id.nav_view_inspecao);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        TextView txt_userName = (TextView) headerView.findViewById(R.id.userName);
        TextView txt_userEmail = (TextView) headerView.findViewById(R.id.userEmail);
        CircleImageView img_userImage = (CircleImageView) headerView.findViewById(R.id.profile_image);

        txt_userName.setText(userLogged.getNome());
        txt_userEmail.setText(userLogged.getEmail());

        byte[] decodedByte = Base64.decode(userLogged.getImagem(), 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        img_userImage.setImageBitmap(bitmap);

        navigationView.setCheckedItem(R.id.nav_home_inspecao);

        casosArrayList = db.getAllCasosByIdObra(obra.getId());

        ListView lv_casos = findViewById(R.id.lv_casos);
        CustomAdapter casosAdapter = new CustomAdapter(InspecaoHomeActivity.this, casosArrayList);
        lv_casos.setAdapter(casosAdapter);

        Button btn_criarCaso = findViewById(R.id.btn_criarCaso);
        Button btn_finalizarInspecao = findViewById(R.id.btn_finalizarInspecao);

        btn_criarCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdicionarCasoActivity.class);
                startActivity(intent);
            }
        });

        btn_finalizarInspecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FinalizarInspecaoActivity.class);
                startActivity(intent);
            }
        });

        lv_casos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CasoViewActivity.class);
                intent.putExtra("IDCASO", casosArrayList.get(i).getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NavigationView navigationView = findViewById(R.id.nav_view_inspecao);
        navigationView.setCheckedItem(R.id.nav_home_inspecao);
        casosArrayList = db.getAllCasosByIdObra(obra.getId());

        ListView lv_casos = findViewById(R.id.lv_casos);
        CustomAdapter casosAdapter = new CustomAdapter(InspecaoHomeActivity.this, casosArrayList);
        lv_casos.setAdapter(casosAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view_inspecao);
        navigationView.setCheckedItem(R.id.nav_home_inspecao);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home_inspecao:
                break;
            case R.id.nav_criar_caso:
                Intent intent = new Intent(getApplicationContext(), AdicionarCasoActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_finalizar:
                Intent intent1 = new Intent(getApplicationContext(), FinalizarInspecaoActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_cancelarInspecao:
                showMessageOKCancel("Tem a certeza que quer cancelar a inspeção? Todos os dados serão perdidos!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelarInspecao(false);
                            }
                        });
                break;
            case R.id.nav_logout_inspecao:
                showMessageOKCancel("Tem a certeza que quer cancelar a inspeção? Todos os dados serão perdidos!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelarInspecao(true);
                            }
                        });
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(InspecaoHomeActivity.this)
                .setMessage(message)
                .setPositiveButton("Aceitar", okListener)
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    private void cancelarInspecao(boolean logout){
        if (isNetworkAvailable()){
            String json = "{ " +
                    "\"IdObra\": \"" + inspecaoOnGoing.getObra().getId() + "\", " +
                    "\"IdInspecionador\": \"" + userLogged.getIdInspecionador() + "\"}";
            JsonObject body = new JsonParser().parse(json).getAsJsonObject();
            Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().cancelarInspecao(body);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.body() != null) {
                        if (response.body().get("Success").getAsBoolean()) {
                            if (response.body().get("RequestCode").getAsInt() == 200) {
                                ArrayList<Caso> casosParaEliminar = db.getAllCasosByIdObra(inspecaoOnGoing.getObra().getId());
                                for (Caso casoParaEliminar:casosParaEliminar) {
                                    db.deleteCasoById(casoParaEliminar.getId());
                                }
                                RegistoHoras registoHorasParaEliminar = db.getRegistoHorasByClienteIdObraId(inspecaoOnGoing.getObra().getId(), userLogged.getIdInspecionador());
                                db.deleteRegistoHoraById(registoHorasParaEliminar.getId());
                                db.acabarInspecao();
                                if (logout){
                                        db.userLogout();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                }else{
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }

                            }else{
                                try {
                                    Toast.makeText(InspecaoHomeActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                }catch (Exception err){
                                    Toast.makeText(InspecaoHomeActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            try {
                                Toast.makeText(InspecaoHomeActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                            }catch (Exception err){
                                Toast.makeText(InspecaoHomeActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(InspecaoHomeActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(InspecaoHomeActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "Para cancelar a inspeção é preciso ter conexão à internet :(", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            return;
        }
    }
}