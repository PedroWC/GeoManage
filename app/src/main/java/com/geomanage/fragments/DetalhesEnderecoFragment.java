package com.geomanage.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.geomanage.entities.Endereco;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DetalhesEnderecoFragment extends Fragment {

    private View view;
    private AppDatabase db;
    private int enderecoId;
    private TextInputEditText descricaoEditText;
    private TextInputEditText ruaEditText;
    private TextInputEditText bairroEditText;
    private Spinner cidadeSpinner;
    private TextInputEditText paisEditText;
    private TextInputEditText cepEditText;
    private List<Cidade> cidades;
    private Endereco endereco;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            enderecoId = getArguments().getInt("endereco_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        db = AppDatabase.getDatabase(getContext());
        view = inflater.inflate(R.layout.fragment_detalhes_endereco, container, false);

        try {
            // Preenchendo o spinner com os nomes das cidades
            cidadeSpinner = view.findViewById(R.id.spinner_cidades);
            cidades = db.cidadeDao().getAllCidades();
            ArrayAdapter<Cidade> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, cidades);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cidadeSpinner.setAdapter(adapter);

            // Carregar os detalhes do endereço com base no enderecoId
            descricaoEditText = view.findViewById(R.id.descricaoEditText);
            ruaEditText = view.findViewById(R.id.ruaEditText);
            bairroEditText = view.findViewById(R.id.bairroEditText);
            paisEditText = view.findViewById(R.id.paisEditText);
            cepEditText = view.findViewById(R.id.cepEditText);
            carregarEndereco();

            AppCompatButton btExcluirEndereco = view.findViewById(R.id.btExcluirEndereco);
            btExcluirEndereco.setOnClickListener(v -> db.enderecoDao().delete(endereco));
            ImageButton btnCancelar = view.findViewById(R.id.btnCancelar);
            btnCancelar.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(view, "DetalhesEnderecoFragment -> onCreateView: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        return view;
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

                // busca dados do endereco com base nas coordenadas
                Address address = buscarEndereco();
                ruaEditText.setText(address.getThoroughfare());
                bairroEditText.setText(address.getSubLocality());
                paisEditText.setText(address.getCountryName());
                cepEditText.setText(address.getPostalCode());
            } else {
                Snackbar snackbar = Snackbar.make(view, "Endereço não encontrado", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(view, "DetalhesEnderecoFragment -> carregarEndereco: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    // metodo busca dados do endereco com base nas coordenadas
    private Address buscarEndereco() {
        Address vlr_address = null;

        try {
            Geocoder geocoder = new Geocoder(requireContext().getApplicationContext());
            List<Address> list = geocoder.getFromLocation(endereco.getLatitude(), endereco.getLongitude(), 1);

            assert list != null;
            if (!list.isEmpty()) {
                vlr_address = list.get(0);
            }
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(view, "DetalhesEnderecoFragment -> buscarEndereco: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        return vlr_address;
    }
}
