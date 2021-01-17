package com.example.tasktree.Models;

import java.util.Date;
import java.util.List;

public class Task {
    private int id;
    private String title;
    private List<Task> subTaskList;
    private boolean isCalender;
    private boolean isFinish;
    private Date finishDate;
    private boolean isMain;
    private float x;
    private float y;

    public Task() {
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

    public List<Task> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(List<Task> subTaskList) {
        this.subTaskList = subTaskList;
    }

    public boolean isCalender() {
        return isCalender;
    }

    public void setIsCalender(boolean calender) {
        isCalender = calender;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean finish) {
        isFinish = finish;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setIsMain(boolean main) {
        isMain = main;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
