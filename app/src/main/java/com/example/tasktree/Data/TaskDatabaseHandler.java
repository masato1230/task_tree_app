package com.example.tasktree.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tasktree.Constants.ProjectConstants;
import com.example.tasktree.Constants.TaskConstants;
import com.example.tasktree.Models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDatabaseHandler extends SQLiteOpenHelper {
    public TaskDatabaseHandler(@Nullable Context context) {
        super(context, TaskConstants.DB_NAME, null, TaskConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TaskConstants.TABLE_NAME + "("
                + TaskConstants.KEY_ID + " INTEGER PRIMARY KEY,"
                + TaskConstants.KEY_TITLE + " TEXT,"
                + TaskConstants.KEY_SUB_TASK_IDS + " TEXT,"
                + TaskConstants.KEY_IS_CALENDER + " INTEGER,"
                + TaskConstants.KEY_IS_FINISH + " INTEGER,"
                + TaskConstants.KEY_FINISH_DATE + " TEXT,"
                + TaskConstants.KEY_IS_MAIN + " INTEGER,"
                + TaskConstants.KEY_X + " REAL,"
                + TaskConstants.KEY_Y + " REAL"
                + ");";

        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskConstants.TABLE_NAME);
        onCreate(db);
    }


    // add
    public long addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskConstants.KEY_TITLE, task.getTitle());
        if (task.getSubTaskList() != null) {
            StringBuilder taskIDsStringBuilder = new StringBuilder();
            for (Task subTask: task.getSubTaskList()) {
                String taskID = String.valueOf(subTask.getId());
                taskIDsStringBuilder.append(taskID + ",");
            }
            taskIDsStringBuilder.setLength(taskIDsStringBuilder.length()-1); // 余分なコンマを削除
            values.put(TaskConstants.KEY_SUB_TASK_IDS, taskIDsStringBuilder.toString());
        }
        int isCalenderInt = 0;
        if (task.isCalender()) {
            isCalenderInt = 1;
        }
        values.put(TaskConstants.KEY_IS_CALENDER, isCalenderInt);
        int isFinishInt = 0;
        if (task.isFinish()) {
            isCalenderInt = 1;
        }
        values.put(TaskConstants.KEY_IS_FINISH, isFinishInt);
        // finishしている時のみ、finishDateを保存する
        if (task.isFinish()) {
            // 日付のフォーマット(getの時に同じものを)
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            String stringFinishDate = simpleDateFormat.format(task.getFinishDate());
            values.put(TaskConstants.KEY_FINISH_DATE, stringFinishDate);
        }
        int isMainInt = 0;
        if (task.isMain()) {
            isMainInt = 1;
        }
        values.put(TaskConstants.KEY_IS_MAIN, isMainInt);
        values.put(TaskConstants.KEY_X, task.getX());
        values.put(TaskConstants.KEY_Y, task.getY());

        return db.insert(TaskConstants.TABLE_NAME, null, values);
    }

    // get one
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Task task = new Task();
        Cursor cursor = db.query(TaskConstants.TABLE_NAME, new String[] {TaskConstants.KEY_ID, TaskConstants.KEY_TITLE, TaskConstants.KEY_SUB_TASK_IDS, TaskConstants.KEY_IS_CALENDER, TaskConstants.KEY_IS_FINISH, TaskConstants.KEY_FINISH_DATE, TaskConstants.KEY_IS_MAIN, TaskConstants.KEY_X, TaskConstants.KEY_Y},
                TaskConstants.KEY_ID + "=?",
                 new String[] {String.valueOf(id)},
                null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
        }
        // id
        task.setId(cursor.getInt(cursor.getColumnIndex(TaskConstants.KEY_ID)));
        // title
        task.setTitle(cursor.getString(cursor.getColumnIndex(TaskConstants.KEY_TITLE)));
        // subTask List
        List<Task> subTaskList = new ArrayList<>();
        String subTaskIDSString = cursor.getString(cursor.getColumnIndex(TaskConstants.KEY_SUB_TASK_IDS));
        if (subTaskIDSString != null) {
            String[] subTaskIDSplit = subTaskIDSString.split(",");
            for (String subTaskIDString: subTaskIDSplit) {
                int subTaskID = Integer.parseInt(subTaskIDString);
                Task subTask = getTask(subTaskID);
                subTaskList.add(subTask);
                task.setSubTaskList(subTaskList);
            }
        }
        // isCalendar
        int isCalendarInt = cursor.getInt(cursor.getColumnIndex(TaskConstants.KEY_IS_CALENDER));
        boolean isCalendar = false;
        if (isCalendarInt==1) {
            isCalendar = true;
        }
        task.setIsCalender(isCalendar);
        // isFinish
        int isFinishInt = cursor.getInt(cursor.getColumnIndex(TaskConstants.KEY_IS_FINISH));
        boolean isFinish = false;
        if (isFinishInt==1) {
            isFinish = true;
        }
        task.setIsFinish(isFinish);
        // finish date
        String finishDateString = cursor.getString(cursor.getColumnIndex(TaskConstants.KEY_FINISH_DATE));
        if (finishDateString != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            try {
                Date date = simpleDateFormat.parse(finishDateString);
                task.setFinishDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int isMainInt = cursor.getInt(cursor.getColumnIndex(TaskConstants.KEY_IS_MAIN));
        boolean isMain = false;
        if (isMainInt==1) {
            isMain = true;
        }
        task.setIsMain(isMain);
        task.setX(cursor.getFloat(cursor.getColumnIndex(TaskConstants.KEY_X)));
        task.setY(cursor.getFloat(cursor.getColumnIndex(TaskConstants.KEY_Y)));

        return task;
    }



    // get all
    public List<Task> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> taskList = new ArrayList<>();

        Cursor cursor = db.query(TaskConstants.TABLE_NAME, new String[] {TaskConstants.KEY_ID, TaskConstants.KEY_TITLE, TaskConstants.KEY_SUB_TASK_IDS, TaskConstants.KEY_IS_CALENDER, TaskConstants.KEY_IS_FINISH, TaskConstants.KEY_FINISH_DATE, TaskConstants.KEY_IS_MAIN, TaskConstants.KEY_X, TaskConstants.KEY_Y},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(TaskConstants.KEY_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(TaskConstants.KEY_TITLE)));
                List<Task> subTaskList = new ArrayList<>();
                String subTaskIDSString = cursor.getString(cursor.getColumnIndex(TaskConstants.KEY_SUB_TASK_IDS));
                if (!subTaskIDSString.isEmpty()) {
                    String[] subTaskIDSplit = subTaskIDSString.split(",");
                    for (String subTaskIDString: subTaskIDSplit) {
                        int subTaskID = Integer.parseInt(subTaskIDString);
                        Task subTask = getTask(subTaskID);
                        subTaskList.add(subTask);
                        task.setSubTaskList(subTaskList);
                    }
                }
                task.setSubTaskList(subTaskList);
                int isCalendarInt = cursor.getInt(cursor.getColumnIndex(TaskConstants.KEY_IS_CALENDER));
                boolean isCalendar = false;
                if (isCalendarInt==1) {
                    isCalendar = true;
                }
                task.setIsCalender(isCalendar);
                int isFinishInt = cursor.getInt(cursor.getColumnIndex(TaskConstants.KEY_IS_FINISH));
                boolean isFinish = false;
                if (isFinishInt==1) {
                    isFinish = true;
                }
                task.setIsFinish(isFinish);
                int isMainInt = cursor.getInt(cursor.getColumnIndex(TaskConstants.KEY_IS_MAIN));
                boolean isMain = false;
                if (isMainInt==1) {
                    isMain = true;
                }
                String finishDateString = cursor.getString(cursor.getColumnIndex(TaskConstants.KEY_FINISH_DATE));
                if (finishDateString != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    try {
                        Date date = simpleDateFormat.parse(finishDateString);
                        task.setFinishDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                task.setIsMain(isMain);
                task.setX(cursor.getFloat(cursor.getColumnIndex(TaskConstants.KEY_X)));
                task.setY(cursor.getFloat(cursor.getColumnIndex(TaskConstants.KEY_Y)));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }


    // update
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskConstants.KEY_TITLE, task.getTitle());
        if (task.getSubTaskList() != null) {
            StringBuilder taskIDsStringBuilder = new StringBuilder();
            for (Task subTask: task.getSubTaskList()) {
                String taskID = String.valueOf(subTask.getId());
                taskIDsStringBuilder.append(taskID + ",");
            }
            taskIDsStringBuilder.setLength(taskIDsStringBuilder.length()-1); // 余分なコンマを削除
            values.put(TaskConstants.KEY_SUB_TASK_IDS, taskIDsStringBuilder.toString());
        }
        int isCalenderInt = 0;
        if (task.isCalender()) {
            isCalenderInt = 1;
        }
        values.put(TaskConstants.KEY_IS_CALENDER, isCalenderInt);
        int isFinishInt = 0;
        if (task.isFinish()) {
            isCalenderInt = 1;
        }
        values.put(TaskConstants.KEY_IS_FINISH, isFinishInt);
        // finishしている時のみ、finishDateを保存する
        if (task.isFinish()) {
            // 日付のフォーマット(getの時に同じものを)
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            String stringFinishDate = simpleDateFormat.format(task.getFinishDate());
            values.put(TaskConstants.KEY_FINISH_DATE, stringFinishDate);
        }
        int isMainInt = 0;
        if (task.isMain()) {
            isMainInt = 1;
        }
        values.put(TaskConstants.KEY_IS_MAIN, isMainInt);
        values.put(TaskConstants.KEY_X, task.getX());
        values.put(TaskConstants.KEY_Y, task.getY());
        return db.update(TaskConstants.TABLE_NAME, values, TaskConstants.KEY_ID + "=?", new String[] {String.valueOf(task.getId())});
    }



    // delete
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskConstants.TABLE_NAME, TaskConstants.KEY_ID + "=?", new String[] {String.valueOf(id)});
    }


    // get count
    public int getTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + TaskConstants.TABLE_NAME;

        Cursor cursor = db.rawQuery(countQuery, null, null);
        return cursor.getCount();
    }
}
