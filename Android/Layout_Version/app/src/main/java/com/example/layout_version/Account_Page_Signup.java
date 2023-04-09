package com.example.layout_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Account_Page_Signup extends AppCompatActivity {

    /**
     * The class Account_ page_ signup extends application compat activity. It creates the account signup page for the user to signup as well as give action to its buttons.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup_page);

        ImageView back_im;
        TextView back_txt;

        back_im = (ImageView) findViewById(R.id.back_home_btn_setting);
        back_txt = (TextView) findViewById(R.id.back_home_text_setting);

        Button signupbtn = (Button) findViewById(R.id.signup);

        back_im.setOnClickListener(new View.OnClickListener()
        {

            /**
             *
             * On click that sends the user from the signup page to the account page.
             *
             * @param view  the view.
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Account_Page_Signup.this,Account_Page.class);
                startActivity(intent);
            }
        });

        /**
         *
         * On click that sends the user from the signup page to the account page.
         *
         * @param view  the view.
         */
        back_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Account_Page_Signup.this,Account_Page.class);
                startActivity(intent);
            }
        });

//        signupbtn.setOnClickListener(new View.OnClickListener() {
//            /**
//             *
//             * On click
//             *
//             * @param v  the v.
//             */
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

}
