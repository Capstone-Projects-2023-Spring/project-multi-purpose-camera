package com.example.layout_version.Account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
//import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationManagerCompat;

import com.example.layout_version.MainTab.Library.VideoViewModel;
//import com.example.layout_version.Notifications;
import com.example.layout_version.R;

public class Account_Page extends AppCompatActivity {
    private Account account;
    private VideoViewModel videoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login_signup_page);
        account = Account.getInstance();

        Button loginbtn = findViewById(R.id.login);
        Button signupbtn = findViewById(R.id.signup);


        loginbtn.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page.this,Account_Page_Login.class));
        });

//        loginbtn.setOnClickListener(view -> {
//            startActivity(new Intent (Account_Page.this, Bluetooth_Page.class));
//        });

        signupbtn.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page.this,Account_Page_Signup.class));
        });

    }


    @Override
    public void onBackPressed() {

        if(account.isSignedIn())
        {
            super.onBackPressed();
        }
    }

}
