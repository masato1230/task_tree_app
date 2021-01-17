package com.example.tasktree.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;

import com.example.tasktree.R;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "タイトル");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "大岡山国立公園");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "新しい建物気になるな〜");
        intent.putExtra(CalendarContract.Events.ALL_DAY, true);
        startActivity(intent);

    }
}