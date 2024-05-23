package com.geomanage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.geomanage.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AdicionarEnderecoFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private TextInputEditText   latitude, longitude, titulo;
    private Double              vlr_latitude = -20.50232, vlr_longitude = -54.61349; // Localização padrão: FACOM
    private String              vlr_titulo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.fragment_adicionar_endereco, container, false);

        latitude    = view.findViewById(R.id.edt_latitudeEndereco);
        longitude   = view.findViewById(R.id.edt_longitudeEndereco);
        titulo      = view.findViewById(R.id.edt_tituloEndereco);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_endereco);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // Botão buscar endereço
        AppCompatButton btnBuscarEndereco = view.findViewById(R.id.btn_buscar_endereco);
        btnBuscarEndereco.setOnClickListener(view1 -> buscarEndereco());

        // Botão visualizar detalhes do endereço
        AppCompatButton btnVisualizarEndereco = view.findViewById(R.id.btn_visualizar_endereco);
        btnVisualizarEndereco.setOnClickListener(v -> visualizarEndereco());

        // Botão cancelar
        ImageButton btnCancelar = view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
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

            mapFragment.getMapAsync(AdicionarEnderecoFragment.this);
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

            // Usando NavController para navegar para o próximo fragmento
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_adicionarEnderecoFragment_to_fragmentDetalhesEndereco, bundle);
        } catch (Exception ignored) {
        }
    }
}
