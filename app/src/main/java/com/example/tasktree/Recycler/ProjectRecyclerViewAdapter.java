package com.example.tasktree.Recycler;

import android.content.ContentValues;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasktree.Data.ProjectDatabaseHandler;
import com.example.tasktree.Data.TaskDatabaseHandler;
import com.example.tasktree.Models.Project;

import java.util.List;

public class ProjectRecyclerViewAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    private List<Project> projectList;
    private ProjectDatabaseHandler projectDB;
    private TaskDatabaseHandler taskDB;
    private Context context;

    public ProjectRecyclerViewAdapter(Context context) {
        this.context = context;
        this.projectDB = new ProjectDatabaseHandler(context);
        this.taskDB = new TaskDatabaseHandler(context);
        this.projectList = projectDB.getAllProjects();
    }


    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
