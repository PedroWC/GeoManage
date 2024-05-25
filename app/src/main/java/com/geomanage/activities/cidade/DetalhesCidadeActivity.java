package com.geomanage.activities.cidade;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.google.android.material.snackbar.Snackbar;

public class DetalhesCidadeActivity extends AppCompatActivity {

    private int cidadeId;
    private TextView cidadeTextView;
    private TextView estadoTextView;
    private Cidade cidade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cidade);

        cidadeTextView = findViewById(R.id.nomeCidadeEditText);
        estadoTextView = findViewById(R.id.estadoEditText);

        if (getIntent() != null) {
            cidadeId = getIntent().getIntExtra("cidadeId", -1);
            System.out.println(cidadeId);
        }

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

        if (cidadeId != -1) {
            try {
                cidade = db.cidadeDao().getCidadeById(cidadeId);
                if (cidade != null) {
                    cidadeTextView.setText(cidade.getCidade());
                    estadoTextView.setText(cidade.getEstado());
                } else {
                    Snackbar.make(findViewById(R.id.main), "Cidade não encontrada", Snackbar.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.main), "Erro ao carregar a cidade: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(findViewById(R.id.main), "ID da cidade inválido", Snackbar.LENGTH_SHORT).show();
        }

        // Botões
        AppCompatButton btEditarCidade = findViewById(R.id.btEditarCidade);
        btEditarCidade.setOnClickListener(v -> {
            try {
                if (cidade != null) {
                    cidade.setCidade(cidadeTextView.getText().toString());
                    cidade.setEstado(estadoTextView.getText().toString());
                    db.cidadeDao().update(cidade);
                    Snackbar.make(findViewById(R.id.main), "Cidade atualizada com sucesso", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(findViewById(R.id.main), "Cidade não encontrada", Snackbar.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.main), "Erro ao atualizar a cidade: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });

        AppCompatButton btExcluirCidade = findViewById(R.id.btExcluirCidade);
        btExcluirCidade.setOnClickListener(v -> {
            try {
                if (cidade != null) {
                    db.cidadeDao().delete(cidade);
                    Snackbar.make(findViewById(R.id.main), "Cidade excluída com sucesso", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(findViewById(R.id.main), "Cidade não encontrada", Snackbar.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.main), "Erro ao excluir a cidade: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });

        ImageButton btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }
}
