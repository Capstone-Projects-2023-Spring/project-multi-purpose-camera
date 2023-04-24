package com.example.layout_version;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notifications {
    private Context context;
    public Notifications( Context c ){
        context = c;
    }
    public void send_Notifications( NotificationManagerCompat managerCompat ) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "My_Notification");
        builder.setSmallIcon(android.R.drawable.stat_notify_sync);
        builder.setContentTitle("Test");
        builder.setContentText("This is a test notification");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        Notification notification;
        notification = builder.build();
        if (ActivityCompat.checkSelfPermission( context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        managerCompat.notify(1, notification);
    }
}
