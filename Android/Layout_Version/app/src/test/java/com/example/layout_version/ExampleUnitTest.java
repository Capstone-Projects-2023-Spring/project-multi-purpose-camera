package com.example.layout_version;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

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


}