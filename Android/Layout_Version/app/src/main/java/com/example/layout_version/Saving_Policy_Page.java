package com.example.layout_version;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontStyle;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class Saving_Policy_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saving_policies);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.saving_vertical_layout);

        ArrayList<String> policies = BackEnd.get_policy_string_saving();

        add_policies(linearLayout, policies);
    }

    public void add_policies(LinearLayout linearLayout, ArrayList<String> policies){
        int color = 0x8EEEE7;
        int left_offset = 100;
        int text_left_offset = 20;
        int height = 200;
        int width = 400;
        int padding = 50;

        TextView title = findViewById(R.id.saving_title);
        Typeface font = title.getTypeface();


        System.out.println("# saving policies: " + policies.size());
        for(int i = 0; i < policies.size(); i++){
            ConstraintLayout constraintLayout = new ConstraintLayout(this);
            constraintLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, height + padding));
            linearLayout.addView(constraintLayout);

            View round_rect = View_Factory.round_rectangle(this, color);
            round_rect.setLayoutParams(View_Factory.createLayoutParams(padding, 0, -1, left_offset, width, height));
            constraintLayout.addView(round_rect);
            round_rect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            TextView text = new TextView(this);
            text.setText(policies.get(i));
            text.setLayoutParams(View_Factory.createLayoutParams(padding, 0, -1, left_offset + text_left_offset, width - text_left_offset*2 - 50, height));
            text.setTextSize(20);
            text.setTypeface(font);
            constraintLayout.addView(text);
            System.out.println(font);



            //Typeface typeface = new Typeface
        }

    }

}
