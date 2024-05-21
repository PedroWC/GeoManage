package com.geomanage.activities.endereco;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.fragments.ListarFragment;

public class EnderecoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        if (savedInstanceState == null) {
            ListarFragment fragment = ListarFragment.newInstance(ListarFragment.TYPE_ADDRESS);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    // TODO: tela de criação de endereco
}