package com.geomanage.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.geomanage.fragments.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class CidadeListActivity extends AppCompatActivity {

    private AppDatabase DB;
    private List<Cidade> listaCidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState == null) {
            ListFragment fragment = ListFragment.newInstance(ListFragment.TYPE_CITY);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        DB = AppDatabase.getDatabase(getApplicationContext());
        buscarCidades();
    }

    private void buscarCidades() {
        listaCidades = DB.cidadeDao().getAllCidades();
        List<String> cidadeNomes = new ArrayList<>();
        for (Cidade cidade : listaCidades) {
            cidadeNomes.add(cidade.getCidade());
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment instanceof ListFragment) {
            ((ListFragment) fragment).updateItems(cidadeNomes);
        }
    }
}