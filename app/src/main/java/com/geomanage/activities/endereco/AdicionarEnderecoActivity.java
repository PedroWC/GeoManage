package com.geomanage.activities.endereco;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.geomanage.entities.Endereco;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class AdicionarEnderecoActivity extends AppCompatActivity {

    private static final String TAG = "AdicionarEnderecoActivity";
    private TextInputEditText latitude, longitude, titulo;
    private Spinner spinnerCidades;
    private String vlr_latitude = "-20.50232", vlr_longitude = "-54.61349"; // Localização padrão: FACOM
    private String vlr_titulo = "FACOM UFMS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_endereco);

        // Pegar elementos da página
        latitude = findViewById(R.id.edt_latitudeEndereco);
        longitude = findViewById(R.id.edt_longitudeEndereco);
        titulo = findViewById(R.id.edt_tituloEndereco);
        spinnerCidades = findViewById(R.id.spinner_cidades);

        // Preencher spinner de cidades
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        List<Cidade> listaCidades = db.cidadeDao().getAllCidades();
        ArrayAdapter<Cidade> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidades.setAdapter(adapter);

        // Definir ação do botão que atualiza mapa
        AppCompatButton btnBuscarEndereco = findViewById(R.id.btn_buscar_endereco);
        btnBuscarEndereco.setOnClickListener(v -> { buscarEndereco(); });

        // Definir ação do botão que salva o endereço
        AppCompatButton btnSalvarEndereco = findViewById(R.id.btn_salvar_endereco);
        btnSalvarEndereco.setOnClickListener(v -> {
            vlr_latitude = Objects.requireNonNull(latitude.getText()).toString();
            vlr_longitude = Objects.requireNonNull(longitude.getText()).toString();
            vlr_titulo = Objects.requireNonNull(titulo.getText()).toString();
            Cidade cidadeSelecionada = (Cidade) spinnerCidades.getSelectedItem();
            int cidadeId = cidadeSelecionada.getCidadeId();
            Endereco endereco = new Endereco();
            endereco.setCidadeId(cidadeId);
            endereco.setDescricao(vlr_titulo);
            endereco.setLatitude(Double.parseDouble(vlr_latitude));
            endereco.setLongitude(Double.parseDouble(vlr_longitude));

            db.enderecoDao().insert(endereco);

            Toast.makeText(AdicionarEnderecoActivity.this, "Endereço adicionado no banco com sucesso!", Toast.LENGTH_LONG).show();
        });

        // Definir ação do botão voltar
        ImageButton btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }
    private void buscarEndereco() {
        vlr_latitude = (Objects.requireNonNull(latitude.getText()).toString());
        vlr_longitude = (Objects.requireNonNull(longitude.getText()).toString());
        vlr_titulo = Objects.requireNonNull(titulo.getText()).toString();
        Log.d(TAG, "Buscar Endereço: Latitude: " + vlr_latitude + ", Longitude: " + vlr_longitude + ", Título: " + vlr_titulo);
        if (vlr_latitude.isEmpty() || vlr_longitude.isEmpty()){
            Toast.makeText(this, "Latitude e longitude obrigatórias", Toast.LENGTH_SHORT).show();
            return;
        }
        double lat, longi;
        try {
            lat = Double.parseDouble(vlr_latitude);
            longi = Double.parseDouble(vlr_longitude);
        }catch (NumberFormatException e){
            Toast.makeText(this, "Latitude e longitude devem ser válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        int zoomLevel = 15;
        String formatedUri = String.format("geo:%s,%s?q=%s,%s&z=%d", lat, longi, lat, longi, zoomLevel);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(formatedUri));
        intent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(formatedUri));
            try {
                startActivity(unrestrictedIntent);
            }catch (ActivityNotFoundException innerE){
                Toast.makeText(this, "Nenhuma aplicação encontrada", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
