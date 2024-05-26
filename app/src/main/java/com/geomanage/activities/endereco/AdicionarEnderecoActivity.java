package com.geomanage.activities.endereco;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class AdicionarEnderecoActivity extends AppCompatActivity {

    private static final String TAG = "AdicionarEnderecoActivity";

    private GoogleMap googleMap;
    private TextInputEditText latitude, longitude, titulo;
    private String vlr_latitude = "-20.50232", vlr_longitude = "-54.61349"; // Localização padrão: FACOM
    private String vlr_titulo = "Localização Padrão";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_endereco);

        latitude = findViewById(R.id.edt_latitudeEndereco);
        longitude = findViewById(R.id.edt_longitudeEndereco);
        titulo = findViewById(R.id.edt_tituloEndereco);
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
