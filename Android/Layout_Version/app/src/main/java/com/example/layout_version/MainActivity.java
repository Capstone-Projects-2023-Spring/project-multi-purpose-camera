package com.example.layout_version;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    public static int background_color1;
    public static int background_color2;

    public ConstraintLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BackEnd.init_test_objects();

        ImageView btn;
        Button lib;

        ConstraintLayout constraintLayout = findViewById(R.id.main_layout);
        System.out.println("*******************\n\n\n" + constraintLayout);

        background_color1 = Color.DKGRAY;
        background_color2 = Color.WHITE;

        main_layout = new ConstraintLayout(this);
        main_layout.setLayoutParams(View_Factory.createLayoutParams(0, 0, 0, 0, -1, -1 ));
        constraintLayout.addView(main_layout);

        //ConstraintLayout camera_layout = construct_camera_layout(main_layout);

        btn = (ImageView) findViewById(R.id.settings);
        lib = (Button) findViewById(R.id.library);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        lib.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,Library.class);
                startActivity(intent);
            }
        });

    }





}