package com.example.layout_version.Network;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layout_version.MainActivity;
import com.example.layout_version.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class NetworkRequestManager {
    private final RequestQueue mRequestQueue;
    private final Context context;

    public NetworkRequestManager(Context context)
    {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void Post(int endpointID, JSONObject data, NetworkInterface success, NetworkInterface fail)
    {
        Resources resource = context.getResources();
        String url = resource.getString(R.string.db_base_url)
                + resource.getString(R.string.db_stage)
                + resource.getString(endpointID);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, data,
                success::action,
                error -> {
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            JSONObject obj = new JSONObject(res);
                            fail.action((JSONObject) obj.get("body"));
                        } catch (UnsupportedEncodingException | JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }){
        };
        mRequestQueue.add(jsonRequest);
    }
}
