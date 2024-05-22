package com.geomanage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Endereco;

public class AdicionarEnderecoFragment extends Fragment {

    private EditText descricaoEditText;
    private EditText ruaEditText;
    private EditText bairroEditText;
    private EditText cidadeEditText;
    private EditText estadoEditText;
    private EditText paisEditText;
    private EditText cepEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adicionar_endereco, container, false);

        descricaoEditText = view.findViewById(R.id.descricaoEditText);
        ruaEditText = view.findViewById(R.id.ruaEditText);
        bairroEditText = view.findViewById(R.id.bairroEditText);
        cidadeEditText = view.findViewById(R.id.cidadeEditText);
        estadoEditText = view.findViewById(R.id.estadoEditText);
        paisEditText = view.findViewById(R.id.paisEditText);
        cepEditText = view.findViewById(R.id.cepEditText);
        Button adicionarButton = view.findViewById(R.id.adicionarButton);

        adicionarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarEndereco();
            }
        });

        return view;
    }

    private void adicionarEndereco() {
        String descricao = descricaoEditText.getText().toString();
        String rua = ruaEditText.getText().toString();
        String bairro = bairroEditText.getText().toString();
        String cidade = cidadeEditText.getText().toString();
        String estado = estadoEditText.getText().toString();
        String pais = paisEditText.getText().toString();
        String cep = cepEditText.getText().toString();

        if (descricao.isEmpty() || rua.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty() || pais.isEmpty() || cep.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase db = AppDatabase.getDatabase(getContext());
        Endereco endereco = new Endereco();
        endereco.setDescricao(descricao);
        endereco.setCidadeId();
        endereco.setLatitude();
        endereco.setLongitude();
        db.enderecoDao().insert(endereco);

        Toast.makeText(getContext(), "Endereço adicionado com sucesso", Toast.LENGTH_SHORT).show();

        NavController navController = Navigation.findNavController(getView());
        navController.popBackStack(); // Volta para o fragmento anterior
    }
}
