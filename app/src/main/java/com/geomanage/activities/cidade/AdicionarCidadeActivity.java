package com.geomanage.activities.cidade;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.google.android.material.snackbar.Snackbar;

public class AdicionarCidadeActivity extends AppCompatActivity {

    private EditText nomeEditText;
    private EditText estadoEditText;
    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cidade);

        view = findViewById(R.id.main);
        nomeEditText = findViewById(R.id.nomeCidadeEditText);
        estadoEditText = findViewById(R.id.estadoEditText);
        AppCompatButton adicionarButton = findViewById(R.id.btRegistrarCidade);
        ImageButton btnCancelar = findViewById(R.id.btnCancelar);

        adicionarButton.setOnClickListener(v -> adicionarCidade());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void adicionarCidade() {
        String nome = nomeEditText.getText().toString();
        String estado = estadoEditText.getText().toString();

        if (nome.isEmpty() || estado.isEmpty()) {
            Snackbar snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        Cidade cidade = new Cidade();
        cidade.setCidade(nome);
        cidade.setEstado(estado);
        db.cidadeDao().insert(cidade);

        Snackbar snackbar = Snackbar.make(view, "Cidade adicionada com sucesso", Snackbar.LENGTH_SHORT);
        snackbar.show();

        finish(); // Volta para a Activity anterior
    }
}
