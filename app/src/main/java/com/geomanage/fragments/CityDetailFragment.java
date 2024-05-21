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
 * Use the {@link CityDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityDetailFragment extends Fragment {

    private static final String ARG_CIDADE = "cidade";
    private static final String ARG_ESTADO = "estado";

    private String cidade;
    private String estado;

    public static CityDetailFragment newInstance(String cidade, String estado) {
        CityDetailFragment fragment = new CityDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CIDADE, cidade);
        args.putString(ARG_ESTADO, estado);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cidade = getArguments().getString(ARG_CIDADE);
            estado = getArguments().getString(ARG_ESTADO);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_detail, container, false);
        TextView cidadeTextView = view.findViewById(R.id.cidadeTextView);
        TextView estadoTextView = view.findViewById(R.id.estadoTextView);

        cidadeTextView.setText(cidade);
        estadoTextView.setText(estado);

        return view;
    }
}