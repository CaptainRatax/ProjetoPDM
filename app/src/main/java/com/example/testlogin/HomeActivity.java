package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    User user;
    TextView id;
    TextView password;
    TextView email;
    TextView telefone;
    TextView nome;
    TextView datanascimento;
    TextView createdat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = (User) getIntent().getExtras().get("USER");

        id = (TextView)findViewById(R.id.id);
        password = (TextView)findViewById(R.id.password);
        email = (TextView)findViewById(R.id.email);
        telefone = (TextView)findViewById(R.id.telefone);
        nome = (TextView)findViewById(R.id.nome);
        datanascimento = (TextView)findViewById(R.id.datanascimento);
        createdat = (TextView)findViewById(R.id.createdat);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            id.setText(user.getId());
            password.setText(user.getPassword());
            email.setText(user.getEmail());
            telefone.setText(user.getTelefone());
            nome.setText(user.getNome());
            datanascimento.setText(user.getDataNascimento());
            createdat.setText(user.getCreatedAt());
        }
    }

}