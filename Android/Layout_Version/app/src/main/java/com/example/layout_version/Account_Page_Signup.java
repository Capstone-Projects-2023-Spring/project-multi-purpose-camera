package com.example.layout_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Account_Page_Signup extends AppCompatActivity {
    private RequestQueue mRequestQueue;

    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup_page);
        account = Account.getInstance();
        ImageView back_im;
        TextView back_txt;

        back_im = (ImageView) findViewById(R.id.back_home_btn_setting);
        back_txt = (TextView) findViewById(R.id.back_home_text_setting);

        Button signupbtn = (Button) findViewById(R.id.signup);

        TextView username = (TextView) findViewById(R.id.username);
        TextView email = (TextView) findViewById(R.id.emailAddress);
        TextView password = (TextView) findViewById(R.id.password);
        TextView re_password = (TextView) findViewById(R.id.repassword);

        back_im.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Account_Page_Signup.this,Account_Page.class);
                startActivity(intent);
            }
        });

        back_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Account_Page_Signup.this,Account_Page.class);
                startActivity(intent);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(re_password.getText().toString()))
                {
                    account.signup(
                            Account_Page_Signup.this,
                            username.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString()
                    );
                }
                else{
                    Toast.makeText(Account_Page_Signup.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
