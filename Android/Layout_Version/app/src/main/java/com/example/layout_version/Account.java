package com.example.layout_version;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Account {
    private static Account single_instance = null;

    private String username;
    private String email;
    private String token;
    private RequestQueue mRequestQueue;


    private Account()
    {

    }

    public void signup(Context context, String username, String email, String password)
    {
        mRequestQueue = Volley.newRequestQueue(context);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            jsonBody.put("email", email);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        final String requestBody = jsonBody.toString();
        Resources resource = context.getResources();
        String url = resource.getString(R.string.db_base_url)
                + resource.getString(R.string.db_stage)
                + resource.getString(R.string.account_signup_endpoint);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    Toast.makeText(context, "Sign up success" + response.toString(), Toast.LENGTH_SHORT).show();
                    this.username = username;
                    this.email = email;
                },
                error -> {
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            JSONObject obj = new JSONObject(res);
                            Toast.makeText(context, obj.toString(), Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException | JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        mRequestQueue.add(jsonRequest);
    }

    public void signin(Context context, String username, String password)
    {
        mRequestQueue = Volley.newRequestQueue(context);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        final String requestBody = jsonBody.toString();

        Resources resource = context.getResources();
        String url = resource.getString(R.string.db_base_url)
                + resource.getString(R.string.db_stage)
                + resource.getString(R.string.account_signin_endpoint);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {

                    this.username = username;
                    try {
                        this.token = response.get("token").toString();
                        Toast.makeText(context, "Log in success" + response + "token: " + token, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            JSONObject obj = new JSONObject(res);
                            Toast.makeText(context, obj.toString(), Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException | JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        mRequestQueue.add(jsonRequest);
    }

    // Static method
    // Static method to create instance of Singleton class
    public static synchronized Account getInstance()
    {
        if (single_instance == null)
            single_instance = new Account();

        return single_instance;
    }
}
