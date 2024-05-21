package com.geomanage.activities.cidade;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.fragments.DetalhesCidadeFragment;

public class DetalhesCidadeActivity extends AppCompatActivity {

    public static final String EXTRA_CIDADE = "cidade";
    public static final String EXTRA_ESTADO = "estado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        if (savedInstanceState == null) {
            String cidade = getIntent().getStringExtra(EXTRA_CIDADE);
            String estado = getIntent().getStringExtra(EXTRA_ESTADO);

            DetalhesCidadeFragment fragment = DetalhesCidadeFragment.newInstance(cidade, estado);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}