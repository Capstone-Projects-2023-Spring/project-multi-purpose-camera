package com.example.layout_version.Network;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layout_version.MainActivity;
import com.example.layout_version.Notifications;
import com.example.layout_version.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class NetworkRequestManager {
    private final RequestQueue mRequestQueue;
    private final Context context;
    private final ProgressBar progressBar;
    public NetworkRequestManager(Context context)
    {
        this(context, null);
    }

    public NetworkRequestManager(Context context, ProgressBar progressBar)
    {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
        this.progressBar = progressBar;
        if(progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    public void Post(int endpointID, JSONObject data, NetworkInterface success, NetworkInterface fail)
    {
        Resources resource = context.getResources();
        String url = resource.getString(R.string.db_base_url)
                + resource.getString(R.string.db_stage)
                + resource.getString(endpointID);

        Post(url, data, success, fail);
    }

    public void Post(String url, JSONObject data, NetworkInterface success, NetworkInterface fail)
    {
        Request(url, Request.Method.POST, data, success, fail);
    }

    public void Put(String url, JSONObject data, NetworkInterface success, NetworkInterface fail)
    {
        Request(url, Request.Method.PUT, data, success, fail);
    }

    public void Request(String url, int method, JSONObject data, NetworkInterface success, NetworkInterface fail)
    {
        if(progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(method, url, data,
                json -> {
                    success.action(json);
                    if(progressBar != null)
                        progressBar.setVisibility(View.GONE);
                },
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
                            fail.action(null);
                        }
                    }
                    if(progressBar != null)
                        progressBar.setVisibility(View.GONE);
                }){
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonRequest);
    }
}
