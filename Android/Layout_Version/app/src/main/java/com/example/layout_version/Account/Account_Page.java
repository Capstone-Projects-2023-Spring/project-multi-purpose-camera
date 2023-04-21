package com.example.layout_version.Account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.layout_version.MainActivity;
import com.example.layout_version.MainTab.VideoViewModel;
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

        loginbtn.setOnClickListener(v ->
                account.signin(
                        Account_Page.this,
                        username.getText().toString(),
                        password.getText().toString(),
                        a -> {
                            onBackPressed();

                        },
                        a -> {}
                )
        );

        loginbtn.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page.this,MainActivity.class));
        });

        signupbtn.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page.this,Account_Page_Signup.class));
        });

        resetPassword.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page.this,Account_Page_Forgot_Password.class));
        });

    }

}
