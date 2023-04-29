package com.example.layout_version.Account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.layout_version.MainActivity;
import com.example.layout_version.MainTab.Library.VideoViewModel;
import com.example.layout_version.Notifications;
import com.example.layout_version.R;

public class Account_Page extends AppCompatActivity {
    private Account account;
    private VideoViewModel videoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login_signup_page);
        account = Account.getInstance();

        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);

        Button loginbtn = findViewById(R.id.login);
        Button signupbtn = findViewById(R.id.signup);

        TextView resetPassword = findViewById(R.id.resetPassword);
        Notifications notif = new Notifications(this);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }

        notif.send_Sign_In_Notification(managerCompat);

        loginbtn.setOnClickListener(v ->
                account.signin(
                        Account_Page.this,
                        username.getText().toString(),
                        password.getText().toString(),
                        () -> onBackPressed(),
                        () -> {}
                )
        );
//        loginbtn.setOnClickListener(view -> {
//            startActivity(new Intent (Account_Page.this, Bluetooth_Page.class));
//        });

        signupbtn.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page.this,Account_Page_Signup.class));
        });

        resetPassword.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page.this,Account_Page_Forgot_Password.class));
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
