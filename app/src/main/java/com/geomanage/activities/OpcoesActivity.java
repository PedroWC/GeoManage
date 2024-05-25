package com.geomanage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.activities.cidade.ListarCidadeActivity;
import com.geomanage.activities.endereco.ListarEnderecoActivity;
import com.geomanage.activities.usuario.ListarUsuarioActivity;
import com.geomanage.activities.cidade.AdicionarCidadeActivity;
import com.geomanage.activities.endereco.AdicionarEnderecoActivity;

public class OpcoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        TextView btnListarCidades = findViewById(R.id.btnListarCidades);
        TextView btnListarEnderecos = findViewById(R.id.btnListarEnderecos);
        TextView btnListarUsuarios = findViewById(R.id.btnListarUsuarios);
        TextView btnCadastrarCidade = findViewById(R.id.btnCadastrarCidade);
        TextView btnCadastrarEnderecos = findViewById(R.id.btnCadastrarEnderecos);

        btnListarCidades.setOnClickListener(v -> {
            Intent intent = new Intent(OpcoesActivity.this, ListarCidadeActivity.class);
            startActivity(intent);
        });

        btnListarEnderecos.setOnClickListener(v -> {
            Intent intent = new Intent(OpcoesActivity.this, ListarEnderecoActivity.class);
            startActivity(intent);
        });

        btnListarUsuarios.setOnClickListener(v -> {
            Intent intent = new Intent(OpcoesActivity.this, ListarUsuarioActivity.class);
            startActivity(intent);
        });

        btnCadastrarCidade.setOnClickListener(v -> {
            Intent intent = new Intent(OpcoesActivity.this, AdicionarCidadeActivity.class);
            startActivity(intent);
        });

        btnCadastrarEnderecos.setOnClickListener(v -> {
            Intent intent = new Intent(OpcoesActivity.this, AdicionarEnderecoActivity.class);
            startActivity(intent);
        });
    }
}
