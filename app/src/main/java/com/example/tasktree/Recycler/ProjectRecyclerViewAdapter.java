package com.example.tasktree.Recycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasktree.Activity.MainActivity;
import com.example.tasktree.Activity.TreeActivity;
import com.example.tasktree.Data.ProjectDatabaseHandler;
import com.example.tasktree.Data.TaskDatabaseHandler;
import com.example.tasktree.Models.Project;
import com.example.tasktree.R;

import java.io.BufferedReader;
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

        // todo reverse projectList
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
        switch (projectList.get(position).getColorInteger()) {
            case 1:
                colorBoxBackground.setColor(context.getResources().getColor(R.color.project_color_red));
                break;
            case 2:
                colorBoxBackground.setColor(context.getResources().getColor(R.color.project_color_blue));
                break;
            case 3:
                colorBoxBackground.setColor(context.getResources().getColor(R.color.project_color_green));
                break;
            case 4:
                colorBoxBackground.setColor(context.getResources().getColor(R.color.project_color_yellow));
                break;
            case 5:
                colorBoxBackground.setColor(context.getResources().getColor(R.color.project_color_gray));
                break;
        }
        // タイトルを変更
        holder.homeListTitle.setText(projectList.get(position).getTitle());
        // todo holder.homeListProgress
        // 日付を変更
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String stringCreatedAt = simpleDateFormat.format(projectList.get(position).getCreatedDate());
        holder.homeListDate.setText(stringCreatedAt);


        // add tags
        holder.homeListColorBox.setTag(projectList.get(position));
        holder.homeListLinearLayout.setTag(projectList.get(position));
        holder.homeListEditIcon.setTag(projectList.get(position));

        // add functions
        holder.homeListColorBox.setOnClickListener(this::colorBoxOnclick);
        holder.homeListLinearLayout.setOnClickListener(this::linearLayoutOnClick);
        holder.homeListEditIcon.setOnClickListener(this::editIconOnClick);
    }



    @Override
    public int getItemCount() {
        return projectList.size();
    }


    private void colorBoxOnclick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.pick_a_color);
        builder.setSingleChoiceItems(R.array.project_colors, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Project selectedProject = (Project) view.getTag();
                selectedProject.setColorInteger(which+1);
                projectDB.updateProject(selectedProject);
                notifyDataSetChanged();
            }
        });
        builder.create().show();
    }


    private void linearLayoutOnClick(View view) {
        Project selectedProject = (Project) view.getTag();
        int projectID = selectedProject.getId();
        Intent intent = new Intent(context, TreeActivity.class);
        intent.putExtra("projectID", projectID);
        context.startActivity(intent);
    }


    private void editIconOnClick(View view) {
        Project selectedProject = (Project) view.getTag();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_project, null);
        builder.setView(dialogView);

        // initialize dialog
        EditText editTitleEditText = dialogView.findViewById(R.id.homeEditDialogTitle);
        Button editDeleteButton = dialogView.findViewById(R.id.homeEditDialogDeleteButton);
        Button editSaveButton = dialogView.findViewById(R.id.homeEditDialogSaveButton);

        Dialog dialog = builder.create();

        // editDeleteButton
        editDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectDB.deleteProject(selectedProject.getId());
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
                dialog.dismiss();
            }
        });

        // editSaveButton
        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProject.setTitle(editTitleEditText.getText().toString());
                projectDB.updateProject(selectedProject);
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
