package com.geomanage.activities.cidade;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.geomanage.R;
import com.geomanage.fragments.ListarFragment;

public class ListarCidadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ListarFragment listarFragment = new ListarFragment();
            Bundle args = new Bundle();
            args.putString("TYPE", "CIDADE");
            listarFragment.setArguments(args);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_listar, listarFragment)
                    .commit();
        }
    }
}