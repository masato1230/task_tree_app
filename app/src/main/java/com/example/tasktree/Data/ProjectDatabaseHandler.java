package com.example.tasktree.Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tasktree.Constants.ProjectConstants;
import com.example.tasktree.Models.Project;
import com.example.tasktree.Models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectDatabaseHandler extends SQLiteOpenHelper {

    public ProjectDatabaseHandler(@Nullable Context context) {
        super(context, ProjectConstants.DB_NAME, null, ProjectConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROJECT_TABLE = "CREATE TABLE " + ProjectConstants.TABLE_NAME + "("
                + ProjectConstants.KEY_ID + " INTEGER PRIMARY KEY,"
                + ProjectConstants.KEY_TITLE + " TEXT,"
                + ProjectConstants.KEY_TASK_IDS + " TEXT," // true:1, false:0
                + ProjectConstants.KEY_IS_FINISH + " INTEGER,"
                + ProjectConstants.KEY_COLOR_INTEGER + " INTEGER,"
                + ProjectConstants.KEY_CREATED_AT + " TEXT"
                + ");";
        db.execSQL(CREATE_PROJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProjectConstants.TABLE_NAME);
        onCreate(db);
    }

    // add
    public void add(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProjectConstants.KEY_ID, project.getId());
        values.put(ProjectConstants.KEY_TITLE, project.getTitle());
        StringBuilder taskIDsStringBuilder = new StringBuilder();
        for (Task task: project.getTasks()) {
            String taskID = String.valueOf(task.getId());
            taskIDsStringBuilder.append(taskID + ",");
        }
        taskIDsStringBuilder.setLength(taskIDsStringBuilder.length()-1); // 余分なコンマを削除
        values.put(ProjectConstants.KEY_TASK_IDS, taskIDsStringBuilder.toString());
        int isFinishInt = 0;
        if (project.isFinish()) {
            isFinishInt = 1;
        }
        values.put(ProjectConstants.KEY_IS_FINISH, isFinishInt);
        values.put(ProjectConstants.KEY_COLOR_INTEGER, project.getColorInteger());
        // 日付のフォーマット(getの時に同じものを)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String stringCreatedAt = simpleDateFormat.format(project.getCreatedDate());
        values.put(ProjectConstants.KEY_CREATED_AT, stringCreatedAt);

        db.insert(ProjectConstants.TABLE_NAME, null, values);
    }

    // get one
    public Project getProject(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Project project = new Project();

        Cursor cursor = db.query(ProjectConstants.TABLE_NAME,
                new String[] {ProjectConstants.KEY_ID, ProjectConstants.KEY_TITLE, ProjectConstants.KEY_TASK_IDS, ProjectConstants.KEY_IS_FINISH, ProjectConstants.KEY_COLOR_INTEGER, ProjectConstants.KEY_CREATED_AT},
                ProjectConstants.KEY_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        project.setId(cursor.getInt(cursor.getColumnIndex(ProjectConstants.KEY_ID)));
        project.setTitle(cursor.getString(cursor.getColumnIndex(ProjectConstants.KEY_TITLE)));
        String taskIDsString = cursor.getString(cursor.getColumnIndex(ProjectConstants.KEY_TASK_IDS));
        String[] taskIDsSplit = taskIDsString.split(",");
        for (String taskIDString: taskIDsSplit) {
            int taskID = Integer.parseInt(taskIDsString);
            // todo taskDB(affter)
        }
        // todo project.setTasks();
        int isFinishInt = cursor.getInt(cursor.getColumnIndex(ProjectConstants.KEY_IS_FINISH));
        boolean isFinish = false;
        if (isFinishInt == 1) {
            isFinish = true;
        }
        project.setFinish(isFinish);
        project.setColorInteger(cursor.getInt(cursor.getColumnIndex(ProjectConstants.KEY_COLOR_INTEGER)));
        String createdDateString = cursor.getString(cursor.getColumnIndex(ProjectConstants.KEY_CREATED_AT));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        try {
            Date createdAt = simpleDateFormat.parse(createdDateString);
            project.setCreatedDate(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return project;
    }



    // get all
    public List<Project> getAllProjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Project> projects = new ArrayList<>();
        Cursor cursor = db.query(ProjectConstants.TABLE_NAME,
                new String[] {ProjectConstants.KEY_ID, ProjectConstants.KEY_TITLE, ProjectConstants.KEY_TASK_IDS, ProjectConstants.KEY_IS_FINISH, ProjectConstants.KEY_COLOR_INTEGER, ProjectConstants.KEY_CREATED_AT},
                 null, null,null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Project project = new Project();
                project.setId(cursor.getInt(cursor.getColumnIndex(ProjectConstants.KEY_ID)));
                project.setTitle(cursor.getString(cursor.getColumnIndex(ProjectConstants.KEY_TITLE)));
                String taskIDsString = cursor.getString(cursor.getColumnIndex(ProjectConstants.KEY_TASK_IDS));
                String[] taskIDsSplit = taskIDsString.split(",");
                for (String taskIDString: taskIDsSplit) {
                    int taskID = Integer.parseInt(taskIDsString);
                    // todo taskDB(affter)
                }
                // todo project.setTasks();
                int isFinishInt = cursor.getInt(cursor.getColumnIndex(ProjectConstants.KEY_IS_FINISH));
                boolean isFinish = false;
                if (isFinishInt == 1) {
                    isFinish = true;
                }
                project.setFinish(isFinish);
                project.setColorInteger(cursor.getInt(cursor.getColumnIndex(ProjectConstants.KEY_COLOR_INTEGER)));
                String createdDateString = cursor.getString(cursor.getColumnIndex(ProjectConstants.KEY_CREATED_AT));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                try {
                    Date createdAt = simpleDateFormat.parse(createdDateString);
                    project.setCreatedDate(createdAt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                projects.add(project);
            } while (cursor.moveToNext());
        }
        return projects;
    }



    // update
    public int updateProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProjectConstants.KEY_TITLE, project.getTitle());
        StringBuilder taskIDsStringBuilder = new StringBuilder();
        for (Task task: project.getTasks()) {
            String taskID = String.valueOf(task.getId());
            taskIDsStringBuilder.append(taskID + ",");
        }
        taskIDsStringBuilder.setLength(taskIDsStringBuilder.length()-1); // 余分なコンマを削除
        values.put(ProjectConstants.KEY_TASK_IDS, taskIDsStringBuilder.toString());
        int isFinishInt = 0;
        if (project.isFinish()) {
            isFinishInt = 1;
        }
        values.put(ProjectConstants.KEY_IS_FINISH, isFinishInt);
        values.put(ProjectConstants.KEY_COLOR_INTEGER, project.getColorInteger());
        // 日付のフォーマット(getの時に同じものを)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String stringCreatedAt = simpleDateFormat.format(project.getCreatedDate());
        values.put(ProjectConstants.KEY_CREATED_AT, stringCreatedAt);

        return db.update(ProjectConstants.TABLE_NAME, values, ProjectConstants.KEY_ID, new String[] {String.valueOf(project.getId())});
    }



    // delete
    public void deleteProject(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ProjectConstants.TABLE_NAME, ProjectConstants.KEY_ID + "=?", new String[] {String.valueOf(id)});
    }


    // get count
    public int getProjectCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + ProjectConstants.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null, null);
        return cursor.getCount();
    }
}
