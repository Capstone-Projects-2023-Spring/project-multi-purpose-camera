package com.example.layout_version;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Edit_Saving_Policy_Page extends AppCompatActivity {
    public static Saving_Policy current_policy = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_policy);
        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.saving_vertical_layout_title);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.edit_policy_linear_layout);
        //ArrayList<String> policies = BackEnd.get_policy_string_saving();
        //System.out.println(linearLayout.getChildAt(0));
        ConstraintLayout view = (ConstraintLayout)linearLayout.getChildAt(0);
        linearLayout.removeView(view);


        add_policies_using_template(linearLayout, current_policy);
    }

    public void add_policies_using_template(LinearLayout linearLayout, Saving_Policy policy) {
        //linearLayout.removeAllViews();
        for(int i = 0; i < policy.cameras.size(); i++){
            int finalI = i;
            ConstraintLayout camera_layout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.camera_of_policy_template, null);
            TextView camera_view = (TextView)((ConstraintLayout)camera_layout.getChildAt(0)).getChildAt(0);
            camera_view.setText(policy.cameras.get(i).name);
            ImageView delete_view = (ImageView)((ConstraintLayout)camera_layout.getChildAt(0)).getChildAt(1);
            delete_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < policy.cameras.size(); i++)
                        System.out.println("<"+policy.cameras.get(i).name+">");
                    linearLayout.removeView(camera_layout);
                    System.out.println("removing: <"  + camera_view.getText() + ">");
                    policy.cameras.remove(finalI);
                    for(int i = 0; i < policy.cameras.size(); i++)
                        System.out.println("<"+policy.cameras.get(i).name+">");
                }
            });
            linearLayout.addView(camera_layout, i);
        }

    }
}