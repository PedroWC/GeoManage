package com.geomanage.activities.endereco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Endereco;
import com.geomanage.activities.endereco.DetalhesEnderecoActivity;

import java.util.ArrayList;
import java.util.List;

public class ListarEnderecoActivity extends AppCompatActivity {

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
            Intent intent = new Intent(ListarEnderecoActivity.this, DetalhesEnderecoActivity.class);
            intent.putExtra("enderecoId", position);
            startActivity(intent);
        });

        loadEnderecos();
    }

    public void loadEnderecos() {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        List<Endereco> enderecos = db.enderecoDao().getAllEnderecos();
        items.clear();
        for (Endereco endereco : enderecos) {
            items.add(endereco.getDescricao());
        }
        adapter.notifyDataSetChanged();
    }
}
