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
    public void send_Notification( NotificationManagerCompat managerCompat, String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "My_Notification");
        builder.setSmallIcon(android.R.drawable.stat_notify_sync);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        Notification notification;
        notification = builder.build();
        if (ActivityCompat.checkSelfPermission( context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        managerCompat.notify(1, notification);
    }

    public void send_Recording_Notification( NotificationManagerCompat managerCompat)
    {
        send_Notification(managerCompat, "Recording", "A recording has started");
    }
    public void send_New_Account_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "New Account", "You have created a new account");
    }
    public void send_Motion_Detected_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "Motion Detected", "Motion has been detected on your camera");
    }
    public void send_Network_Connected_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "Network Connected", "You have successfully connected to the network");
    }
    public void send_Sign_In_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "Signed In", "You have successfully signed in!");
    }
    public void send_Password_Change_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "Password Changed", "You have successfully changed your password");
    }
    public void send_Delete_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "Deleted", "Information from your account has been deleted");
    }
    public void send_Streaming_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "Stream Started!", "The system has started streaming");
    }
    public void send_Forgot_Password_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "Forgot Password", "Looks like you forgot your password. We got your back!");
    }
    public void send_Bluetooth_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "Bluetooth Connected", "Bluetooth has been connected and is working properly");
    }
}
