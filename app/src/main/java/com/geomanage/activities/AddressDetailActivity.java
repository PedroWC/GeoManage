package com.geomanage.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.geomanage.databinding.FragmentAddressDetailBinding;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class AddressDetailActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentAddressDetailBinding binding = FragmentAddressDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        assert bundle != null;
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

        try {
            if (savedInstanceState == null) {
                Address address = buscarEndereco();

                binding.descricaoTextView.setText(MessageFormat.format("{0}\n Latitude {1} Longitude {2}\n{3}",
                        binding.descricaoTextView.getText(),
                        latitude,
                        longitude,
                        address.getAddressLine(0)
                ));
                binding.ruaTextView.setText(MessageFormat.format("{0} {1}",
                        binding.ruaTextView.getText(),
                        address.getThoroughfare()
                ));
                binding.bairroTextView.setText(MessageFormat.format("{0} {1}",
                        binding.bairroTextView.getText(),
                        address.getSubLocality()
                ));
                binding.cidadeTextView.setText(MessageFormat.format("{0} {1}",
                        binding.cidadeTextView.getText(),
                        address.getSubAdminArea()
                ));
                binding.estadoTextView.setText(MessageFormat.format("{0} {1}",
                        binding.estadoTextView.getText(),
                        address.getSubAdminArea()
                ));
                binding.paisTextView.setText(MessageFormat.format("{0} {1}",
                        binding.paisTextView.getText(),
                        address.getCountryName()
                ));
                binding.cepTextView.setText(MessageFormat.format("{0} {1}",
                        binding.cepTextView.getText(),
                        address.getPostalCode()
                ));
            }

        } catch (Exception ignored) {
        }
    }

    private Address buscarEndereco() throws IOException {
        Address address = null;
        List<Address> list;
        Geocoder geocoder = new Geocoder(getApplicationContext());
        list = geocoder.getFromLocation(latitude, longitude, 1);

        assert list != null;
        if (!list.isEmpty()) {
            address = list.get(0);
        }

        return address;
    }

    // TODO: tela de visualização de endereço no mapa
}