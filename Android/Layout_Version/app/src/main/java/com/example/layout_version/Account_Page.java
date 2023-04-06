package com.example.layout_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Account_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login_signup_page);

        TextView username = (TextView) findViewById(R.id.EmailAddress);
        TextView password = (TextView) findViewById(R.id.Password);

        Button loginbtn = (Button) findViewById(R.id.login);

        EditText clearEmail = (EditText) findViewById(R.id.EmailAddress);
        EditText clearPassword = (EditText) findViewById(R.id.Password);

        ImageView back_home_im;
        TextView back_home_txt;

        back_home_im = (ImageView) findViewById(R.id.back_home_btn_setting);
        back_home_txt = (TextView) findViewById(R.id.back_home_text_setting);

        back_home_im.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Account_Page.this,MainActivity.class);
                startActivity(intent);
            }
        });

        back_home_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Account_Page.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // Account U: 'JohnSmith@google.com' & P: 'password'
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("JohnSmith@gmail.com") && password.getText().toString().equals("password"))
                {
                    Toast.makeText(Account_Page.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    clearEmail.setText("");
                    clearPassword.setText("");
                }
                else
                {
                    Toast.makeText(Account_Page.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
