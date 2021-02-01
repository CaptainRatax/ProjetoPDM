package com.example.projetopdmgrupo3;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetopdmgrupo3.models.UserLogged;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    UserLogged userLogged;

    BaseDadosLocal db = new BaseDadosLocal(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
                Intent intent = new Intent(getApplicationContext(), QrCodeReaderActivity.class);
                startActivity(intent);
            }
        });

        btn_inspecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ObraActivity.class);
                EditText edit_ObraId = (EditText) findViewById(R.id.edit_ObraId);
                int obraId = 0;
                try {
                    obraId = Integer.parseInt(edit_ObraId.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(HomeActivity.this, "Id da obra inválido!", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("ObraId", obraId);
                startActivity(intent);
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
                Intent intent = new Intent(getApplicationContext(), QrCodeReaderActivity.class);
                startActivity(intent);
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
}