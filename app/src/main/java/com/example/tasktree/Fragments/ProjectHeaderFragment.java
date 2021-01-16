package com.example.tasktree.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tasktree.R;

public class ProjectHeaderFragment extends Fragment {
    private View projectHeaderView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        projectHeaderView = inflater.inflate(R.layout.fragment_project_header, container);
        return projectHeaderView;
    }
}
