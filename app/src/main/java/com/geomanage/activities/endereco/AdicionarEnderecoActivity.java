package com.geomanage.activities.endereco;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.geomanage.entities.Endereco;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class AdicionarEnderecoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "AdicionarEnderecoActivity";
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private TextInputEditText latitude, longitude, titulo;
    private Spinner spinnerCidades;
    private Double vlr_latitude = -20.50232, vlr_longitude = -54.61349; // Localização padrão: FACOM
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

        // Atualizar mapa
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_endereco);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "Map Fragment is null");
        }

        // Definir ação do botão que atualiza mapa
        AppCompatButton btnBuscarEndereco = findViewById(R.id.btn_buscar_endereco);
        btnBuscarEndereco.setOnClickListener(v -> {
            try {
                vlr_latitude = Double.parseDouble(Objects.requireNonNull(latitude.getText()).toString());
                vlr_longitude = Double.parseDouble(Objects.requireNonNull(longitude.getText()).toString());
                vlr_titulo = Objects.requireNonNull(titulo.getText()).toString();
                Log.d(TAG, "Buscar Endereço: Latitude: " + vlr_latitude + ", Longitude: " + vlr_longitude + ", Título: " + vlr_titulo);
                mapFragment.getMapAsync(AdicionarEnderecoActivity.this);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Erro ao converter coordenadas", e);
            }
        });

        // Definir ação do botão que salva o endereço
        AppCompatButton btnSalvarEndereco = findViewById(R.id.btn_salvar_endereco);
        btnSalvarEndereco.setOnClickListener(v -> {
            vlr_latitude = Double.parseDouble(Objects.requireNonNull(latitude.getText()).toString());
            vlr_longitude = Double.parseDouble(Objects.requireNonNull(longitude.getText()).toString());
            vlr_titulo = Objects.requireNonNull(titulo.getText()).toString();
            Cidade cidadeSelecionada = (Cidade) spinnerCidades.getSelectedItem();
            int cidadeId = cidadeSelecionada.getCidadeId();
            Endereco endereco = new Endereco();
            endereco.setCidadeId(cidadeId);
            endereco.setDescricao(vlr_titulo);
            endereco.setLatitude(vlr_latitude);
            endereco.setLongitude(vlr_longitude);

            db.enderecoDao().insert(endereco);

            Toast.makeText(AdicionarEnderecoActivity.this, "Endereço adicionado no banco com sucesso!", Toast.LENGTH_LONG).show();
        });

        // Definir ação do botão voltar
        ImageButton btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        this.googleMap = map;
        atualizarMapa();
    }

    public void visualizarInfosEndereco(View view) {
        try {
            vlr_latitude = Double.parseDouble(Objects.requireNonNull(latitude.getText()).toString());
            vlr_longitude = Double.parseDouble(Objects.requireNonNull(longitude.getText()).toString());
            Intent intent=new Intent(AdicionarEnderecoActivity.this, InformacoesEndereco.class);
            Bundle bundle=new Bundle();
            bundle.putDouble("latitude", vlr_latitude);
            bundle.putDouble("longitude", vlr_longitude);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }catch (Exception ignored) {
        }
    }

    private void atualizarMapa() {
        if (googleMap != null) {
            LatLng latLng = new LatLng(vlr_latitude, vlr_longitude);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(vlr_titulo));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } else {
            Log.e(TAG, "GoogleMap não está pronto");
        }
    }
}
