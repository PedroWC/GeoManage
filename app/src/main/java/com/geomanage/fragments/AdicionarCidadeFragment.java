package com.geomanage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;

public class AdicionarCidadeFragment extends Fragment {

    private EditText nomeEditText;
    private EditText estadoEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adicionar_cidade, container, false);

        nomeEditText = view.findViewById(R.id.nomeCidadeEditText);
        estadoEditText = view.findViewById(R.id.estadoEditText);
        AppCompatButton adicionarButton = view.findViewById(R.id.btRegistrarCidade);

        adicionarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { adicionarCidade(); }
        });

        return view;
    }

    private void adicionarCidade() {
        String nome = nomeEditText.getText().toString();
        String estado = estadoEditText.getText().toString();

        if (nome.isEmpty() || estado.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase db = AppDatabase.getDatabase(getContext());
        Cidade cidade = new Cidade();
        cidade.setCidade(nome);
        cidade.setEstado(estado);
        db.cidadeDao().insert(cidade);

        Toast.makeText(getContext(), "Cidade adicionada com sucesso", Toast.LENGTH_SHORT).show();

        NavController navController = Navigation.findNavController(getView());
        navController.popBackStack(); // Volta para o fragmento anterior
    }
}
