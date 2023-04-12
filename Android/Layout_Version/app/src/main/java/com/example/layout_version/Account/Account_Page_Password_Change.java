package com.example.layout_version.Account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.layout_version.MainActivity;
import com.example.layout_version.R;

public class Account_Page_Password_Change extends AppCompatActivity {
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_password);
        account = Account.getInstance();

        TextView password = findViewById(R.id.password);
        TextView re_password = findViewById(R.id.repassword);

        Button changeBtn = findViewById(R.id.change);

        ImageView back_home_im = findViewById(R.id.back_home_btn_setting);
        TextView back_home_txt = findViewById(R.id.back_home_text_setting);


        changeBtn.setOnClickListener(v ->
                {
                    if (password.getText().toString().equals(re_password.getText().toString())) {
                        account.changePassword(
                                Account_Page_Password_Change.this,
                                password.getText().toString(),
                                a -> startActivity(new Intent(
                                                Account_Page_Password_Change.this,
                                                Account_Page.class
                                        )
                                ),
                                a -> {
                                }
                        );
                    }else{
                        Toast.makeText(Account_Page_Password_Change.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        back_home_im.setOnClickListener(view -> onBackPressed());

        back_home_txt.setOnClickListener(view -> onBackPressed());

    }

}
