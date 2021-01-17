package com.example.tasktree.Recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasktree.R;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    public TextView homeListColorBox;
    public TextView homeListTitle;
    public TextView homeListProgress;
    public TextView homeListDate;
    public LinearLayout homeListLinearLayout;
    public ImageView homeListEditIcon;

    public ProjectViewHolder(@NonNull View itemView) {
        super(itemView);
        homeListColorBox = itemView.findViewById(R.id.homeListColorBox);
        homeListTitle = itemView.findViewById(R.id.homeListTitle);
        homeListProgress = itemView.findViewById(R.id.homeListDate);
        homeListDate = itemView.findViewById(R.id.homeListDate);
        homeListLinearLayout = itemView.findViewById(R.id.homeListLinearLayout);
        homeListEditIcon = itemView.findViewById(R.id.homeListEditIcon);
    }
}
