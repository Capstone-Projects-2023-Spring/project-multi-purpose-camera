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
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.content.res.AppCompatResources;
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

    private final ImageView resultView;
    public NetworkRequestManager(Context context)
    {
        this(context, null, null);
    }

    public NetworkRequestManager(Context context, ProgressBar progressBar)
    {
        this(context, progressBar, null);
    }

    public NetworkRequestManager(Context context, ImageView resultView)
    {
        this(context, null, resultView);
    }

    public NetworkRequestManager(Context context, ProgressBar progressBar, ImageView resultView)
    {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
        this.progressBar = progressBar;
        if(progressBar != null)
            progressBar.setVisibility(View.GONE);
        this.resultView = resultView;
        if(resultView != null)
            resultView.setVisibility(View.GONE);
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
        Request(url, Request.Method.POST, data, success, fail, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4, DefaultRetryPolicy.DEFAULT_MAX_RETRIES);
    }

    public void Put(int endpointID, JSONObject data, NetworkInterface success, NetworkInterface fail)
    {
        Resources resource = context.getResources();
        String url = resource.getString(R.string.db_base_url)
                + resource.getString(R.string.db_stage)
                + resource.getString(endpointID);
        Put(url, data, success, fail);
    }

    public void Put(String url, JSONObject data, NetworkInterface success, NetworkInterface fail)
    {
        Request(url, Request.Method.PUT, data, success, fail, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, 0);
    }

    public void Request(String url, int method, JSONObject data, NetworkInterface success, NetworkInterface fail, int timeout, int retry)
    {
        if(progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        if(resultView != null)
            resultView.setVisibility(View.GONE);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(method, url, data,
                json -> {
                    success.action(json);
                    if(progressBar != null)
                        progressBar.setVisibility(View.GONE);
                    if(resultView != null)
                    {
                        resultView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.baseline_check_circle_outline_32));
                        resultView.setVisibility(View.VISIBLE);
                    }
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
                    if(resultView != null)
                    {
                        resultView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.baseline_highlight_off_24));
                        resultView.setVisibility(View.VISIBLE);
                    }
                }){
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                retry,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonRequest);
    }
}
