package com.example.layout_version.Account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.layout_version.R;

public class Account_Page_Verify_Code extends AppCompatActivity {
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_verify);
        account = Account.getInstance();

        TextView code = findViewById(R.id.code);
        TextView resend = findViewById(R.id.resend);

        Button sendBtn = findViewById(R.id.send);

        ImageView back_home_im = findViewById(R.id.back_home_btn_setting);
        TextView back_home_txt = findViewById(R.id.back_home_text_setting);

        sendBtn.setOnClickListener(v ->
                account.verifyCode(
                        Account_Page_Verify_Code.this,
                        code.getText().toString(),
                        () -> startActivity(new Intent(
                                        Account_Page_Verify_Code.this,
                                        Account_Page_Password_Change.class
                                )
                        ),
                        () -> {}
                )
        );

        resend.setOnClickListener(v ->
                account.reset(
                        Account_Page_Verify_Code.this,
                        account.getUsername(),
                        () -> {},
                        () -> {}
                )
        );

        back_home_im.setOnClickListener(view -> onBackPressed());

        back_home_txt.setOnClickListener(view -> onBackPressed());

    }

}
