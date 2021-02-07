package com.example.projetopdmgrupo3.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetopdmgrupo3.models.Caso;
import com.example.projetopdmgrupo3.models.Fotografia;
import com.example.projetopdmgrupo3.server.BaseDadosLocal;
import com.example.projetopdmgrupo3.R;
import com.example.projetopdmgrupo3.models.Cliente;
import com.example.projetopdmgrupo3.models.InspecaoOnGoing;
import com.example.projetopdmgrupo3.models.Obra;
import com.example.projetopdmgrupo3.models.RegistoHoras;
import com.example.projetopdmgrupo3.models.UserLogged;
import com.example.projetopdmgrupo3.server.MyService;
import com.example.projetopdmgrupo3.server.RetrofitClient;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdicionarCasoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BaseDadosLocal db = new BaseDadosLocal(this);

    private DrawerLayout drawer;

    static final int IMG_REQUEST = 21;

    ArrayList<Fotografia> imagens = new ArrayList<Fotografia>();

    UserLogged userLogged;
    InspecaoOnGoing inspecaoOnGoing;
    Obra obra;
    Cliente cliente;
    RegistoHoras registoHoras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_adicionar_caso);

        userLogged = db.getUserLogged();

        inspecaoOnGoing = db.getInspecaoOnGoing();

        obra = inspecaoOnGoing.getObra();
        cliente = inspecaoOnGoing.getCliente();
        registoHoras = db.getRegistoHorasByClienteIdObraId(obra.getId(), userLogged.getIdInspecionador());
        Toolbar toolbar = findViewById(R.id.toolbar_inspecao_casoView);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_inspecao);
        NavigationView navigationView = findViewById(R.id.nav_view_inspecao);
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

        navigationView.setCheckedItem(R.id.nav_criar_caso);

        Button btn_cancelarCaso = findViewById(R.id.btn_cancelarCaso);
        Button btn_submeterCaso = findViewById(R.id.btn_submeterCaso);

        btn_submeterCaso.setEnabled(true);
        btn_submeterCaso.setClickable(true);

        btn_cancelarCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InspecaoHomeActivity.class);
                startActivity(intent);
            }
        });

        btn_submeterCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_submeterCaso.setClickable(false);
                btn_submeterCaso.setEnabled(false);
                submeterCaso();
            }
        });

        ImageView img_caso1 = findViewById(R.id.img_caso1);
        ImageView img_caso2 = findViewById(R.id.img_caso2);
        ImageView img_caso3 = findViewById(R.id.img_caso3);
        ImageView img_caso4 = findViewById(R.id.img_caso4);
        ImageView img_caso5 = findViewById(R.id.img_caso5);
        ImageView img_caso6 = findViewById(R.id.img_caso6);
        ImageView img_caso7 = findViewById(R.id.img_caso7);
        ImageView img_caso8 = findViewById(R.id.img_caso8);
        ImageView img_caso9 = findViewById(R.id.img_caso9);

        ImageView img_delete1 = findViewById(R.id.img_delete1);
        ImageView img_delete2 = findViewById(R.id.img_delete2);
        ImageView img_delete3 = findViewById(R.id.img_delete3);
        ImageView img_delete4 = findViewById(R.id.img_delete4);
        ImageView img_delete5 = findViewById(R.id.img_delete5);
        ImageView img_delete6 = findViewById(R.id.img_delete6);
        ImageView img_delete7 = findViewById(R.id.img_delete7);
        ImageView img_delete8 = findViewById(R.id.img_delete8);
        ImageView img_delete9 = findViewById(R.id.img_delete9);


        img_caso1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        img_caso2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        img_caso3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        img_caso4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        img_caso5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        img_caso6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        img_caso7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        img_caso8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        img_caso9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        img_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImagem(0);
            }
        });
        img_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImagem(1);
            }
        });
        img_delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImagem(2);
            }
        });
        img_delete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImagem(3);
            }
        });
        img_delete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImagem(4);
            }
        });
        img_delete6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImagem(5);
            }
        });
        img_delete7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImagem(6);
            }
        });
        img_delete8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImagem(7);
            }
        });
        img_delete9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImagem(8);
            }
        });

    }

    public void submeterCaso(){
        EditText edit_titulo = findViewById(R.id.edit_titulo);
        EditText edit_descricao = findViewById(R.id.edit_descricao);
        Button btn_submeter = findViewById(R.id.btn_submeterCaso);
        String titulo = edit_titulo.getText().toString();
        String descricao = edit_descricao.getText().toString();
        if(titulo!=""){
            if (descricao!=""){
                if (titulo.length()<=50){
                    if (descricao.length()<=1024){
                        Caso novoCaso = new Caso(
                                titulo,
                                descricao,
                                obra.getId(),
                                userLogged.getIdInspecionador(),
                                "",
                                0,
                                0
                        );
                        String json = "{ " +
                                "\"Titulo\": \"" + titulo + "\", " +
                                "\"Descricao\": \"" + descricao + "\", " +
                                "\"IdObra\": \"" + obra.getId() + "\", " +
                                "\"IdInspecionador\": \"" + userLogged.getIdInspecionador() + "\", " +
                                "\"ListaDeImagens\":[";
                        if(imagens.size()!=0){
                            for (Fotografia imagem:imagens) {
                                json = json + "\"" + imagem.getFotografia() + "\", ";
                            }
                            json = json.substring(0, json.length()-2);
                        }
                        json = json + "]}";
                        if (isNetworkAvailable()) {
                            JsonObject body = new JsonParser().parse(json).getAsJsonObject();
                            Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().criarCaso(body);
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if(response.body() != null) {
                                        if (response.body().get("Success").getAsBoolean()) {
                                            if (response.body().get("RequestCode").getAsInt() == 200) {
                                                String str = response.body().get("Message").getAsString();
                                                str = str.substring(14, str.length());
                                                String[] splited = str.split("\\s+");
                                                novoCaso.setIdIsSynced(Integer.parseInt(splited[0]));
                                                novoCaso.setIsSynced(1);
                                                db.addCaso(novoCaso, imagens);
                                                Intent intent = new Intent(getApplicationContext(), InspecaoHomeActivity.class);
                                                startActivity(intent);
                                            }else{
                                                try {
                                                    Toast.makeText(AdicionarCasoActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                                }catch (Exception err){
                                                    Toast.makeText(AdicionarCasoActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                                }
                                                btn_submeter.setEnabled(true);
                                                btn_submeter.setClickable(true);
                                            }
                                        }else {
                                            try {
                                                Toast.makeText(AdicionarCasoActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                            }catch (Exception err){
                                                Toast.makeText(AdicionarCasoActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                            }
                                            btn_submeter.setEnabled(true);
                                            btn_submeter.setClickable(true);
                                        }
                                    }else{
                                        btn_submeter.setEnabled(true);
                                        btn_submeter.setClickable(true);
                                        Toast.makeText(AdicionarCasoActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    btn_submeter.setEnabled(true);
                                    btn_submeter.setClickable(true);
                                    Toast.makeText(AdicionarCasoActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            db.updateIsDataSynced(false);
                            db.addCaso(novoCaso, imagens);
                            Intent intent = new Intent(getApplicationContext(), InspecaoHomeActivity.class);
                            startActivity(intent);
                        }
                    }else{
                        btn_submeter.setEnabled(true);
                        btn_submeter.setClickable(true);
                        Toast.makeText(this, "A descrição não pode ter mais do que 1024 caractéres!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    btn_submeter.setEnabled(true);
                    btn_submeter.setClickable(true);
                    Toast.makeText(this, "O título não pode ter mais do que 30 caractéres!", Toast.LENGTH_SHORT).show();
                }
            }else{
                btn_submeter.setEnabled(true);
                btn_submeter.setClickable(true);
                Toast.makeText(this, "A descrição não pode estar vazia!", Toast.LENGTH_SHORT).show();
            }
        }else{
            btn_submeter.setEnabled(true);
            btn_submeter.setClickable(true);
            Toast.makeText(this, "O título não pode estar vazio!", Toast.LENGTH_SHORT).show();
        }
    }

    public  void abrirGaleria(){
        Intent takePictureIntent = new Intent();
        takePictureIntent.setType("image/*");
        takePictureIntent.setAction(Intent.ACTION_GET_CONTENT);
        try {
            startActivityForResult(takePictureIntent, IMG_REQUEST);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Ocorreu um erro a abrir a galeria.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
                addImagem(imageEncoded);
            }catch (IOException e){
                Toast.makeText(this, "Algo correu mal e a imagem não foi carregada. Tenta outra vez", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void addImagem(String base64) {
        ImageView img_caso1 = findViewById(R.id.img_caso1);
        ImageView img_caso2 = findViewById(R.id.img_caso2);
        ImageView img_caso3 = findViewById(R.id.img_caso3);
        ImageView img_caso4 = findViewById(R.id.img_caso4);
        ImageView img_caso5 = findViewById(R.id.img_caso5);
        ImageView img_caso6 = findViewById(R.id.img_caso6);
        ImageView img_caso7 = findViewById(R.id.img_caso7);
        ImageView img_caso8 = findViewById(R.id.img_caso8);
        ImageView img_caso9 = findViewById(R.id.img_caso9);

        ImageView img_delete1 = findViewById(R.id.img_delete1);
        ImageView img_delete2 = findViewById(R.id.img_delete2);
        ImageView img_delete3 = findViewById(R.id.img_delete3);
        ImageView img_delete4 = findViewById(R.id.img_delete4);
        ImageView img_delete5 = findViewById(R.id.img_delete5);
        ImageView img_delete6 = findViewById(R.id.img_delete6);
        ImageView img_delete7 = findViewById(R.id.img_delete7);
        ImageView img_delete8 = findViewById(R.id.img_delete8);
        ImageView img_delete9 = findViewById(R.id.img_delete9);

        switch (imagens.size()) {
            case 0:
                imagens.add(new Fotografia(1, base64));
                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                img_caso1.setImageBitmap(base64ToBitmap(imagens.get(0).getFotografia()));
                img_caso1.setClickable(false);
                img_delete1.setVisibility(View.VISIBLE);
                img_delete1.setClickable(true);
                img_delete1.setImageResource(R.drawable.ic_baseline_cancel_24);
                img_caso2.setVisibility(View.VISIBLE);
                img_caso2.setClickable(true);
                img_caso2.setImageResource(R.drawable.ic_baseline_file_upload_24);
                break;
            case 1:
                imagens.add(new Fotografia(2, base64));
                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                img_caso2.setImageBitmap(base64ToBitmap(imagens.get(1).getFotografia()));
                img_caso2.setClickable(false);
                img_delete2.setVisibility(View.VISIBLE);
                img_delete2.setClickable(true);
                img_delete2.setImageResource(R.drawable.ic_baseline_cancel_24);
                img_caso3.setVisibility(View.VISIBLE);
                img_caso3.setClickable(true);
                img_caso3.setImageResource(R.drawable.ic_baseline_file_upload_24);
                break;
            case 2:
                imagens.add(new Fotografia(3, base64));
                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                img_caso3.setImageBitmap(base64ToBitmap(imagens.get(2).getFotografia()));
                img_caso3.setClickable(false);
                img_delete3.setVisibility(View.VISIBLE);
                img_delete3.setClickable(true);
                img_delete3.setImageResource(R.drawable.ic_baseline_cancel_24);
                img_caso4.setVisibility(View.VISIBLE);
                img_caso4.setClickable(true);
                img_caso4.setImageResource(R.drawable.ic_baseline_file_upload_24);
                break;
            case 3:
                imagens.add(new Fotografia(4, base64));
                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                img_caso4.setImageBitmap(base64ToBitmap(imagens.get(3).getFotografia()));
                img_caso4.setClickable(false);
                img_delete4.setVisibility(View.VISIBLE);
                img_delete4.setClickable(true);
                img_delete4.setImageResource(R.drawable.ic_baseline_cancel_24);
                img_caso5.setVisibility(View.VISIBLE);
                img_caso5.setClickable(true);
                img_caso5.setImageResource(R.drawable.ic_baseline_file_upload_24);
                break;
            case 4:
                imagens.add(new Fotografia(5, base64));
                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                img_caso5.setImageBitmap(base64ToBitmap(imagens.get(4).getFotografia()));
                img_caso5.setClickable(false);
                img_delete5.setVisibility(View.VISIBLE);
                img_delete5.setClickable(true);
                img_delete5.setImageResource(R.drawable.ic_baseline_cancel_24);
                img_caso6.setVisibility(View.VISIBLE);
                img_caso6.setClickable(true);
                img_caso6.setImageResource(R.drawable.ic_baseline_file_upload_24);
                break;
            case 5:
                imagens.add(new Fotografia(6, base64));
                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                img_caso6.setImageBitmap(base64ToBitmap(imagens.get(5).getFotografia()));
                img_caso6.setClickable(false);
                img_delete6.setVisibility(View.VISIBLE);
                img_delete6.setClickable(true);
                img_delete6.setImageResource(R.drawable.ic_baseline_cancel_24);
                img_caso7.setVisibility(View.VISIBLE);
                img_caso7.setClickable(true);
                img_caso7.setImageResource(R.drawable.ic_baseline_file_upload_24);
                break;
            case 6:
                imagens.add(new Fotografia(7, base64));
                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                img_caso7.setImageBitmap(base64ToBitmap(imagens.get(6).getFotografia()));
                img_caso7.setClickable(false);
                img_delete7.setVisibility(View.VISIBLE);
                img_delete7.setClickable(true);
                img_delete7.setImageResource(R.drawable.ic_baseline_cancel_24);
                img_caso8.setVisibility(View.VISIBLE);
                img_caso8.setClickable(true);
                img_caso8.setImageResource(R.drawable.ic_baseline_file_upload_24);
                break;
            case 7:
                imagens.add(new Fotografia(8, base64));
                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                img_caso8.setImageBitmap(base64ToBitmap(imagens.get(7).getFotografia()));
                img_caso8.setClickable(false);
                img_delete8.setVisibility(View.VISIBLE);
                img_delete8.setClickable(true);
                img_delete8.setImageResource(R.drawable.ic_baseline_cancel_24);
                img_caso9.setVisibility(View.VISIBLE);
                img_caso9.setClickable(true);
                img_caso9.setImageResource(R.drawable.ic_baseline_file_upload_24);
                break;
            case 8:
                imagens.add(new Fotografia(9, base64));
                Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                img_caso9.setImageBitmap(base64ToBitmap(imagens.get(8).getFotografia()));
                img_caso9.setClickable(false);
                img_delete9.setVisibility(View.VISIBLE);
                img_delete9.setClickable(true);
                img_delete9.setImageResource(R.drawable.ic_baseline_cancel_24);
                break;
            default:
                Toast.makeText(this, "Só pode escolher no máximo 8 imagens!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void deleteImagem(int i){
        imagens.remove(i);
        atualizarImageViews();
    }

    private void atualizarImageViews() {
        ImageView img_casoi;
        ImageView img_deletei;

        int nExceptions = 0;
        for (int i = 0; i<9; i++){
            switch (i){
                case 0:
                    img_casoi = findViewById(R.id.img_caso1);
                    img_deletei = findViewById(R.id.img_delete1);
                    break;
                case 1:
                    img_casoi = findViewById(R.id.img_caso2);
                    img_deletei = findViewById(R.id.img_delete2);
                    break;
                case 2:
                    img_casoi = findViewById(R.id.img_caso3);
                    img_deletei = findViewById(R.id.img_delete3);
                    break;
                case 3:
                    img_casoi = findViewById(R.id.img_caso4);
                    img_deletei = findViewById(R.id.img_delete4);
                    break;
                case 4:
                    img_casoi = findViewById(R.id.img_caso5);
                    img_deletei = findViewById(R.id.img_delete5);
                    break;
                case 5:
                    img_casoi = findViewById(R.id.img_caso6);
                    img_deletei = findViewById(R.id.img_delete6);
                    break;
                case 6:
                    img_casoi = findViewById(R.id.img_caso7);
                    img_deletei = findViewById(R.id.img_delete7);
                    break;
                case 7:
                    img_casoi = findViewById(R.id.img_caso8);
                    img_deletei = findViewById(R.id.img_delete8);
                    break;
                case 8:
                    img_casoi = findViewById(R.id.img_caso9);
                    img_deletei = findViewById(R.id.img_delete9);
                    break;
                default:
                    img_casoi = null;
                    img_deletei = null;
                    break;
            }
            try {
                img_casoi.setClickable(false);
                img_casoi.setVisibility(View.VISIBLE);
                img_casoi.setImageBitmap(base64ToBitmap(imagens.get(i).getFotografia()));
                img_deletei.setClickable(true);
                img_deletei.setVisibility(View.VISIBLE);
                img_deletei.setImageResource(R.drawable.ic_baseline_cancel_24);
            }catch (IndexOutOfBoundsException e){
                if (nExceptions==0){
                    nExceptions=1;
                    img_casoi.setClickable(true);
                    img_casoi.setVisibility(View.VISIBLE);
                    img_casoi.setImageResource(R.drawable.ic_baseline_file_upload_24);
                }else{
                    img_casoi.setClickable(false);
                    img_casoi.setVisibility(View.INVISIBLE);
                }
                img_deletei.setClickable(false);
                img_deletei.setVisibility(View.INVISIBLE);
            }
        }
    }

    private Bitmap base64ToBitmap(String fotografia) {
        byte[] decodedByte = Base64.decode(fotografia, 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        return bitmap;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home_inspecao:
                Intent intent = new Intent(getApplicationContext(), InspecaoHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_criar_caso:
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
        new AlertDialog.Builder(AdicionarCasoActivity.this)
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
                                    Toast.makeText(AdicionarCasoActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                                }catch (Exception err){
                                    Toast.makeText(AdicionarCasoActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            try {
                                Toast.makeText(AdicionarCasoActivity.this, response.body().get("Message").getAsString(), Toast.LENGTH_LONG).show();
                            }catch (Exception err){
                                Toast.makeText(AdicionarCasoActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(AdicionarCasoActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(AdicionarCasoActivity.this, "Ups. Algo correu mal...", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "Para cancelar a inspeção é preciso ter conexão à internet :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NavigationView navigationView = findViewById(R.id.nav_view_inspecao);
        navigationView.setCheckedItem(R.id.nav_criar_caso);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view_inspecao);
        navigationView.setCheckedItem(R.id.nav_criar_caso);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}