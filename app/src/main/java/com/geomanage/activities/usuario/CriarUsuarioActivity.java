package com.geomanage.activities.usuario;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.activities.OpcoesActivity;
import com.geomanage.dao.UsuarioDao;
import com.geomanage.database.AppDatabase;
import com.geomanage.databinding.ActivityCriarUsuarioBinding;
import com.geomanage.entities.Usuario;

import java.util.Objects;

public class CriarUsuarioActivity extends AppCompatActivity {

    private ActivityCriarUsuarioBinding binding;
    private Intent intent;
    private UsuarioDao usuarioDao;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCriarUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));

        String text = Objects.requireNonNull(binding.txtTelaLogin.getText()).toString();
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(CriarUsuarioActivity.this, LoginUsuarioActivity.class);
                startActivity(intent);
            }
        };

        int start = text.indexOf("Entre aqui!");
        int end = start + "Entre aqui!".length();
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.txtTelaLogin.setText(spannableString);
        binding.txtTelaLogin.setMovementMethod(LinkMovementMethod.getInstance());

        db = AppDatabase.getDatabase(getApplicationContext());
        usuarioDao = db.usuarioDao();
        binding.btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        intent = new Intent(this, OpcoesActivity.class);
    }

    private void registrarUsuario() {
        String nome = binding.editNome.getText().toString().trim();
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if (TextUtils.isEmpty(nome)) {
            binding.editNome.setError("Nome é obrigatório");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            binding.editEmail.setError("Email é obrigatório");
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            binding.editSenha.setError("Senha é obrigatória");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        new Thread(new Runnable() {
            @Override
            public void run() {
                usuarioDao.insert(usuario);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CriarUsuarioActivity.this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }


    // TODO: Implementar lógica de criação de usuários
}