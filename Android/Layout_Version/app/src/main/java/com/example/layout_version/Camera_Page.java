package com.example.layout_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Camera_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        ImageView back_home_im;
        TextView back_home_txt;

        back_home_im = (ImageView) findViewById(R.id.back_home_btn_camera);
        back_home_txt = (TextView) findViewById(R.id.back_home_txt_camera);

        back_home_im.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Camera_Page.this,MainActivity.class);
                startActivity(intent);
            }
        });

        back_home_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Camera_Page.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
