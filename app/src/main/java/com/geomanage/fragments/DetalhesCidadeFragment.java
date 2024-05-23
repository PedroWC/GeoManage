package com.geomanage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class DetalhesCidadeFragment extends Fragment {

    private int cidadeId;
    private TextView cidadeTextView;
    private TextView estadoTextView;
    private Cidade cidade;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cidadeId = getArguments().getInt("cidadeId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        AppDatabase db = AppDatabase.getDatabase(getContext());
        View view = inflater.inflate(R.layout.fragment_detalhes_cidade, container, false);
        try {
            // Carregar os dados da cidade com base no cidadeId
            cidade = db.cidadeDao().getCidadeById(cidadeId);

            cidadeTextView = view.findViewById(R.id.nomeCidadeEditText);
            cidadeTextView.setText(cidade.getCidade());
            estadoTextView = view.findViewById(R.id.estadoEditText);
            estadoTextView.setText(cidade.getEstado());

            // Botoes
            AppCompatButton btEditarCidade = view.findViewById(R.id.btEditarCidade);
            btEditarCidade.setOnClickListener(v -> {
                try {
                    cidade.setCidade((String) cidadeTextView.getText());
                    cidade.setEstado((String) estadoTextView.getText());

                    db.cidadeDao().update(cidade);
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar.make(view, "DetalhesCidadeFragment -> onCreateView: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            });
            AppCompatButton btExcluirCidade = view.findViewById(R.id.btExcluirCidade);
            btExcluirCidade.setOnClickListener(v -> {
                try {
                    db.cidadeDao().delete(cidade);

                } catch (Exception e) {
                    Snackbar snackbar = Snackbar.make(view, "DetalhesCidadeFragment -> onCreateView: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            });
            ImageButton btnCancelar = view.findViewById(R.id.btnCancelar);
            btnCancelar.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(view, "DetalhesCidadeFragment -> onCreateView: " + Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        return view;
    }
}