package com.example.layout_version;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
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

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.saving_vertical_layout_title);

        ArrayList<String> policies = BackEnd.get_policy_string_saving();
        linearLayout.removeAllViews();

        add_policies_using_template(linearLayout, policies);
    }

    public void add_policies_using_template(LinearLayout linearLayout, ArrayList<String> policies){
        for(int i = 0; i < policies.size(); i++){
            int finalI = i;
            Saving_Policy policy = BackEnd.savings.get(finalI);
            ConstraintLayout policy_layout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.title_and_description_template, null);

            policy_layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Edit_Saving_Policy_Page.current_policy = policy;
                    Intent intent = new Intent (Saving_Policy_Page.this,Edit_Saving_Policy_Page.class);
                    startActivity(intent);
                }
            });

            LinearLayout inner_layout = (LinearLayout) ((ConstraintLayout) policy_layout.getChildAt(0)).getChildAt(0);

            String[] tokens = policies.get(i).split("\n");
            TextView title = (TextView) inner_layout.getChildAt(0);
            TextView time = (TextView) inner_layout.getChildAt(2);
            TextView second = (TextView) inner_layout.getChildAt(4);

            title.setText(tokens[0]);
            time.setText(tokens[1]);

            linearLayout.addView(policy_layout);
        }

    }

    public void add_policies(LinearLayout linearLayout, ArrayList<String> policies){
        int color = 0x8EEEE7;
        int left_offset = 100;
        int text_left_offset = 20;
        int height = 200;
        int width = 400;
        int padding = 50;






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

            TextView text = (TextView) LayoutInflater.from(this).inflate(R.layout.policy_text_view, null);
            text.setText(policies.get(i));
            text.setLayoutParams(View_Factory.createLayoutParams(padding, 0, -1, left_offset + text_left_offset, width - text_left_offset*2 - 50, height));
            text.setTextSize(20);
            //text.setTypeface(font);
            constraintLayout.addView(text);
            //System.out.println(font);



            //Typeface typeface = new Typeface
        }

    }

}
