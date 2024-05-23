package com.geomanage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Endereco;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarEnderecoFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextInputEditText   latitude, longitude, titulo;
    private Double              vlr_latitude = -20.50232, vlr_longitude = -54.61349;
    private String              vlr_titulo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.fragment_adicionar_endereco, container, false);

        latitude    = view.findViewById(R.id.edt_latitudeEndereco);
        longitude   = view.findViewById(R.id.edt_longitudeEndereco);
        titulo      = view.findViewById(R.id.edt_tituloEndereco);
        AppCompatButton btnBuscarEndereco = view.findViewById(R.id.btn_buscar_endereco);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_endereco);
        mapFragment.getMapAsync(this);

        btnBuscarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vlr_latitude     = Double.parseDouble(latitude.getText().toString());
                vlr_longitude    = Double.parseDouble(longitude.getText().toString());
                vlr_titulo       = titulo.getText().toString();

                mapFragment.getMapAsync(AdicionarEnderecoFragment.this);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(vlr_latitude, vlr_longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(vlr_titulo));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    public void buscarEndereco(View view) {
        try {
            vlr_latitude = Double.parseDouble(latitude.getText().toString());
            vlr_longitude = Double.parseDouble(longitude.getText().toString());

            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", vlr_latitude);
            bundle.putDouble("longitude", vlr_longitude);

            // Usando NavController para navegar para o próximo fragmento
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_adicionarEnderecoFragment_to_detalhesEnderecoFragment, bundle);
        } catch (Exception e) {
            // Trate a exceção conforme necessário
        }
    }
}
