package com.example.layout_version.Account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.layout_version.R;

public class Account_Page_Profile extends AppCompatActivity {
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_profile);
        account = Account.getInstance();

        TextView username = findViewById(R.id.username);
        TextView resetPassword = findViewById(R.id.resetPassword);

        EditText email = findViewById(R.id.emailAddress);

        Button logOutBtn = findViewById(R.id.logout);
        Button deleteBtn = findViewById(R.id.delete);

        ImageView back_home_im = findViewById(R.id.back_home_btn_setting);
        TextView back_home_txt = findViewById(R.id.back_home_text_setting);

        back_home_im.setOnClickListener(view -> onBackPressed());

        back_home_txt.setOnClickListener(view -> onBackPressed());

        resetPassword.setOnClickListener(view -> {
            startActivity(new Intent (Account_Page_Profile.this,Account_Page_Forgot_Password.class));
        });

        email.setFocusable(false);

        logOutBtn.setOnClickListener(view -> {
            account.clear();
            onBackPressed();
        });

        deleteBtn.setOnClickListener(view -> {
            new AlertDialog.Builder(Account_Page_Profile.this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this account?")

                    .setPositiveButton(android.R.string.yes, (dialog, which) ->
                            account.delete(
                                    Account_Page_Profile.this,
                                    a-> {
                                        onBackPressed();
                                        account.clear();
                                    },
                                    a-> {}
                    ))

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        account.profile(
                Account_Page_Profile.this,
                a -> {
                    username.setText(a.getUsername());
                    email.setText(a.getEmail());
                },
                a -> {
                    onBackPressed();
                    account.setToken(null);
                });

    }

}
