package com.example.layout_version;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class Edit_Saving_Policy_Page extends AppCompatActivity {
    public static Saving_Policy current_policy = null;
    public static final int number_views_below_cameras = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_policy);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.edit_policy_linear_layout);
        ConstraintLayout view = (ConstraintLayout) linearLayout.getChildAt(0);
        linearLayout.removeView(view);
        add_policies_using_template(linearLayout, current_policy);
        spinner_camera = findViewById(R.id.spinner1);
        spinner_resolution = findViewById(R.id.spinner);
        set_entries_camera();
        set_entries_resolution();
        add_camera_add_button(linearLayout);
        add_resolution_add_button(linearLayout);
        set_resolution_label(current_policy.resolution.name, linearLayout);
    }
    Spinner spinner_camera;
    Spinner spinner_resolution;
    public void set_entries_camera(){
        ArrayList<Camera> camera_entries = current_policy.get_available_cameras_to_add();
        ArrayList<String> camera_entry_names = Camera.names(camera_entries);
        View_Factory.set_entries(camera_entry_names, spinner_camera, Edit_Saving_Policy_Page.this);
    }

    public void set_entries_resolution(){
        ArrayList<Resolution> camera_entries = current_policy.get_available_resoltions_to_add();
        ArrayList<String> camera_entry_names = Resolution.names(camera_entries);
        View_Factory.set_entries(camera_entry_names, spinner_resolution, Edit_Saving_Policy_Page.this);
    }

    public void add_camera_add_button(LinearLayout linearLayout){
        ImageView add_camera_view = findViewById(R.id.add_camera_view);
        add_camera_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = (String) spinner_camera.getSelectedItem();
                Camera chosen_camera = BackEnd.name_to_camera(item);
                if(chosen_camera!= null)
                    add_camera_view(linearLayout, chosen_camera, current_policy);
                current_policy.add_camera(chosen_camera);
                set_entries_camera();
            }
        });
    }

    public void add_resolution_add_button(LinearLayout linearLayout){
        ImageView add_resolution_view = findViewById(R.id.add_resolution_view);
        add_resolution_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = (String) spinner_resolution.getSelectedItem();
                Resolution chosen_resolution = BackEnd.name_to_resolution(item);
                if(chosen_resolution!= null)
                    set_resolution_label(item, linearLayout);
                current_policy.resolution = chosen_resolution;
                set_entries_resolution();
            }
        });
    }

    public void set_resolution_label(String resolution, LinearLayout linearLayout){
        TextView view = findViewById(R.id.resolution_text_view);
        view.setText(resolution);
    }

    public void add_policies_using_template(LinearLayout linearLayout, Saving_Policy policy) {
        for(int i = 0; i < policy.cameras.size(); i++){
            add_camera_view(linearLayout, policy.cameras.get(i), policy);
        }
    }

    public void add_camera_view(LinearLayout linearLayout, Camera camera, Saving_Policy policy){
        ConstraintLayout camera_layout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.camera_of_policy_template, null);
        TextView camera_view = (TextView)((ConstraintLayout)camera_layout.getChildAt(0)).getChildAt(0);
        camera_view.setText(camera.name);
        ImageView delete_view = (ImageView)((ConstraintLayout)camera_layout.getChildAt(0)).getChildAt(1);
        delete_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeView(camera_layout);
                policy.cameras.remove(camera);

                set_entries_camera();
            }
        });
        linearLayout.addView(camera_layout, linearLayout.getChildCount()-number_views_below_cameras);
    }
}