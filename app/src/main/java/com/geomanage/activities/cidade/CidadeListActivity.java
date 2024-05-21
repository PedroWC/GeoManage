package com.geomanage.activities.cidade;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.geomanage.fragments.ListarFragment;

import java.util.ArrayList;
import java.util.List;

public class CidadeListActivity extends AppCompatActivity {

    private AppDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        if (savedInstanceState == null) {
            ListarFragment fragment = ListarFragment.newInstance(ListarFragment.TYPE_CITY);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        DB = AppDatabase.getDatabase(getApplicationContext());
        buscarCidades();
    }

    private void buscarCidades() {
        List<Cidade> listaCidades = DB.cidadeDao().getAllCidades();
        List<String> cidadeNomes = new ArrayList<>();
        for (Cidade cidade : listaCidades) {
            cidadeNomes.add(cidade.getCidade());
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment instanceof ListarFragment) {
            ((ListarFragment) fragment).updateItems(cidadeNomes);
        }
    }

    // TODO: tela de criação de cidade
}