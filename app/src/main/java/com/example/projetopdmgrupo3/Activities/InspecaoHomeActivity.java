package com.example.projetopdmgrupo3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        obra = inspecaoOnGoing.getObra();
        cliente = inspecaoOnGoing.getCliente();
        registoHoras = db.getRegistoHorasByClienteIdObraId(obra.getId(), cliente.getId());
        Toolbar toolbar = findViewById(R.id.toolbar_inspecao);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_inspecao);
        NavigationView navigationView= findViewById(R.id.nav_view_inspecao);
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

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NavigationView navigationView = findViewById(R.id.nav_view_inspecao);
        navigationView.setCheckedItem(R.id.nav_home_inspecao);
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
                Toast.makeText(this, "Cancelaste .-.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout_inspecao:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
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
}