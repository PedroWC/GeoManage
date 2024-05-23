package com.geomanage.activities.usuario;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        String text = Objects.requireNonNull(binding.txtTelaCadastro.getText()).toString();
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginUsuarioActivity.this, CriarUsuarioActivity.class);
                startActivity(intent);
            }
        };

        int start = text.indexOf("Cadastre-se!");
        int end = start + "Cadastre-se!".length();
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.txtTelaCadastro.setText(spannableString);
        binding.txtTelaCadastro.setMovementMethod(LinkMovementMethod.getInstance());
        binding.btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
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

            Handler handler = new Handler(Looper.getMainLooper());

            Runnable timeoutRunnable = new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    binding.btEntrar.setEnabled(true);
                    binding.btEntrar.setTextColor(Color.parseColor("#000000")); // Restaura a cor do texto
                    Snackbar snackbar = Snackbar.make(view, "Tempo de resposta excedido. Tente novamente.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            };

            handler.postDelayed(timeoutRunnable, 5000); // Timeout de 5 segundos

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Usuario usuario = db.usuarioDao().login(email, senha);
                    handler.removeCallbacks(timeoutRunnable); // Remove o timeout se o login for concluído a tempo
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            binding.btEntrar.setEnabled(true);
                            binding.btEntrar.setTextColor(Color.parseColor("#000000")); // Restaura a cor do texto

                            if (usuario != null) {
                                Snackbar snackbar = Snackbar.make(view, "Login efetuado com sucesso!", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                startActivity(intent);
                            } else {
                                Snackbar snackbar = Snackbar.make(view, "Usuário não encontrado!", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        }
                    });
                }
            }).start();
        }
    }
}