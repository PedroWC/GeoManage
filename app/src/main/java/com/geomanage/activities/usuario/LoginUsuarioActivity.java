package com.geomanage.activities.usuario;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.activities.OpcoesActivity;
import com.geomanage.database.AppDatabase;
import com.geomanage.databinding.ActivityLoginUsuarioBinding;
import com.geomanage.entities.Usuario;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class LoginUsuarioActivity extends AppCompatActivity {
    private ActivityLoginUsuarioBinding binding;
    private Intent intent;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));

        db = AppDatabase.getDatabase(getApplicationContext());
    }

    @Override
    protected void onResume(){
        super.onResume();
        intent = new Intent(this, OpcoesActivity.class);
    }

    private void login(View view) {

        String email = Objects.requireNonNull(binding.editEmail.getText()).toString();
        String senha = Objects.requireNonNull(binding.editSenha.getText()).toString();

        if (email.isEmpty()) {
            binding.editEmail.setError("Preencha o E-mail!");
        } else if (senha.isEmpty()) {
            binding.editSenha.setError("Preencha a Senha!");
        } else if (!email.contains("@gmail.com")) {
            Snackbar snackbar = Snackbar.make(view, "E-mail inválido!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (senha.length() <= 5) {
            Snackbar snackbar = Snackbar.make(view, "A senha precisa ter pelo menos 6 caracteres!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {

            ProgressBar progressBar = binding.progressBar;
            progressBar.setVisibility(View.VISIBLE);

            binding.btEntrar.setEnabled(false);
            binding.btEntrar.setTextColor(Color.parseColor("#FFFFFF"));

            Usuario usuario = db.usuarioDao().login(email, senha);

            if (usuario != null) {
                Snackbar snackbar = Snackbar.make(view, "Login efetuado com sucesso!", Snackbar.LENGTH_SHORT);
                snackbar.show();

                startActivity(intent);
            }
        }
    }

    // TODO: Testar login do usuário
}