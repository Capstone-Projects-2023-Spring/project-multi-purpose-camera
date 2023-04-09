
package com.example.layout_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * The class Main_ top_ nav_ bar extends application compat activity. Creates a reusable navbar template for the app.
 */
public class Main_Top_Nav_Bar extends AppCompatActivity {

    /**
     *
     * On create creates navbar display
     *
     * @param savedInstanceState  the saved instance state.
     */
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
            /**
             *
             * On click sends user from the current page to settings
             *
             * @param view  the view.
             */
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

/**
 *
 * On click sends user from the current page to library
 *
 * @param view  the view.
 */
            public void onClick(View view) {

                Intent intent = new Intent (Main_Top_Nav_Bar.this,Library.class);
                startActivity(intent);
                System.out.println("--------Entering library");
            }
        });

    }

}
