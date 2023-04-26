package com.example.layout_version.Account;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import androidx.core.app.NotificationManagerCompat;

import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.Notifications;
import com.example.layout_version.R;

import org.json.JSONException;
import org.json.JSONObject;


interface AccountActionInterface
{
    void action();
}

public class Account {
    private static Account single_instance = null;
    private String username;
    private String email;
    private String code;
    private String status;
    private MutableLiveData<String> tokenData;
//    private TokenChangeInterface tokenChangeInterface;

    private Account(){
        tokenData = new MutableLiveData<>();
    }

//    private Account(TokenChangeInterface tokenChangeInterface){
//        this.tokenChangeInterface = tokenChangeInterface;
//    }

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
        tokenData.setValue(token);
//        if(tokenChangeInterface != null)
//            tokenChangeInterface.changed(token);
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public MutableLiveData<String> getTokenData()
    {
        return tokenData;
    }
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSignedIn(){
        return tokenData.getValue() != null;
    }

    public void clear()
    {
        username = null;
        email = null;
        status = null;
        tokenData.setValue(null);
    }

    public void profile(Context context, AccountActionInterface success, AccountActionInterface fail)
    {
        if(tokenData.getValue() == null)
        {
            Toast.makeText(context, "Log in first", Toast.LENGTH_SHORT).show();
            fail.action();
            return;
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", tokenData.getValue());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        nrm.Post(R.string.account_profile_endpoint, jsonBody,
                json -> {
                    try {
                        this.setUsername(json.get("username").toString());
                        this.setEmail(json.get("email").toString());
                        this.setStatus(json.get("status").toString());
                        success.action();
                    } catch (JSONException e) {
                        fail.action();
                    }
                },
                json -> {
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        fail.action();
                    } catch (JSONException e) {
                        fail.action();
                    }
                });
    }

    public void signup(Context context, String username, String email, String password,
                       AccountActionInterface success, AccountActionInterface fail)
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
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        this.setToken(json.get("token").toString());
                        success.action();
                    } catch (JSONException e) {
                        fail.action();
                    }
                },
                json -> {
                    try {
                        Toast.makeText(context, "Login failed: " + json.get("message"), Toast.LENGTH_SHORT).show();
                        fail.action();
                    } catch (JSONException e) {
                        fail.action();
                    }
                });
    }

    public void signin(Context context, String username, String password,
                       AccountActionInterface success, AccountActionInterface fail)
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
                        success.action();
                    } catch (JSONException e) {
                        fail.action();
                    }
                },
                json -> {
                    fail.action();
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException ignored) {

                    }
                }
        );
    }



    public void reset(Context context, String username,
                      AccountActionInterface success, AccountActionInterface fail)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        nrm.Post(R.string.account_reset_endpoint, jsonBody,
                json -> {
                    try {
                        this.setUsername(username);
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        success.action();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                json -> {
                    fail.action();
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException ignored) {

                    }
                }
        );
    }

    public void verifyCode(Context context, String code,
                           AccountActionInterface success, AccountActionInterface fail)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("code", code);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        nrm.Post(R.string.account_code_endpoint, jsonBody,
                json -> {
                    try {
                        this.setCode(code);
                        this.setToken(json.get("token").toString());
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        success.action();
                    } catch (JSONException e) {
                        fail.action();
                    }
                },
                json -> {
                    fail.action();
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException ignored) {

                    }
                }
        );
    }

    public void changePassword(Context context, String password,
                               AccountActionInterface success, AccountActionInterface fail)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            jsonBody.put("token", tokenData.getValue());
            jsonBody.put("code", code);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        nrm.Post(R.string.account_password_endpoint, jsonBody,
                json -> {
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        success.action();
                    } catch (JSONException e) {
                        fail.action();
                    }
                },
                json -> {
                    fail.action();
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException ignored) {

                    }
                }
        );
    }

    public void delete(Context context, AccountActionInterface success, AccountActionInterface fail)
    {
        if(tokenData.getValue() == null)
        {
            Toast.makeText(context, "Log in first", Toast.LENGTH_SHORT).show();
            fail.action();
            return;
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", tokenData.getValue());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        nrm.Post(R.string.account_delete_endpoint, jsonBody,
                json -> {
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        success.action();
                    } catch (JSONException e) {
                        fail.action();
                    }
                },
                json -> {
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        fail.action();
                    } catch (JSONException e) {
                        fail.action();
                    }
                });
    }


    public static synchronized Account getInstance()
    {
        if (single_instance == null)
            single_instance = new Account();

        return single_instance;
    }

//    public static synchronized Account getInstance(TokenChangeInterface changeInterface)
//    {
//        if (single_instance == null)
//            single_instance = new Account(changeInterface);
//        return single_instance;
//    }
}
