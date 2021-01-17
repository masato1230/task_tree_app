package com.example.tasktree.Models;

import android.graphics.Color;

import com.example.tasktree.Data.TaskDatabaseHandler;

import java.util.Date;
import java.util.List;

public class Project {
    private int id;
    private String title;
    private List<Task> tasks;
    private boolean isFinish;
    private int colorInteger; // 1: red, 2: blue, 3: green, 4: yellow, 5: gray
    private Date createdDate;

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public int getColorInteger() {
        return colorInteger;
    }

    public void setColorInteger(int colorInteger) {
        this.colorInteger = colorInteger;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
