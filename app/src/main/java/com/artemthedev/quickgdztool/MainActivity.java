package com.artemthedev.quickgdztool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    // Инициализация объектов
    // Строковые объекты
    String subject, form, task, paragraph;
    // Инициализация спиннеров
    Spinner subject_spinner, form_spinner;
    // Инициализация текстовых инпут - полей
    EditText TaskEdit;


    // Удаляемые поля
    EditText parag_edit;
    TextView parag;


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

        // Удаляемые поля
        parag = findViewById(R.id.parag_label);
        parag_edit = findViewById(R.id.parag_edit);

        Hide_Parag();


        // Инициализация слушателей спиннеров
        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int in, long id) {
                subject = subject_spinner.getSelectedItem().toString();
                form = form_spinner.getSelectedItem().toString();
                change_parag_state(subject, form);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        form_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int in, long id) {
                subject = subject_spinner.getSelectedItem().toString();
                form = form_spinner.getSelectedItem().toString();
                change_parag_state(subject, form);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    public void saveAsDefault(View view) {
        // Сохранение дефолтных настроек
    }


    public void search(View view) {
        // Получение данных
        subject = subject_spinner.getSelectedItem().toString();
        form = form_spinner.getSelectedItem().toString();
        task = TaskEdit.getText().toString();
        paragraph = parag_edit.getText().toString();

        // Создание интента
        Intent intent = new Intent(this, answer_Activity.class);
        // Кидаем доп.данные
        intent.putExtra("subject", subject);
        intent.putExtra("form", form);
        intent.putExtra("task", task);
        intent.putExtra("paragraph", paragraph);
        // Запускаем активность
        startActivity(intent);
    }


    public void Hide_Parag() {
        parag.setAlpha((float) 0.7);
        parag_edit.setEnabled(false);
    }


    public void Show_Parag() {
        parag.setAlpha((float) 1);
        parag_edit.setEnabled(true);
    }


    public void change_parag_state(String subject, String form) {
        if (subject.equals("Алгебра") && form.equals("10")) {
            Show_Parag();
            Log.d(settings.DEBUG_TAG, subject + " " + form);
        } else {
            Hide_Parag();
        }
    }
}