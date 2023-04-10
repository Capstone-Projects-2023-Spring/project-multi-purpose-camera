package com.example.layout_version;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.security.Policy;
import java.util.ArrayList;


/**
 * The class Saving_ policy_ page extends application compat activity. This displays the page for the saving policy
 */
public class Saving_Policy_Page extends AppCompatActivity {
    ArrayList<Saving_Policy> current_saving_policies;
    boolean data_saved = false;


    /**
     *
     * Checks if policies are valid
     *
     * @return ArrayList
     */
    public ArrayList<Saving_Policy> is_policies_valid(){

        System.out.println("policies: ");
        for(int i = 0; i < current_saving_policies.size(); i++){
            System.out.println(current_saving_policies.get(i));
        }
        //unique policy: resolution, camera
        for(int saving_policy = 0; saving_policy < current_saving_policies.size(); saving_policy++){
            Saving_Policy temp = current_saving_policies.get(saving_policy);
            Resolution resolution = temp.get_resolution();
            ArrayList<Camera> cameras = temp.get_cameras();
            for(int camera_index = 0; camera_index < cameras.size(); camera_index++){
                Camera camera = cameras.get(camera_index);
                Saving_Policy duplicate = has_camera_resolution(camera, resolution, saving_policy + 1);
                if(duplicate != null){
                    ArrayList<Saving_Policy> list = new ArrayList<>();
                    list.add(temp);
                    list.add(duplicate);
                    return list;
                }
            }
        }
        return null;
    }


    /**
     *
     * Has camera resolution
     *
     * @param camera  the camera.
     * @param resolution  the resolution.
     * @param start  the start.
     * @return Saving_Policy
     */
    public Saving_Policy has_camera_resolution(Camera camera, Resolution resolution, int start){

        System.out.println("is there: " + camera + ", " + resolution + ", after " + start);
        for(int i = start; i < current_saving_policies.size(); i++){
            Saving_Policy policy = current_saving_policies.get(i);
            if(policy.get_resolution().equals(resolution)){
                if(policy.get_cameras().contains(camera)){
                    System.out.println("yes");
                    return policy;
                }
            }
        }
        System.out.println("no");
        return null;
    }

    @Override

/**
 *
 * On create renders the page for saving policies
 *
 * @param savedInstanceState  the saved instance state.
 */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println("start oncreate");
        setContentView(R.layout.saving_policies);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.saving_vertical_layout_title);
        LinearLayout linearLayout_policies = (LinearLayout)findViewById(R.id.saving_policies_container);
        linearLayout.removeViewAt(1);
        setup_view();
        System.out.println("done oncreate");

    }
    void setup_view(){
        if(current_saving_policies == null){
            current_saving_policies = BackEnd.main.get_savings();
        }
        System.out.println("setting up saving_policy page: ");
        System.out.println("\n\npolicies: " + current_saving_policies.size());
        for(int i = 0; i < current_saving_policies.size(); i++){
            System.out.println(current_saving_policies.get(i).get_display_text());
        }
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.saving_vertical_layout_title);
        LinearLayout linearLayout_policies = (LinearLayout)findViewById(R.id.saving_policies_container);
        ArrayList<String> policies;

        policies = Saving_Policy.list_to_string(current_saving_policies);
        linearLayout_policies.removeAllViews();
        add_policies_using_template(linearLayout_policies, policies);
    }


    /**
     *
     * On start
     *
     */
    protected void onStart(){

        super.onStart();
        setup_view();
    }

    void save_data(){
        ArrayList<Saving_Policy> to_add = new ArrayList<>();
        ArrayList<Saving_Policy> to_delete = new ArrayList<>();

        BackEnd.main.get_different_saving(current_saving_policies, to_delete, to_add);
        data_saved = false;
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            // Process exception
        }
        BackEnd old = BackEnd.main;
        BackEnd new_data = new BackEnd(old.get_cameras(), current_saving_policies, old.get_notifications());
        BackEnd.main = new_data;
        data_saved = false;
        System.out.println("\n\nto add: " + to_add.size());
        for(int i = 0; i < to_add.size(); i++){
            System.out.println(to_add.get(i).get_display_text());
        }
        System.out.println("\n\nto delete: " + to_delete.size());
        for(int i = 0; i < to_delete.size(); i++){
            System.out.println(to_delete.get(i).get_display_text());
        }
    }

    @Override

