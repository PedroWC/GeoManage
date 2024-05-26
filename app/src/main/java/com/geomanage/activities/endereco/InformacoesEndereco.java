package com.geomanage.activities.endereco;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.geomanage.R;
import com.geomanage.databinding.ActivityInformacoesEnderecoBinding;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class InformacoesEndereco extends AppCompatActivity {

    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.geomanage.databinding.ActivityInformacoesEnderecoBinding binding = ActivityInformacoesEnderecoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        assert bundle != null;
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

        try {
            Address address = buscarEndereco();
            binding.enderecoEditText.setText(MessageFormat.format("{0}\n Latitude {1} Longitude {2}\n{3}",
                    binding.enderecoEditText.getText(),
                    latitude,
                    longitude,
                    address.getAddressLine(0)));
            binding.ruaEditText.setText(MessageFormat.format("{0} {1}",
                    binding.ruaEditText.getText(),
                    address.getThoroughfare()));
            binding.bairroEditText.setText(MessageFormat.format("{0} {1}",
                    binding.bairroEditText.getText(),
                    address.getSubLocality()));
            binding.cidadeEditText.setText(MessageFormat.format("{0} {1}",
                    binding.cidadeEditText.getText(),
                    address.getSubAdminArea()));
            binding.estadoEditText.setText(MessageFormat.format("{0} {1}",
                    binding.estadoEditText.getText(),
                    address.getAdminArea()));
            binding.paisEditText.setText(MessageFormat.format("{0} {1}",
                    binding.paisEditText.getText(),
                    address.getCountryName()));
            binding.cepEditText.setText(MessageFormat.format("{0} {1}",
                    binding.cepEditText.getText(),
                    address.getPostalCode()));

        } catch (Exception ignored) {
        }

        AppCompatButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());
    }

    private Address buscarEndereco() throws IOException {
        Address address1 = null;
        List<Address> list;
        Geocoder geocoder = new Geocoder(getApplicationContext());
        list = geocoder.getFromLocation(latitude, longitude,1);
        assert list != null;
        if(!list.isEmpty()) {
            address1 = list.get(0);
        }
        return address1;
    }
}