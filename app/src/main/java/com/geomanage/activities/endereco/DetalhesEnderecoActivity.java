package com.geomanage.activities.endereco;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.geomanage.entities.Endereco;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class DetalhesEnderecoActivity extends AppCompatActivity {

    private AppDatabase db;
    private int enderecoId = -1;
    private Endereco endereco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_endereco);

        if (getIntent() != null) {
            enderecoId = getIntent().getIntExtra("endereco_id", -1);
        }

        db = AppDatabase.getDatabase(getApplicationContext());

        TextInputEditText descricaoEditText = findViewById(R.id.descricaoEditText);
        TextInputEditText ruaEditText       = findViewById(R.id.ruaEditText);
        TextInputEditText bairroEditText    = findViewById(R.id.bairroEditText);
        TextInputEditText paisEditText      = findViewById(R.id.paisEditText);
        TextInputEditText cepEditText       = findViewById(R.id.cepEditText);
        TextInputEditText cidadeEditText    = findViewById(R.id.cidadeEditText);

        endereco = db.enderecoDao().getEnderecoById(enderecoId);
        if (endereco != null) {
            descricaoEditText.setText(endereco.getDescricao());

            Cidade cidade = db.cidadeDao().getCidadeById(endereco.getCidadeId());
            if (cidade != null) {
                cidadeEditText.setText(cidade.getCidade());
            }

            Address address = buscarEndereco();
            if (address != null) {
                ruaEditText.setText(address.getThoroughfare());
                bairroEditText.setText(address.getSubLocality());
                paisEditText.setText(address.getCountryName());
                cepEditText.setText(address.getPostalCode());
            }
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Endereço não encontrado", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        AppCompatButton btExcluirEndereco = findViewById(R.id.btExcluirEndereco);
        btExcluirEndereco.setOnClickListener(v -> {
            if (endereco != null) {
                db.enderecoDao().delete(endereco);
                finish();
            }
        });

        ImageButton btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }

    private Address buscarEndereco() {
        Address vlr_address = null;

        try {
            Geocoder geocoder = new Geocoder(this.getApplicationContext());
            List<Address> list = geocoder.getFromLocation(endereco.getLatitude(), endereco.getLongitude(), 1);

            if (list != null && !list.isEmpty()) {
                vlr_address = list.get(0);
            }
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "DetalhesEnderecoActivity -> buscarEndereco: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        return vlr_address;
    }
}
