package com.geomanage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.activities.cidade.CidadeListActivity;
import com.geomanage.activities.endereco.EnderecoListActivity;

public class OpcoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        TextView btnListarCidades = findViewById(R.id.btnListarCidades);
        TextView btnListarEnderecos = findViewById(R.id.btnListarEnderecos);
        TextView btnListarUsuarios = findViewById(R.id.btnListarUsuarios);
        TextView btnCadastrarCidade = findViewById(R.id.btnCadastrarCidade);
        TextView btnCadastrarEnderecos = findViewById(R.id.btnCadastrarEnderecos);

        btnListarCidades.setOnClickListener(v -> {
            Intent intent = new Intent(OpcoesActivity.this, CidadeListActivity.class);
            startActivity(intent);
        });

        btnListarEnderecos.setOnClickListener(v -> {
            Intent intent = new Intent(OpcoesActivity.this, EnderecoListActivity.class);
            startActivity(intent);
        });

        btnListarUsuarios.setOnClickListener(v -> {
            //TODO: Adicione a intenção para listar usuários aqui
        });

        btnCadastrarCidade.setOnClickListener(v -> {
            //TODO: Adicione a intenção para cadastrar cidade aqui
        });

        btnCadastrarEnderecos.setOnClickListener(v -> {
            //TODO: Adicione a intenção para cadastrar endereços aqui
        });
    }
}