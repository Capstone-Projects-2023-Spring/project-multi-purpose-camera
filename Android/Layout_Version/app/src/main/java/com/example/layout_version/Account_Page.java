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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Account_Page extends AppCompatActivity {
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login_signup_page);
        account = Account.getInstance();

        TextView username = (TextView) findViewById(R.id.emailAddress);
        TextView password = (TextView) findViewById(R.id.password);

        Button loginbtn = (Button) findViewById(R.id.login);
        Button signupbtn = (Button) findViewById(R.id.signup);

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
                account.signin(Account_Page.this, username.getText().toString(), password.getText().toString());
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Account_Page.this,Account_Page_Signup.class);
                startActivity(intent);
            }
        });

    }

}
