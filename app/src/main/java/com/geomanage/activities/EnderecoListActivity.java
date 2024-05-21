package com.geomanage.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.R;
import com.geomanage.fragments.ListFragment;

public class EnderecoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState == null) {
            ListFragment fragment = ListFragment.newInstance(ListFragment.TYPE_ADDRESS);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}