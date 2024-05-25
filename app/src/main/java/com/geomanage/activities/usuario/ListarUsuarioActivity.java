package com.geomanage.activities.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Usuario;
import com.geomanage.activities.usuario.DetalhesUsuarioActivity;

import java.util.ArrayList;
import java.util.List;

public class ListarUsuarioActivity extends AppCompatActivity {

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
            Intent intent = new Intent(ListarUsuarioActivity.this, DetalhesUsuarioActivity.class);
            intent.putExtra("usuarioId", position);
            startActivity(intent);
        });

        loadUsuarios();
    }

    public void loadUsuarios() {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        List<Usuario> usuarios = db.usuarioDao().getAllUsuarios();
        items.clear();
        for (Usuario usuario : usuarios) {
            items.add(usuario.getNome());
        }
        adapter.notifyDataSetChanged();
    }
}
