package com.example.layout_version.Account;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.Notifications;
import com.example.layout_version.R;

import org.json.JSONException;
import org.json.JSONObject;


interface AccountActionInterface
{
    void action(Account account);
}

public class Account {
    private static Account single_instance = null;
    private String username;
    private String email;
    private String token;
    private String code;
    private String status;
    private TokenChangeInterface tokenChangeInterface;

    private Account(){}
    private Context context;

    private Notifications notif;

    private NotificationManagerCompat managerCompat;

    private Account(TokenChangeInterface tokenChangeInterface, Context c){
        this.tokenChangeInterface = tokenChangeInterface;
        context = c;
        notif = new Notifications(context);
        managerCompat = NotificationManagerCompat.from(context);

    }

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
        if(tokenChangeInterface != null)
            tokenChangeInterface.changed(token);
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setStatus(String status)
    {
        this.status = status;
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

    public void clear()
    {
        username = null;
        email = null;
        token = null;
        status = null;
        if(tokenChangeInterface != null)
            tokenChangeInterface.changed(token);
    }

    public void profile(Context context, AccountActionInterface success, AccountActionInterface fail)
    {
        if(token == null)
        {
            Toast.makeText(context, "Log in first", Toast.LENGTH_SHORT).show();
            fail.action(this);
            return;
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
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
                        success.action(this);
                    } catch (JSONException e) {
                        fail.action(this);
                    }
                },
                json -> {
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        fail.action(this);
                    } catch (JSONException e) {
                        fail.action(this);
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
                        success.action(this);
                    } catch (JSONException e) {
                        fail.action(this);
                    }
                },
                json -> {
                    try {
                        Toast.makeText(context, "Login failed: " + json.get("message"), Toast.LENGTH_SHORT).show();
                        fail.action(this);
                    } catch (JSONException e) {
                        fail.action(this);
                    }
                });
        notif.send_New_Account_Notification( managerCompat);
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
                        success.action(this);
                    } catch (JSONException e) {
                        fail.action(this);
                    }
                },
                json -> {
                    fail.action(this);
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException ignored) {

                    }
                }
        );
        notif.send_Sign_In_Notification( managerCompat);
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
                        success.action(this);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                json -> {
                    fail.action(this);
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
                        success.action(this);
                    } catch (JSONException e) {
                        fail.action(this);
                    }
                },
                json -> {
                    fail.action(this);
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
            jsonBody.put("token", token);
            jsonBody.put("code", code);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        nrm.Post(R.string.account_password_endpoint, jsonBody,
                json -> {
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        success.action(this);
                    } catch (JSONException e) {
                        fail.action(this);
                    }
                },
                json -> {
                    fail.action(this);
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException ignored) {

                    }
                }
        );
        notif.send_Password_Change_Notification( managerCompat);
    }

    public void delete(Context context, AccountActionInterface success, AccountActionInterface fail)
    {
        if(token == null)
        {
            Toast.makeText(context, "Log in first", Toast.LENGTH_SHORT).show();
            fail.action(this);
            return;
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        nrm.Post(R.string.account_delete_endpoint, jsonBody,
                json -> {
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        success.action(this);
                    } catch (JSONException e) {
                        fail.action(this);
                    }
                },
                json -> {
                    try {
                        Toast.makeText(context, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                        fail.action(this);
                    } catch (JSONException e) {
                        fail.action(this);
                    }
                });
        notif.send_Delete_Notification( managerCompat);
    }


    public static synchronized Account getInstance()
    {
        if (single_instance == null)
            single_instance = new Account();

        return single_instance;
    }

    public static synchronized Account getInstance(TokenChangeInterface changeInterface)
    {
        if (single_instance == null)
            single_instance = new Account(changeInterface);
        return single_instance;
    }
}
