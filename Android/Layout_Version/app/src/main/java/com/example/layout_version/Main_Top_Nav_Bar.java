
package com.example.layout_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.layout_version.Library.Library;

public class Main_Top_Nav_Bar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_top_nav_bar);

        ImageView btn;
        Button lib;

        btn = (ImageView) findViewById(R.id.settings);
        lib = (Button) findViewById(R.id.library);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Main_Top_Nav_Bar.this,Settings.class);
                startActivity(intent);
                System.out.println("--------Entering settings");
            }
        });

        lib.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Main_Top_Nav_Bar.this, Library.class);
                startActivity(intent);
                System.out.println("--------Entering library");
            }
        });

    }

}
