package com.example.tasktree.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tasktree.R;

public class TreeFooterFragment extends Fragment {
    View treeFooterView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        treeFooterView = inflater.inflate(R.layout.fragment_project_tree_footer, container);

        return treeFooterView;
    }
}
