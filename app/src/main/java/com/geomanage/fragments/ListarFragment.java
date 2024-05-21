package com.geomanage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.geomanage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Uma subclasse {@link Fragment} simples.
 * Use o método de fábrica {@link ListarFragment#newInstance} para
 * crie uma instância deste fragmento.
 */
public class ListarFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    public static final String TYPE_CITY = "city";
    public static final String TYPE_ADDRESS = "address";

    private String type;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> items = new ArrayList<>();

    public static ListarFragment newInstance(String type) {
        ListarFragment fragment = new ListarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar, container, false);
        listView = view.findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        return view;
    }

    public void updateItems(List<String> newItems) {
        items.clear();
        items.addAll(newItems);
        adapter.notifyDataSetChanged();
    }
}


