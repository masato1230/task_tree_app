package com.example.tasktree.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.LinearLayout;

import com.example.tasktree.Data.ProjectDatabaseHandler;
import com.example.tasktree.Models.Project;
import com.example.tasktree.R;
import com.example.tasktree.Recycler.ProjectRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Project project = new Project();
        project.setTitle("サンプル");
        project.setCreatedDate(new Date());
        project.setColorInteger(1);
        project.setFinish(false);
        ProjectDatabaseHandler projectDB = new ProjectDatabaseHandler(this);
        projectDB.add(project);


        recyclerView = findViewById(R.id.homeRecyclerView);

        ProjectRecyclerViewAdapter recyclerViewAdapter = new ProjectRecyclerViewAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyclerViewAdapter);

//        Intent intent = new Intent(Intent.ACTION_INSERT);
//        intent.setData(CalendarContract.Events.CONTENT_URI);
//        intent.putExtra(CalendarContract.Events.TITLE, "タイトル");
//        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "大岡山国立公園");
//        intent.putExtra(CalendarContract.Events.DESCRIPTION, "新しい建物気になるな〜");
//        intent.putExtra(CalendarContract.Events.ALL_DAY, true);
//        startActivityForResult(intent, 1);


    }
}