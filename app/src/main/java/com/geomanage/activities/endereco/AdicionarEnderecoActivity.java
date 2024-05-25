package com.geomanage.activities.endereco;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.geomanage.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AdicionarEnderecoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "AdicionarEnderecoActivity";

    private GoogleMap googleMap;
    private TextInputEditText latitude, longitude, titulo;
    private Double vlr_latitude = -20.50232, vlr_longitude = -54.61349; // Localização padrão: FACOM
    private String vlr_titulo = "Localização Padrão";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_endereco);

        latitude = findViewById(R.id.edt_latitudeEndereco);
        longitude = findViewById(R.id.edt_longitudeEndereco);
        titulo = findViewById(R.id.edt_tituloEndereco);
        System.out.println("ANTES DO SUPPORT MANAGER");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_endereco);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "Map Fragment is null");
        }

        AppCompatButton btnBuscarEndereco = findViewById(R.id.btn_buscar_endereco);
        btnBuscarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEndereco();
            }
        });

        ImageButton btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        this.googleMap = map;
        atualizarMapa();
    }

    private void buscarEndereco() {
        try {
            vlr_latitude = Double.parseDouble(Objects.requireNonNull(latitude.getText()).toString());
            vlr_longitude = Double.parseDouble(Objects.requireNonNull(longitude.getText()).toString());
            vlr_titulo = Objects.requireNonNull(titulo.getText()).toString();
            Log.d(TAG, "Buscar Endereço: Latitude: " + vlr_latitude + ", Longitude: " + vlr_longitude + ", Título: " + vlr_titulo);
            atualizarMapa();
        } catch (NumberFormatException e) {
            Log.e(TAG, "Erro ao converter coordenadas", e);
        }
    }

    private void atualizarMapa() {
        if (googleMap != null) {
            LatLng latLng = new LatLng(vlr_latitude, vlr_longitude);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title(vlr_titulo));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } else {
            Log.e(TAG, "GoogleMap não está pronto");
        }
    }
}
