package com.geomanage.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geomanage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalhesEnderecoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalhesEnderecoFragment extends Fragment {

    private static final String ARG_DESCRICAO = "descricao";
    private static final String ARG_RUA = "rua";
    private static final String ARG_BAIRRO = "bairro";
    private static final String ARG_CIDADE = "cidade";
    private static final String ARG_ESTADO = "estado";
    private static final String ARG_PAIS = "pais";
    private static final String ARG_CEP = "cep";

    private String descricao;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;

    public static DetalhesEnderecoFragment newInstance(String descricao, String rua, String bairro, String cidade, String estado, String pais, String cep) {
        DetalhesEnderecoFragment fragment = new DetalhesEnderecoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DESCRICAO, descricao);
        args.putString(ARG_RUA, rua);
        args.putString(ARG_BAIRRO, bairro);
        args.putString(ARG_CIDADE, cidade);
        args.putString(ARG_ESTADO, estado);
        args.putString(ARG_PAIS, pais);
        args.putString(ARG_CEP, cep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            descricao = getArguments().getString(ARG_DESCRICAO);
            rua = getArguments().getString(ARG_RUA);
            bairro = getArguments().getString(ARG_BAIRRO);
            cidade = getArguments().getString(ARG_CIDADE);
            estado = getArguments().getString(ARG_ESTADO);
            pais = getArguments().getString(ARG_PAIS);
            cep = getArguments().getString(ARG_CEP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_endereco, container, false);
        TextView descricaoTextView = view.findViewById(R.id.descricaoTextView);
        TextView ruaTextView = view.findViewById(R.id.ruaTextView);
        TextView bairroTextView = view.findViewById(R.id.bairroTextView);
        TextView cidadeTextView = view.findViewById(R.id.cidadeTextView);
        TextView estadoTextView = view.findViewById(R.id.estadoTextView);
        TextView paisTextView = view.findViewById(R.id.paisTextView);
        TextView cepTextView = view.findViewById(R.id.cepTextView);

        descricaoTextView.setText(descricao);
        ruaTextView.setText(rua);
        bairroTextView.setText(bairro);
        cidadeTextView.setText(cidade);
        estadoTextView.setText(estado);
        paisTextView.setText(pais);
        cepTextView.setText(cep);

        return view;
    }
}