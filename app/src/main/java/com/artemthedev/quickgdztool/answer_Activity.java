package com.artemthedev.quickgdztool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;

public class answer_Activity extends AppCompatActivity {

    // Инициализация строк
    String subject, form, task, paragraph, link;
    ImageView urlImage;

    int elementID = 11;

    private Document doc;
    private Thread secThread;
    private Runnable runnable;

    TextView link_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        init();
    }


    // Метод инициализации
    private void init() {
        // Добавление кнопки "назад"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        // Получение интента и данных из него
        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        form = intent.getStringExtra("form");
        task = intent.getStringExtra("task");
        paragraph = intent.getStringExtra("paragraph");

        link = link_generator(subject, form, task, paragraph);

        // Инициализация потоков для парсера
        runnable = new Runnable() {
            @Override
            public void run() {
                parse(link);
            }
        };
        secThread = new Thread(runnable);
        secThread.start();

        // Ссылка на оригинал
        link_view = findViewById(R.id.link);
        link_view.setText(link);
    }


    // Метод, заставляющий кнопку "назад" работать ._.
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return true;
    }


    // Метод, генерирующий ссылку для парсинга
    public String link_generator (String subject, String form, String task, String paragraph) {
        String part = "";
        boolean no_book = false;
        // Подбираем фрагменты для ссылки
        switch (subject) {
            case "Алгебра":
                switch (form) {
                    case "7":
                        part = "class-7/algebra/merzlyak-polonskij";
                        task = task + "-nom";
                        break;
                    case "8":
                        part = "class-8/algebra/merzlyak";
                        break;
                    case "9":
                        part = "class-9/algebra/merzlyak";
                        task = task + "-nom";
                        break;
                    case "10":
                        part = "class-10/algebra/merzlyak";
                        task = String.format("%s-%s", paragraph, task);
                        break;
                }
                break;

            case "Геометрия":
                switch (form) {
                    case "7":
                        part = "class-7/geometria/merzljak";
                        task = task + "-nom";
                        break;
                    case "8":
                        part = "class-8/geometria/merzlyak";
                        task = task + "-nom";
                        break;
                    case "9":
                        part = "class-9/geometria/merzlyak-polonskij";
                        task = task + "-nom";
                        break;
                    case "10":
                        no_book = true;
                        break;
                }
                break;

            case "Рус. яз.":
                switch (form) {
                    case "7":
                        part = "class-7/russkii_yazik/rybchenkova";
                        task = task + "-nom";
                        break;
                    case "8":
                        part = "class-8/russkii_yazik/rybchenkova";
                        task = task + "-nom";
                        break;
                    case "9":
                        part = "class-9/russkii_yazik/ribchenkova-9";
                        if (Integer.parseInt(task) <= 212) task = "1-" + task;
                        break;
                    case "10":
                        no_book = true;
                        break;
                }
                break;
        }
        // Получения ссылки из имеющихся данных
        String link = "";
        if (!no_book) {
            link = String.format("https://gdz.ru/%s/%s/", part, task);
            Log.d(settings.DEBUG_TAG, "SRC LINK: " + link);
        }
        return link;
    }


    public void parse(String link) {
        try {
            doc = Jsoup.connect(link).get();
            Elements element = doc.getElementsByTag("img");
            for (int i = 0; i < element.size(); i++) {
                if (checkifGDZ(element.get(i).attr("alt"))) {
                    Log.d(settings.DEBUG_TAG, element.get(i).attr("alt"));
                    DisplayIMGFromURL( element.get(i).absUrl("src"),
                                       element.get(i).attr("alt"));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void DisplayIMGFromURL(String src, String name) {
        try {
            InputStream stream = (InputStream) new URL(src).getContent();
            final Drawable d = Drawable.createFromStream(stream, "src name");

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth();

            item fragment = new item(d, width, name);

            fragmentTransaction.add(R.id.a, fragment);
            fragmentTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean checkifGDZ(String alt) {
        char c;
        int progress = 0;
        for (int i = 0; i < alt.length(); i++) {
            c = alt.charAt(i);
            if (progress == 0 && c == 'Г') progress = 1;
            else if (progress == 1 && c == 'Д') progress = 2;
            else if (progress == 2 && c == 'З') progress = 3;
            else progress = 0;

            if (progress == 3) return true;
        }
        return false;
    }


}