package com.example.tasktree.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tasktree.R;

public class ListFooterFragment extends Fragment {
    View listFooterView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listFooterView = inflater.inflate(R.layout.fragment_project_list_footer, container);

        return listFooterView;
    }
}