/**
 *
 * On back pressed
 *
 */
    public void onBackPressed() {

        ArrayList<Saving_Policy> duplicate = is_policies_valid();
        if(duplicate != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cannot save duplicate");
            String message = "";
            message += duplicate.get(0) + "\n\n" + duplicate.get(1);


            builder.setMessage(message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override

/**
 *
 * On click that saves data and gives the users the choice to save and exit.
 *
 * @param dialog  the dialog.
 * @param which  the which.
 */
                public void onClick(DialogInterface dialog, int which) {

                    // Save the data

                    // User chose to save and exit, call the superclass onBackPressed() method to exit the activity

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return;
        }
        else{
            System.out.println("no duplicate found");
        }

        showExitConfirmationDialog();
    }

    private ProgressDialog progressDialog;


    /**
     *
     * Show saving progress dialog
     *
     */
    private void showSavingProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Saving");
        progressDialog.setMessage("Please wait while your data is being saved...");
        progressDialog.setIndeterminate(true);
        //progressDialog.set
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private class SaveDataTask extends AsyncTask<Void, Void, Void> {
        @Override

/**
 *
 * On pre execute
 *
 */
        protected void onPreExecute() {

            super.onPreExecute();
            showSavingProgressDialog();
        }

        @Override

/**
 *
 * Do in background
 *
 * @param voids  the voids.
 * @return Void
 */
        protected Void doInBackground(Void... voids) {

            // Save your data here
            save_data();
            return null;
        }

        @Override

/**
 *
 * On post execute
 *
 * @param aVoid  the a void.
 */
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            if(data_saved){
                progressDialog.dismiss();
                Saving_Policy_Page.super.onBackPressed();
                return;
            }
            progressDialog.dismiss();
            //progressDialog.setMessage("Data was not able to be saved. [Connection error]");
            //progressDialog.setCancelable(true);
            show_saving_error_dialog();



        }
    }


    /**
     *
     * Show saving error dialog
     *
     */
    private void show_saving_error_dialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("There was an error sending the data.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override

/**
 *
 * On click saves data and gives the users the choice to save and exit.
 *
 * @param dialog  the dialog.
 * @param which  the which.
 */
            public void onClick(DialogInterface dialog, int which) {

                // Save the data

                // User chose to save and exit, call the superclass onBackPressed() method to exit the activity

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     *
     * Show exit confirmation dialog
     *
     */
    private void showExitConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Would you like to save your changes before exiting?");

        // Set up the buttons
        builder.setPositiveButton("Leave and Save", new DialogInterface.OnClickListener() {
            @Override

/**
 *
 * On click that saves data and gives the users the choice to save and exit.
 *
 * @param dialog  the dialog.
 * @param which  the which.
 */
            public void onClick(DialogInterface dialog, int which) {

                // Save the data
                new SaveDataTask().execute();

                // User chose to save and exit, call the superclass onBackPressed() method to exit the activity

            }
        });

        builder.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
            @Override

/**
 *
 * On click that gives the option to stay and remain on activity.
 *
 * @param dialog  the dialog.
 * @param which  the which.
 */
            public void onClick(DialogInterface dialog, int which) {

                // User chose to stay, close the dialog and remain in the activity
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Leave and Discard", new DialogInterface.OnClickListener() {
            @Override

/**
 *
 * On click that gives the option to discard changes and exit activity
 *
 * @param dialog  the dialog.
 * @param which  the which.
 */
            public void onClick(DialogInterface dialog, int which) {

                // User chose to discard changes and exit, call the superclass onBackPressed() method to exit the activity
                Saving_Policy_Page.super.onBackPressed();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     *
     * Add policies using template
     *
     * @param linearLayout  the linear layout.
     * @param policies  the policies.
     */
    public void add_policies_using_template(LinearLayout linearLayout, ArrayList<String> policies){

        for(int i = 0; i < policies.size(); i++){
            int finalI = i;
            Saving_Policy policy = current_saving_policies.get(finalI);
            ConstraintLayout policy_layout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.title_and_description_template, null);
            policy_layout.setOnClickListener(new View.OnClickListener() {

                @Override

/**
 *
 * On click saves the changes and implements them
 *
 * @param v  the v.
 */
                public void onClick(View v) {

                    Edit_Saving_Policy_Page.current_policy = policy;
                    Edit_Saving_Policy_Page.policy_list = current_saving_policies;
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

        ImageView back_setting_im;
        TextView back_setting_txt;
        back_setting_im = (ImageView) findViewById(R.id.back_setting_btn_savingP);
        back_setting_txt = (TextView) findViewById(R.id.back_setting_text_savingP);

        back_setting_im.setOnClickListener(new View.OnClickListener()
        {
            @Override

/**
 *
 * On click that sends the user from the saving policy page to settings
 *
 * @param view  the view.
 */
            public void onClick(View view) {

                Intent intent = new Intent (Saving_Policy_Page.this,Settings.class);
                startActivity(intent);
            }
        });

        back_setting_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override

/**
 *
 * On click that sends the user from the saving policy page to settings
 *
 * @param view  the view.
 */
            public void onClick(View view) {

                Intent intent = new Intent (Saving_Policy_Page.this,Settings.class);
                startActivity(intent);
            }
        });
    }


    /**
     *
     * Add policies
     *
     * @param linearLayout  the linear layout.
     * @param policies  the policies.
     */
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

/**
 *
 * On click to edit text view from app.
 *
 * @param v  the v.
 */
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
