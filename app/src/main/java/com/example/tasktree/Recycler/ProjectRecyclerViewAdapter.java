package com.example.tasktree.Recycler;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasktree.Activity.TreeActivity;
import com.example.tasktree.Data.ProjectDatabaseHandler;
import com.example.tasktree.Data.TaskDatabaseHandler;
import com.example.tasktree.Models.Project;
import com.example.tasktree.R;

import java.text.SimpleDateFormat;
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
        View view = LayoutInflater.from(context).inflate(R.layout.row_home, null);
        ProjectViewHolder viewHolder = new ProjectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        // initialize
        // color box の色を変更
        GradientDrawable colorBoxBackground = (GradientDrawable) holder.homeListColorBox.getBackground();
        colorBoxBackground.setColor(context.getResources().getColor(R.color.black));
        // タイトルを変更
        holder.homeListTitle.setText(projectList.get(position).getTitle());
        // todo holder.homeListProgress
        // 日付を変更
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String stringCreatedAt = simpleDateFormat.format(projectList.get(position).getCreatedDate());
        holder.homeListDate.setText(stringCreatedAt);
        // todo holder.homeListEditIcon


        // add functions
        holder.homeListLinearLayout.setOnClickListener(this::linearLayoutOnClick);
    }


    @Override
    public int getItemCount() {
        return projectList.size();
    }



    private void linearLayoutOnClick(View view) {
        Intent intent = new Intent(context, TreeActivity.class);
        context.startActivity(intent);
    }

}
