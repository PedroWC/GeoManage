package com.geomanage.activities.usuario;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class DetalhesUsuarioActivity extends AppCompatActivity {

    private int usuarioId;
    private Usuario usuario;
    private AppDatabase db;
    private TextInputEditText nomeEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText senhaEditText;
    private TextInputEditText novaSenhaEditText;
    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_usuario);

        view = findViewById(R.id.main);
        db = AppDatabase.getDatabase(getApplicationContext());

        if (getIntent() != null) {
            usuarioId = getIntent().getIntExtra("id", -1);
        }

        usuario = db.usuarioDao().getUsuarioById(usuarioId);

        if (usuario != null) {
            nomeEditText = findViewById(R.id.nomeEditText);
            emailEditText = findViewById(R.id.emailEditText);
            senhaEditText = findViewById(R.id.senhaEditText);
            novaSenhaEditText = findViewById(R.id.novaSenhaEditText);

            nomeEditText.setText(usuario.getNome());
            emailEditText.setText(usuario.getEmail());
        } else {
            Snackbar.make(view, "Usuário não encontrado", Snackbar.LENGTH_SHORT).show();
            finish();
        }

        AppCompatButton btRegistrarUsuario = findViewById(R.id.btRegistrarUsuario);
        btRegistrarUsuario.setOnClickListener(v -> {
            try {
                usuario.setNome(Objects.requireNonNull(nomeEditText.getText()).toString());
                usuario.setEmail(Objects.requireNonNull(emailEditText.getText()).toString());
                String novaSenha = Objects.requireNonNull(novaSenhaEditText.getText()).toString();

                if (!novaSenha.isEmpty()) {
                    usuario.setSenha(novaSenha);
                }

                db.usuarioDao().update(usuario);
                Snackbar.make(view, "Usuário atualizado com sucesso", Snackbar.LENGTH_SHORT).show();
            } catch (Exception e) {
                Snackbar.make(view, "Erro ao atualizar o usuário: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });

        AppCompatButton btExcluirUsuario = findViewById(R.id.btExcluirUsuario);
        btExcluirUsuario.setOnClickListener(v -> {
            try {
                db.usuarioDao().delete(usuario);
                Snackbar.make(view, "Usuário excluído com sucesso", Snackbar.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Snackbar.make(view, "Erro ao excluir o usuário: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });

        ImageButton btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }
}
