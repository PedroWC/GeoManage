package com.geomanage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;

public class DetalhesCidadeFragment extends Fragment {

    private int cidadeId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cidadeId = getArguments().getInt("cidadeId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_cidade, container, false);
        TextView cidadeTextView = view.findViewById(R.id.cidadeTextView);
        TextView estadoTextView = view.findViewById(R.id.estadoTextView);

        // Carregar os dados da cidade com base no cidadeId
        AppDatabase db = AppDatabase.getDatabase(getContext());
        Cidade cidade = db.cidadeDao().getCidadeById(cidadeId);

        cidadeTextView.setText(cidade.getCidade());
        estadoTextView.setText(cidade.getEstado());

        return view;
    }
}