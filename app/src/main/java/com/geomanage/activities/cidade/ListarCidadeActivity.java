package com.geomanage.activities.cidade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.geomanage.activities.cidade.DetalhesCidadeActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListarCidadeActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private final List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        ListView listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Obtém o nome da cidade selecionada
            String cityName = items.get(position);

            // Busca a cidade no banco de dados pelo nome
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            Cidade cidade = db.cidadeDao().getCidadeByName(cityName);

            // Verifica se a cidade foi encontrada
            if (cidade != null) {
                // Se encontrou, passa o ID da cidade para a próxima Activity
                Intent intent = new Intent(ListarCidadeActivity.this, DetalhesCidadeActivity.class);
                intent.putExtra("cidadeId", cidade.getCidadeId());
                startActivity(intent);
            } else {
                // Se não encontrou, exibe uma mensagem de erro usando um SnackBar
                Snackbar.make(findViewById(android.R.id.content), "Cidade não encontrada", Snackbar.LENGTH_SHORT).show();
            }
        });


        loadCidades();
    }

    public void loadCidades() {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        List<Cidade> cidades = db.cidadeDao().getAllCidades();
        items.clear();
        for (Cidade cidade : cidades) {
            items.add(cidade.getCidade());
        }
        adapter.notifyDataSetChanged();
    }
}
