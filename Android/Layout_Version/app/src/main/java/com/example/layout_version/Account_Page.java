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
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
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
        Button signupbtn = (Button) findViewById(R.id.signup);

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
                    jsonBody.put("username", username.getText().toString());
                    jsonBody.put("password", password.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                final String requestBody = jsonBody.toString();

                String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/signin";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> Toast.makeText(Account_Page.this, "Log in success", Toast.LENGTH_SHORT).show(),
                        error -> {
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    Toast.makeText(Account_Page.this, obj.toString(), Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException e1) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                } catch (JSONException e2) {
                                    // returned data is not JSONObject?
                                    e2.printStackTrace();
                                }
                            }
                        }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                mRequestQueue.add(stringRequest);

                if (username.getText().toString().equals("John Smith") && password.getText().toString().equals("Password"))
                {
                    Toast.makeText(Account_Page.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    clearEmail.setText("");
                    clearPassword.setText("");
                }
                else if (username.getText().toString().equals("Keita Nakashima") && password.getText().toString().equals("Password"))
                {
                    Toast.makeText(Account_Page.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    clearEmail.setText("");
                    clearPassword.setText("");
                }
                else if (username.getText().toString().equals("Tom Morgan") && password.getText().toString().equals("Password"))
                {
                    Toast.makeText(Account_Page.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    clearEmail.setText("");
                    clearPassword.setText("");
                }
                else if (username.getText().toString().equals("username") && password.getText().toString().equals("password"))
                {
                    Toast.makeText(Account_Page.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    clearEmail.setText("");
                    clearPassword.setText("");
                }
                else
                {
                    Toast.makeText(Account_Page.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                }
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
