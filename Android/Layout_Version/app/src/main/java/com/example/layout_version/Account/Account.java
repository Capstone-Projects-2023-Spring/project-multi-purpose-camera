package com.example.layout_version.Account;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layout_version.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Account {
    private static Account single_instance = null;

    private String username;
    private String email;
    private String token;

    private Account(){}

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSignedIn(){
        return token != null;
    }

    public void signup(Context context, String username, String email, String password)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            jsonBody.put("email", email);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        nrm.Post(R.string.account_signup_endpoint, jsonBody,
                json -> {
                    try {
                        this.setUsername(jsonBody.get("username").toString());
                        this.setEmail(jsonBody.get("email").toString());
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                json -> {
                    try {
                        Toast.makeText(context, "Login failed: " + json.get("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void signin(Context context, String username, String password)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        nrm.Post(R.string.account_signin_endpoint, jsonBody,
                json -> {
                    try {
                        this.setUsername(json.get("username").toString());
                        this.setEmail(json.get("email").toString());
                        this.setToken(json.get("token").toString());
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                json -> {
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
    public static synchronized Account getInstance()
    {
        if (single_instance == null)
            single_instance = new Account();

        return single_instance;
    }
}
