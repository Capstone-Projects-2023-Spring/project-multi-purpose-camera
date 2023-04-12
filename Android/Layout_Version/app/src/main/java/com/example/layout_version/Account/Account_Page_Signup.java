package com.example.layout_version.Account;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.layout_version.R;

public class Account_Page_Signup extends AppCompatActivity {
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup_page);
        account = Account.getInstance();
        ImageView back_im;
        TextView back_txt;

        back_im = findViewById(R.id.back_home_btn_setting);
        back_txt = findViewById(R.id.back_home_text_setting);

        Button signupbtn = findViewById(R.id.signup);

        TextView username = findViewById(R.id.username);
        TextView email = findViewById(R.id.emailAddress);
        TextView password = findViewById(R.id.password);
        TextView re_password = findViewById(R.id.repassword);

        signupbtn.setOnClickListener(v -> {
            if(password.getText().toString().equals(re_password.getText().toString()))
            {
                account.signup(
                        Account_Page_Signup.this,
                        username.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        a -> {},
                        a -> {}
                );
            }
            else{
                Toast.makeText(Account_Page_Signup.this, "Password does not match", Toast.LENGTH_SHORT).show();
            }
        });

        back_im.setOnClickListener(view -> onBackPressed());

        back_txt.setOnClickListener(view -> onBackPressed());
    }

}
