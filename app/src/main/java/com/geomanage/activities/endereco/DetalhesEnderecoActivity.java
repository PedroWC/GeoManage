package com.geomanage.activities.endereco;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.geomanage.entities.Endereco;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DetalhesEnderecoActivity extends AppCompatActivity {

    private View view;
    private AppDatabase db;
    private int enderecoId = -1;
    private double latitude;
    private double longitude;
    private String titulo;
    private TextInputEditText descricaoEditText;
    private TextInputEditText ruaEditText;
    private TextInputEditText bairroEditText;
    private Spinner cidadeSpinner;
    private TextInputEditText paisEditText;
    private TextInputEditText cepEditText;
    private List<Cidade> cidades;
    private Endereco endereco;
    private boolean isAdding = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_endereco);

        db = AppDatabase.getDatabase(getApplicationContext());

        if (getIntent() != null) {
            if (getIntent().hasExtra("endereco_id")) {
                enderecoId = getIntent().getIntExtra("endereco_id", -1);
            } else if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude") && getIntent().hasExtra("titulo")) {
                latitude = getIntent().getDoubleExtra("latitude", 0);
                longitude = getIntent().getDoubleExtra("longitude", 0);
                titulo = getIntent().getStringExtra("titulo");
                isAdding = true;
            }
        }

        try {
            cidadeSpinner = findViewById(R.id.spinner_cidades);
            cidades = db.cidadeDao().getAllCidades();
            ArrayAdapter<Cidade> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cidades);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cidadeSpinner.setAdapter(adapter);

            descricaoEditText = findViewById(R.id.descricaoEditText);
            ruaEditText = findViewById(R.id.ruaEditText);
            bairroEditText = findViewById(R.id.bairroEditText);
            paisEditText = findViewById(R.id.paisEditText);
            cepEditText = findViewById(R.id.cepEditText);

            AppCompatButton btExcluirEndereco = findViewById(R.id.btExcluirEndereco);
            ImageButton btnCancelar = findViewById(R.id.btnCancelar);
            btnCancelar.setOnClickListener(v -> finish());

            if (isAdding) {
                // Adicionar novo endereço
                btExcluirEndereco.setText(R.string.txt_button_registrar);
                btExcluirEndereco.setOnClickListener(v -> adicionarEndereco());
                descricaoEditText.setText(titulo);
                Address address = buscarEndereco();
                if (address != null) {
                    ruaEditText.setText(address.getThoroughfare());
                    bairroEditText.setText(address.getSubLocality());
                    paisEditText.setText(address.getCountryName());
                    cepEditText.setText(address.getPostalCode());
                }
            } else {
                // Listar detalhes do endereço existente
                btExcluirEndereco.setOnClickListener(v -> {
                    db.enderecoDao().delete(endereco);
                    finish();
                });
                carregarEndereco();
            }

        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "DetalhesEnderecoActivity -> onCreate: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    private void carregarEndereco() throws IOException {
        try {
            endereco = db.enderecoDao().getEnderecoById(enderecoId);

            if (endereco != null) {
                descricaoEditText.setText(endereco.getDescricao());
                Cidade cidade = db.cidadeDao().getCidadeById(endereco.getCidadeId());
                if (cidade != null) {
                    int position = cidades.indexOf(cidade);
                    cidadeSpinner.setSelection(position);
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
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "DetalhesEnderecoActivity -> carregarEndereco: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    private void adicionarEndereco() {
        try {
            Endereco novoEndereco = new Endereco();
            novoEndereco.setDescricao(Objects.requireNonNull(descricaoEditText.getText()).toString());
            novoEndereco.setCidadeId(((Cidade) cidadeSpinner.getSelectedItem()).getCidadeId());
            novoEndereco.setLatitude(latitude);
            novoEndereco.setLongitude(longitude);

            db.enderecoDao().insert(novoEndereco);
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Endereço adicionado com sucesso", Snackbar.LENGTH_SHORT);
            snackbar.show();
            finish();
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "DetalhesEnderecoActivity -> adicionarEndereco: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    private Address buscarEndereco() {
        Address vlr_address = null;

        try {
            Geocoder geocoder = new Geocoder(this.getApplicationContext());
            List<Address> list = geocoder.getFromLocation(latitude, longitude, 1);

            assert list != null;
            if (!list.isEmpty()) {
                vlr_address = list.get(0);
            }
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "DetalhesEnderecoActivity -> buscarEndereco: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        return vlr_address;
    }
}
