package com.geomanage.activities.cidade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.geomanage.R;
import com.geomanage.fragments.AdicionarCidadeFragment;

public class AdicionarCidadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cidade);

        if (savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            AdicionarCidadeFragment fragment = new AdicionarCidadeFragment();
            Bundle args = new Bundle();
            args.putString("TYPE", "CIDADE");
            fragment.setArguments(args);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_adicionar, fragment)
                    .commit();
        }
    }
}