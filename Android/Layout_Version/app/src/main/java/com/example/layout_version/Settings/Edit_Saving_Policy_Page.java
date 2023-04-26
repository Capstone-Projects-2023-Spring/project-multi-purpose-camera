package com.example.layout_version.Settings;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.layout_version.R;
import com.example.layout_version.Settings.Attributes.*;
import com.example.layout_version.Settings.Attributes.Number;
import com.example.layout_version.View_Factory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Edit_Saving_Policy_Page extends AppCompatActivity {
    public static ArrayList<Saving_Policy> policy_list = null;
    public static Saving_Policy current_policy = null;
    public static BackEnd settings_copy = null;
    public static boolean edited = false;
    public static final int number_views_below_cameras = 3;
    LinearLayout main_layout;
    TextInputEditText text_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_policy);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.edit_policy_linear_layout);
        ConstraintLayout view = (ConstraintLayout) linearLayout.getChildAt(0);
        //linearLayout.removeView(view);
        main_layout = linearLayout;
        linearLayout.removeAllViews();
//        add_policies_using_template(linearLayout, current_policy);
//        spinner_camera = findViewById(R.id.spinner1);
//        spinner_resolution = findViewById(R.id.spinner);
//
//        set_entries_camera();
//        set_entries_resolution();
//        add_camera_add_button(linearLayout);
//        add_resolution_add_button(linearLayout);
//        set_resolution_label(current_policy.get_resolution().name, linearLayout);
//        setup_time_input();
        add_using_attributes();
    }

    public void setup_time_input(){
        TextInputEditText text_input = findViewById(R.id.time_input_text);

        text_input.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String text = s.toString();
                System.out.println("\ntrying time setting string: "+s + "\n");
                try{
                    int temp = Integer.parseInt(text);
                    Saving_Policy new_policy = new Saving_Policy(current_policy.get_cameras(), temp, current_policy.get_resolution(), 1);
                    int index = policy_list.indexOf(current_policy);
                    policy_list.set(index, new_policy);
                    current_policy = new_policy;
                    System.out.println("set done");
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
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
        //System.out.println(BackEnd.resolutions);
    }

    public void add_camera_add_button(LinearLayout linearLayout){
        ImageView add_camera_view = findViewById(R.id.add_camera_view);
        add_camera_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = (String) spinner_camera.getSelectedItem();
                Camera chosen_camera = BackEnd.main.name_to_camera(item);
                if(chosen_camera!= null)
                    add_camera_view(linearLayout, chosen_camera, current_policy);

                ArrayList<Camera> cameras = current_policy.get_cameras();
                cameras.add(chosen_camera);

                Saving_Policy new_policy = new Saving_Policy(cameras, current_policy.get_max_time(), current_policy.get_resolution(), 1);
                int index = policy_list.indexOf(current_policy);
                policy_list.set(index, new_policy);
                current_policy = new_policy;

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
                Resolution chosen_resolution = Resolution.name_to_resolution(item);
                if(chosen_resolution!= null)
                    set_resolution_label(item, linearLayout);
                Saving_Policy new_policy = new Saving_Policy(current_policy.get_cameras(), current_policy.get_max_time(), chosen_resolution, 1);

                int index = policy_list.indexOf(current_policy);
                policy_list.set(index, new_policy);
                current_policy = new_policy;

                set_entries_resolution();
            }
        });
    }

    public void set_resolution_label(String resolution, LinearLayout linearLayout){
        TextView view = findViewById(R.id.resolution_text_view);
        view.setText(resolution);
    }

    public void add_policies_using_template(LinearLayout linearLayout, Saving_Policy policy) {
        for(int i = 0; i < policy.get_cameras().size(); i++){
            add_camera_view(linearLayout, policy.get_cameras().get(i), policy);
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
                ArrayList<Camera> cameras = policy.get_cameras();
                cameras.remove(camera);
                Saving_Policy new_policy = new Saving_Policy(cameras, policy.get_max_time(), policy.get_resolution(), 1);

                int index = policy_list.indexOf(current_policy);
                policy_list.set(index, new_policy);
                current_policy = new_policy;

                set_entries_camera();
            }
        });
        linearLayout.addView(camera_layout, linearLayout.getChildCount()-number_views_below_cameras);
    }

    public void add_using_attributes(){
        LinearLayout linearLayout = main_layout;
        if(text_input != null){
            if(text_input.hasFocus()){
                text_input.clearFocus();
                handle_number_change();
                return;
            }
        }

        linearLayout.removeAllViews();
        Attribute[] attributes = current_policy.get_attributes();
        for(int i = 0; i < attributes.length; i++){
            Attribute attribute = attributes[i];
            if(attribute == null){
                System.out.println("attribute: null");
                continue;
            }
            System.out.println("attribute: " + attribute.type());
            if(attribute instanceof X_Attribute)
                construct_X_attribute(linearLayout, (X_Attribute) attribute);
            if(attribute instanceof Add_Button)
                construct_add_attribute(linearLayout, (Add_Button) attribute);
            if(attribute instanceof Number)
                construct_number_attribute(linearLayout, (Number) attribute);
        }
    }

    public void construct_X_attribute(LinearLayout linearLayout, X_Attribute attribute){
        ConstraintLayout camera_layout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.camera_of_policy_template, null);
        TextView camera_view = (TextView)((ConstraintLayout)camera_layout.getChildAt(0)).getChildAt(0);
        camera_view.setText(attribute.value());
        ImageView delete_view = (ImageView)((ConstraintLayout)camera_layout.getChildAt(0)).getChildAt(1);
        delete_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attribute.set(null);
                //linearLayout.removeAllViews();
                add_using_attributes();
            }
        });
        linearLayout.addView(camera_layout);//linearLayout.addView(camera_layout, linearLayout.getChildCount()-number_views_below_cameras);
    }

    public void construct_add_attribute(LinearLayout linearLayout, Add_Button attribute){
        ConstraintLayout camera_layout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.settings_add_button, null);
        ImageView add_camera_view = (ImageView)((ConstraintLayout)camera_layout.getChildAt(0)).getChildAt(0);//findViewById(R.id.add_camera_view);
        Spinner spinner = (Spinner)((ConstraintLayout) camera_layout.getChildAt(0)).getChildAt(1);
        View_Factory.set_entries(attribute.get_list(), spinner, Edit_Saving_Policy_Page.this);
        add_camera_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = (String) spinner.getSelectedItem();
                Camera chosen_camera = BackEnd.main.name_to_camera(item);
                attribute.set(chosen_camera);
                add_using_attributes();
            }
        });
        linearLayout.addView(camera_layout);
    }

    public void construct_number_attribute(LinearLayout linearLayout, Number attribute){
        ConstraintLayout camera_layout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.settings_number_template, null);
        text_input = (TextInputEditText) ((FrameLayout)((TextInputLayout)((ConstraintLayout)camera_layout.getChildAt(0)).getChildAt(1)).getChildAt(0)).getChildAt(0);//findViewById(R.id.time_input_text);
        text_input.setHint(attribute.value());
        text_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
//        text_input.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String text = s.toString();
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                String text = s.toString();
//                System.out.println("\ntrying time setting string: "+s + "\n");
//                try{
//                    int temp = Integer.parseInt(text);
//                    Saving_Policy new_policy = new Saving_Policy(current_policy.get_cameras(), temp, current_policy.get_resolution(), 1);
//                    int index = policy_list.indexOf(current_policy);
//                    policy_list.set(index, new_policy);
//                    current_policy = new_policy;
//                    System.out.println("set done");
//                } catch(Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        });
        linearLayout.addView(camera_layout);
    }

    public void handle_number_change(){
        String text = text_input.getText().toString();
        System.out.println("\ntrying time setting string: \n");
        try{
            int temp = Integer.parseInt(text);
            Saving_Policy new_policy = new Saving_Policy(current_policy.get_cameras(), temp, current_policy.get_resolution(), current_policy.id);
            int index = policy_list.indexOf(current_policy);
            policy_list.set(index, new_policy);
            current_policy = new_policy;
            System.out.println("reset policy using change focus done");
        } catch(Exception e){
            e.printStackTrace();
        }
        Attribute[] attributes = current_policy.get_attributes();
        add_using_attributes();
    }
}