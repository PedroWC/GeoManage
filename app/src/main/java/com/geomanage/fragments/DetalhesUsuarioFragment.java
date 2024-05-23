package com.geomanage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Usuario;


public class DetalhesUsuarioFragment extends Fragment {

    private View view;
    private int usuarioId;
    private Usuario usuario;
    private AppDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usuarioId = getArguments().getInt("usuarioId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        db = AppDatabase.getDatabase(getContext());
        view = inflater.inflate(R.layout.fragment_detalhes_usuario, container, false);

        usuario = db.usuarioDao().getUsuarioById(usuarioId);

        // TODO: Implementar resto da lógica

        return view;
    }
}