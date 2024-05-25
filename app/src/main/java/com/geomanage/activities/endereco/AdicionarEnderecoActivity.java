package com.geomanage.activities.endereco;

import android.os.Bundle;
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

    private SupportMapFragment mapFragment;
    private TextInputEditText latitude, longitude, titulo;
    private Double vlr_latitude = -20.50232, vlr_longitude = -54.61349; // Localização padrão: FACOM
    private String vlr_titulo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_endereco);

        latitude = findViewById(R.id.edt_latitudeEndereco);
        longitude = findViewById(R.id.edt_longitudeEndereco);
        titulo = findViewById(R.id.edt_tituloEndereco);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_endereco);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // Botão buscar endereço
        AppCompatButton btnBuscarEndereco = findViewById(R.id.btn_buscar_endereco);
        btnBuscarEndereco.setOnClickListener(view -> buscarEndereco());

        // Botão visualizar detalhes do endereço
        AppCompatButton btnVisualizarEndereco = findViewById(R.id.btn_visualizar_endereco);
        btnVisualizarEndereco.setOnClickListener(v -> visualizarEndereco());

        // Botão cancelar
        ImageButton btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng = new LatLng(vlr_latitude, vlr_longitude);

        googleMap.addMarker(new MarkerOptions().position(latLng).title(vlr_titulo));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    public void buscarEndereco() {
        try {
            vlr_latitude = Double.parseDouble(Objects.requireNonNull(latitude.getText()).toString());
            vlr_longitude = Double.parseDouble(Objects.requireNonNull(longitude.getText()).toString());
            vlr_titulo = Objects.requireNonNull(titulo.getText()).toString();

            mapFragment.getMapAsync(AdicionarEnderecoActivity.this);
        } catch (Exception ignored) {
        }
    }

    public void visualizarEndereco() {
        try {
            vlr_latitude = Double.parseDouble(Objects.requireNonNull(latitude.getText()).toString());
            vlr_longitude = Double.parseDouble(Objects.requireNonNull(longitude.getText()).toString());

            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", vlr_latitude);
            bundle.putDouble("longitude", vlr_longitude);

            // Navegar para a Activity de detalhes (substitua com sua Activity de destino)
            // Intent intent = new Intent(this, DetalhesEnderecoActivity.class);
            // intent.putExtras(bundle);
            // startActivity(intent);
        } catch (Exception ignored) {
        }
    }
}
