package com.geomanage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geomanage.R;
import com.geomanage.database.AppDatabase;
import com.geomanage.entities.Cidade;
import com.geomanage.entities.Endereco;
import com.geomanage.entities.Usuario;
import com.geomanage.utils.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ListarFragment extends Fragment {

    private ArrayAdapter<String> adapter;
    private final List<String> items = new ArrayList<>();

    private DataType TYPE;
    private int ID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TYPE = DataType.valueOf(getArguments().getString("TYPE"));
            this.ID = getArguments().getInt("id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar, container, false);
        ListView listView = view.findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            NavController navController = Navigation.findNavController(view1);

            // Dependendo da posição ou do item, navegue para o fragmento correto
            if (Objects.equals(TYPE, DataType.CIDADE)) {
                Bundle bundle = new Bundle();
                bundle.putInt("cidadeId", ID);
                navController.navigate(R.id.action_listar_to_detalhes_cidade, bundle);

            } else if (Objects.equals(TYPE, DataType.ENDERECO)) {
                Bundle bundle = new Bundle();
                bundle.putInt("enderecoId", ID);
                navController.navigate(R.id.action_listar_to_detalhes_endereco, bundle);

            } else if (Objects.equals(TYPE, DataType.USUARIO)) {
                Bundle bundle = new Bundle();
                bundle.putInt("usuarioId", ID);
                navController.navigate(R.id.action_listar_to_detalhes_usuario, bundle);
            }
        });

        return view;
    }

    public void loadCidades() {
        if (adapter == null) {
            return;
        }
        AppDatabase db = AppDatabase.getDatabase(getContext());
        List<Cidade> cidades = db.cidadeDao().getAllCidades();
        items.clear();
        for (Cidade cidade : cidades) {
            items.add(cidade.getCidade());
        }
        adapter.notifyDataSetChanged();
    }

    public void loadEnderecos() {
        if (adapter == null) {
            return;
        }
        AppDatabase db = AppDatabase.getDatabase(getContext());
        List<Endereco> enderecos = db.enderecoDao().getAllEnderecos();
        items.clear();
        for (Endereco endereco : enderecos) {
            items.add(endereco.getDescricao());
        }
        adapter.notifyDataSetChanged();
    }

    public void loadUsuarios() {
        if (adapter == null) {
            return;
        }
        AppDatabase db = AppDatabase.getDatabase(getContext());
        List<Usuario> usuarios = db.usuarioDao().getAllUsuarios();
        items.clear();
        for (Usuario usuario : usuarios) {
            items.add(usuario.getNome());
        }
        adapter.notifyDataSetChanged();
    }
}


