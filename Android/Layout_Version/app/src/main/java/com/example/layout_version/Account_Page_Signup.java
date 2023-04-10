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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup_page);

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
                mRequestQueue = Volley.newRequestQueue(Account_Page_Signup.this);
                if(!password.getText().toString().equals(re_password.getText().toString()))
                {
                    Toast.makeText(Account_Page_Signup.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("username", username.getText().toString());
                    jsonBody.put("password", password.getText().toString());
                    jsonBody.put("email", email.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                final String requestBody = jsonBody.toString();

                String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/signup";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> Toast.makeText(Account_Page_Signup.this, "Sign up success", Toast.LENGTH_SHORT).show(),
                        error -> {
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    Toast.makeText(Account_Page_Signup.this, obj.toString(), Toast.LENGTH_SHORT).show();
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
            }
        });

    }

}
