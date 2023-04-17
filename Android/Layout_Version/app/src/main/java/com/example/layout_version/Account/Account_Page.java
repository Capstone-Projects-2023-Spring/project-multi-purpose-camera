package com.example.layout_version.Account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.layout_version.R;

public class Account_Page extends AppCompatActivity {
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login_signup_page);
        account = Account.getInstance();

        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);

        Button loginbtn = findViewById(R.id.login);
        Button signupbtn = findViewById(R.id.signup);

        ImageView back_home_im = findViewById(R.id.back_home_btn_setting);
        TextView back_home_txt = findViewById(R.id.back_home_text_setting);

        TextView resetPassword = findViewById(R.id.resetPassword);

        loginbtn.setOnClickListener(v ->
                account.signin(
                        Account_Page.this,
                        username.getText().toString(),
                        password.getText().toString(),
                        a -> onBackPressed(),
                        a -> {}
                )
        );

        back_home_im.setOnClickListener(view -> onBackPressed());

        back_home_txt.setOnClickListener(view -> onBackPressed());

        signupbtn.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page.this,Account_Page_Signup.class));
        });

        resetPassword.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page.this,Account_Page_Forgot_Password.class));
        });

    }

}
