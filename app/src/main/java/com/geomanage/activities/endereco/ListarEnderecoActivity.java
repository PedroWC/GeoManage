package com.geomanage.activities.endereco;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.geomanage.R;
import com.geomanage.fragments.ListarFragment;

public class ListarEnderecoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        if (savedInstanceState == null) {
            ListarFragment fragment = new ListarFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
            fragment.loadEnderecos();
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment instanceof ListarFragment) {
                ((ListarFragment) fragment).loadEnderecos();
            }
        }
    }

    // TODO: tela de criação de endereco
}