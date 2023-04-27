package com.example.layout_version;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.layout_version.Account.Account;

public class Notifications {
    private Context context;

    private static Notifications single_instance = null;
    NotificationManagerCompat managerCompat;
    private Notifications( Context c ){

        context = c;
        managerCompat = NotificationManagerCompat.from(c);
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

    public void send_Recording_Notification()
    {
        send_Notification(managerCompat, "Recording", "A recording has started");
    }
    public void send_New_Account_Notification() {
        send_Notification(managerCompat, "New Account", "You have created a new account");
    }
    public void send_Motion_Detected_Notification() {
        send_Notification(managerCompat, "Motion Detected", "Motion has been detected on your camera");
    }
    public void send_Network_Connected_Notification() {
        send_Notification(managerCompat, "Network Connected", "You have successfully connected to the network");
    }
    public void send_Sign_In_Notification() {
        send_Notification(managerCompat, "Signed In", "You have successfully signed in!");
    }
    public void send_Password_Change_Notification() {
        send_Notification(managerCompat, "Password Changed", "You have successfully changed your password");
    }
    public void send_Delete_Notification() {
        send_Notification(managerCompat, "Deleted", "Information from your account has been deleted");
    }
    public void send_Stream_Notification( NotificationManagerCompat managerCompat ) {
        send_Notification(managerCompat, "Stream Started", "You have started streaming");
    }
    public static synchronized Notifications getInstance(Context c)
    {
        if (single_instance == null)
            single_instance = new Notifications(c);

        return single_instance;
    }
    public static synchronized Notifications getInstance()
    {
        return single_instance;
    }

}
