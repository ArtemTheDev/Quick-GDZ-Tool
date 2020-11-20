package com.artemthedev.quickgdztool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class answer_Activity extends AppCompatActivity {

    String subject, form, task;
    String link = link_generator("1", "", "26");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return true;
    }


    public String link_generator (String subject, String form, String task) {
        // Получения ссылки из имеющихся данных
        String link = String.format("https://gdz.ru/class-8/algebra/merzlyak/%s/", task);
        return link;
    }
}