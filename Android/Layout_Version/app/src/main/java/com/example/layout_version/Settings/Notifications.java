package com.example.layout_version.Settings;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.layout_version.R;

public class Notifications {
    private Context context;
    public Notifications(Context context)
    {
        this.context = context;
    }
    public void send_Notifications(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My_Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(context, NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Notification notification;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "My_Notification");
        builder.setSmallIcon(android.R.drawable.stat_notify_sync);
        builder.setContentTitle("Settings");
        builder.setContentText("You have clicked the Settings button");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        notification = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            managerCompat.notify(1, notification);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }
}
