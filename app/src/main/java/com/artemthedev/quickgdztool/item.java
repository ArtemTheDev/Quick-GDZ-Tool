package com.artemthedev.quickgdztool;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class item extends Fragment {

    ImageView img;
    TextView name;
    Drawable bg;
    int width;
    String name_str;

    public item(Drawable d, int width, String name) {
        this.bg = d;
        this.width = width;
        this.name_str = name;
    }


    public item() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item, null);
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            name = view.findViewById(R.id.text);
            name.setText(name_str);

            img = view.findViewById(R.id.img);
            img.setImageDrawable(this.bg);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.width, 1000);
            img.setLayoutParams(layoutParams);
        }
    }

}