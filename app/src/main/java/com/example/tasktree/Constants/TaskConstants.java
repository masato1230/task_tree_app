package com.example.tasktree.Constants;

import com.example.tasktree.Models.Task;

import java.util.Date;
import java.util.List;

public class TaskConstants {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "taskDB";
    public static final String TABLE_NAME = "taskTable";
    // table columns
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SUB_TASK_IDS = "subTaskIDS";
    public static final String KEY_IS_CALENDER = "isCalender";
    public static final String KEY_IS_FINISH = "isFinish";
    public static final String KEY_FINISH_DATE = "finishDate";
    public static final String KEY_IS_MAIN = "isMain";
    public static final String KEY_X = "x";
    public static final String KEY_Y = "y";
}
