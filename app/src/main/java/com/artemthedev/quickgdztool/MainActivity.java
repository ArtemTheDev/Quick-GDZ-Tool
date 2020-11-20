package com.artemthedev.quickgdztool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    // Инициализация объектов
    // Строковые объекты
    String subject, form, task;
    // Инициализация спиннеров
    Spinner subject_spinner, form_spinner;
    // Инициализация текстовых инпут - полей
    EditText TaskEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // Инициализация спиннеров
        subject_spinner = findViewById(R.id.SubjectSpinner);
        form_spinner = findViewById(R.id.FormSpinner);
        // Инициализация текстовых инпут - полей
        TaskEdit = findViewById(R.id.TaskEdit);
    }

    public void saveAsDefault(View view) {
        // Сохранение дефолтных настроек
    }

    public void search(View view) {
        // Получение данных
        subject = subject_spinner.getSelectedItem().toString();
        form = form_spinner.getSelectedItem().toString();
        task = TaskEdit.getText().toString();

        // Создание интента
        Intent intent = new Intent(this, answer_Activity.class);
        // Кидаем доп.данные
        intent.putExtra("subject", subject);
        intent.putExtra("form", form);
        intent.putExtra("task", task);
        // Запускаем активность
        startActivity(intent);
    }
}