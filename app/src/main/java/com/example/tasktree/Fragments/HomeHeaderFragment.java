package com.example.tasktree.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tasktree.R;

public class HomeHeaderFragment extends Fragment {
    private View homeHeaderView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeHeaderView = inflater.inflate(R.layout.fragment_home_header, container);
        // todo setOnClickListener
        return homeHeaderView;
    }
}
