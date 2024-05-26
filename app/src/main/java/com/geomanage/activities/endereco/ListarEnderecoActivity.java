package com.geomanage.activities.endereco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Endereco;

import java.util.ArrayList;
import java.util.List;

public class ListarEnderecoActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private List<Endereco> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        ListView listView = findViewById(R.id.listView);
        items = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Endereco enderecoSelecionado = items.get(position);
            Intent intent = new Intent(ListarEnderecoActivity.this, DetalhesEnderecoActivity.class);
            intent.putExtra("endereco_id", enderecoSelecionado.getEnderecoId());
            startActivity(intent);
        });

        loadEnderecos();
    }

    public void loadEnderecos() {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        List<Endereco> enderecos = db.enderecoDao().getAllEnderecos();
        items.clear();
        items.addAll(enderecos);

        List<String> enderecoDescricoes = new ArrayList<>();
        for (Endereco endereco : items) {
            enderecoDescricoes.add(endereco.getDescricao());
        }

        adapter.clear();
        adapter.addAll(enderecoDescricoes);
        adapter.notifyDataSetChanged();
    }
}
