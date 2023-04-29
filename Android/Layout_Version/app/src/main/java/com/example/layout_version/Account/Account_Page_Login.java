package com.example.layout_version.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layout_version.MainActivity;
import com.example.layout_version.Notifications;
import com.example.layout_version.R;

public class Account_Page_Login extends AppCompatActivity {
    private Account account;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page_login);
        account = Account.getInstance();
        Notifications notif = new Notifications(this);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        notif.send_Sign_In_Notification(managerCompat);

        ImageView back_im;
        TextView back_txt;

        back_im = findViewById(R.id.back_home_btn_setting);
        back_txt = findViewById(R.id.back_home_text_setting);

        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);

        Button loginbtn = findViewById(R.id.login);

        TextView resetPassword = findViewById(R.id.resetPassword);

        loginbtn.setOnClickListener(v ->
                account.signin(
                        Account_Page_Login.this,
                        username.getText().toString(),
                        password.getText().toString(),
                        () -> {
                            startActivity(new Intent(Account_Page_Login.this, MainActivity.class));
                        },
                        () -> {}
                )
        );

        resetPassword.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page_Login.this,Account_Page_Forgot_Password.class));
        });

        back_im.setOnClickListener(view -> onBackPressed());

        back_txt.setOnClickListener(view -> onBackPressed());
    }
}