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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Account_Page extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login_signup_page);

        TextView username = (TextView) findViewById(R.id.EmailAddress);
        TextView password = (TextView) findViewById(R.id.Password);

        Button loginbtn = (Button) findViewById(R.id.login);

        EditText clearEmail = (EditText) findViewById(R.id.EmailAddress);
        EditText clearPassword = (EditText) findViewById(R.id.Password);

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
                mRequestQueue = Volley.newRequestQueue(Account_Page.this);

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("username_", username.getText().toString());
                    jsonBody.put("password_", password.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                final String requestBody = jsonBody.toString();

                String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/signin";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> Toast.makeText(Account_Page.this, response, Toast.LENGTH_SHORT).show(),
                        error -> {

                        }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }
                };
                mRequestQueue.add(stringRequest);
//                if (username.getText().toString().equals("JohnSmith@gmail.com") && password.getText().toString().equals("password"))
//                {
//                    Toast.makeText(Account_Page.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
//                    clearEmail.setText("");
//                    clearPassword.setText("");
//                }
//                else
//                {
//                    Toast.makeText(Account_Page.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

}
