package com.example.layout_version;

import static androidx.core.content.ContextCompat.getSystemService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Notifications n;
    private Looper mLooper;
    @Test
    public void test_user_settings_query(){
        List<Database_Manager.Account_Configuration> accounts = Database_Manager.get_accounts_from_database();
        for(int i = 0; i < accounts.size(); i++)
            System.out.println(accounts.get(i));
        List<Database_Manager.Notification_Configuration> notifications = Database_Manager.get_notification_from_database();
        for(int i = 0; i < notifications.size(); i++)
            System.out.println(notifications.get(i));
        List<Database_Manager.Saving_Policy_Configuration> saving_policies = Database_Manager.get_saving_policy_from_database();
        for(int i = 0; i < saving_policies.size(); i++)
            System.out.println(saving_policies.get(i));
        List<Database_Manager.Criteria_Configuration> criterias = Database_Manager.get_criterias_from_database();
        for(int i = 0; i < criterias.size(); i++)
            System.out.println(criterias.get(i));
        List<Database_Manager.Camera_Configuration> cameras = Database_Manager.get_cameras_from_database();
        for(int i = 0; i < cameras.size(); i++)
            System.out.println(cameras.get(i));
    }

    @Test
    public void test_query(){
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_notification) + "\n");
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_saving_policy) + "\n");
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_criteria) + "\n");
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_hardware) + "\n");
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_account) + "\n");
    }



    @Test
    public void test_post(){
        Database_Manager.test();
    }
    @Test
    public void build_objects_from_database(){
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_saving_policy));
        System.out.println("criterias");
        ArrayList<Criteria> criterias = Database_Manager.convert_criterias(Database_Manager.get_criterias_from_database());
        for(int i = 0; i < criterias.size(); i++){
            System.out.println(criterias.get(i));
        }
        System.out.println("camera");
        ArrayList<Camera> cameras = Database_Manager.convert_cameras((Database_Manager.get_cameras_from_database()));
        for(int i = 0; i < cameras.size(); i++){
            System.out.println(cameras.get(i));
        }
        System.out.println("saving_policies");
        ArrayList<Saving_Policy> saving_policies = Database_Manager.convert_saving_policies(Database_Manager.get_saving_policy_from_database(), cameras);
        for(int i = 0; i < saving_policies.size(); i++){
            System.out.println(saving_policies.get(i));
        }
        System.out.println("notifcations");
        ArrayList<Notification_Policy> notification_policies = Database_Manager.convert_noitification_policies(Database_Manager.get_notification_from_database(), cameras, criterias);
        for(int i = 0; i < notification_policies.size(); i++){
            System.out.println(notification_policies.get(i));
        }
    }

    @Test
    public void test_saving_policy_duplicate_checker(){
        ArrayList<Saving_Policy> policies = new ArrayList<>();
        Saving_Policy_Page.is_policies_valid(policies);
    }

    /*
    Testing notifications
    Tried testing the notifications using a mock Context object and mock Looper object.
    The build isn't recognizing the mock even though I added it to the dependencies and
    it synced correctly. The main problem with testing the notifications is using context outside
    the onCreate() method. Everything is trying to get around using context outside of onCreate().
    However, the notifications do work when you run the application.
    @Test
    public void test_Notifications_Using_Looper(){
        System.out.println("Application Notifications");

        Looper mLooper = mock(Looper.class);
        Context c = mock(Context.class);
        when(c.getMainLooper()).thenReturn(mLooper);
        n = new Notifications(c);

        //Notifications n = Notifications.getInstance();
        MainActivity main = new MainActivity();
        NotificationManagerCompat managerCompat = main.getManagerCompat();
        n.send_Sign_In_Notification(managerCompat);
        System.out.println("Application Notificatons");
        n.send_New_Account_Notification(managerCompat);
        System.out.println("New Account Notificaton");
        n.send_Delete_Notification(managerCompat);
        System.out.println("Delete Notificaton");
        n.send_Password_Change_Notification(managerCompat);
        System.out.println("Password Change Notificaton");
        n.send_Forgot_Password_Notification(managerCompat);
        System.out.println("Forgot Password Notificaton");
        n.send_Motion_Detected_Notification(managerCompat);
        System.out.println("Motion Detected Notificaton");
        n.send_Recording_Notification(managerCompat);
        System.out.println("Recording Notificaton");
        n.send_Network_Connected_Notification(managerCompat);
        System.out.println("Network Connected Notificaton");
        n.send_Sign_In_Notification(managerCompat);
        System.out.println("Sign In Notificaton");
        n.send_Streaming_Notification(managerCompat);
        System.out.println("Streaming Notificaton");
        n.send_Network_Not_Connected_Notification(managerCompat);
        System.out.println("Network Not Connected Notificaton");
        n.send_Bluetooth_Notification(managerCompat);
        System.out.println("Bluetooth Notificaton");
    }
    */

}