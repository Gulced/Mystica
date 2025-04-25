package com.example.mystica.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystica.R;
import com.example.mystica.adapters.ThoughtAdapter;
import com.example.mystica.models.Thought;
import com.example.mystica.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerThoughts;
    private Button btnShowToday, btnClearAll;
    private ThoughtAdapter thoughtAdapter;
    private ArrayList<Thought> thoughtList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // View tanımlamaları

        recyclerThoughts = view.findViewById(R.id.recyclerThoughts);
        btnShowToday = view.findViewById(R.id.btnShowToday);
        btnClearAll = view.findViewById(R.id.btnClearAll);



        // Düşünce listesiyle başla
        thoughtList = new ArrayList<>(SharedPrefManager.getThoughts(requireContext()));
        setupRecycler(thoughtList);

        // Bugünküleri göster
        btnShowToday.setOnClickListener(v -> {
            List<Thought> todayList = SharedPrefManager.getTodayThoughts(requireContext());
            setupRecycler(todayList);
        });

        // Hepsini temizle
        btnClearAll.setOnClickListener(v -> {
            SharedPrefManager.clearAllThoughts(requireContext());
            setupRecycler(new ArrayList<>());
        });

        return view;
    }

    private void setupRecycler(List<Thought> list) {
        thoughtAdapter = new ThoughtAdapter(requireContext(), list);
        recyclerThoughts.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerThoughts.setAdapter(thoughtAdapter);
    }
}
