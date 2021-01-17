package com.example.tasktree.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tasktree.Activity.MainActivity;
import com.example.tasktree.Data.ProjectDatabaseHandler;
import com.example.tasktree.Models.Project;
import com.example.tasktree.R;

import java.time.chrono.MinguoChronology;
import java.util.Date;

public class HomeHeaderFragment extends Fragment {
    private View homeHeaderView;
    private ProjectDatabaseHandler projectDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeHeaderView = inflater.inflate(R.layout.fragment_home_header, container);

        // initialize
        projectDB = new ProjectDatabaseHandler(getContext());
        TextView addButton = homeHeaderView.findViewById(R.id.homeHeaderAddButton);

        // onClick
        addButton.setOnClickListener(this::addButtonOnClick);

        return homeHeaderView;
    }


    private void addButtonOnClick(View view) {
        // 1. show dialog
        // 2. create data and save to DB
        // 3. restart activity
        // 1.
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        EditText editText = new EditText(getContext());
        editText.setHint("タイトルを入力してください.");
        builder.setTitle("NEW Project");
        builder.setMessage("タイトルを入力してください.");
        builder.setView(editText);
        builder.setPositiveButton("作成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 2.
                Project project = new Project();
                project.setTitle(editText.getText().toString());
                project.setFinish(false);
                project.setColorInteger(1);
                project.setCreatedDate(new Date());
                projectDB.add(project);
                // 3.
                Intent intent = new Intent(getContext(), MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
