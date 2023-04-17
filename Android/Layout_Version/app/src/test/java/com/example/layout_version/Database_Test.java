package com.example.layout_version;
import static com.example.layout_version.Database_Manager.convert_cameras;
import static com.example.layout_version.Database_Manager.convert_criterias;
import static com.example.layout_version.Database_Manager.convert_noitification_policies;
import static com.example.layout_version.Database_Manager.convert_saving_policies;
import static com.example.layout_version.Database_Manager.get_accounts_from_database;
import static com.example.layout_version.Database_Manager.get_cameras_from_database;
import static com.example.layout_version.Database_Manager.get_criterias_from_database;
import static com.example.layout_version.Database_Manager.get_notification_from_database;
import static com.example.layout_version.Database_Manager.get_saving_policy_from_database;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
public class Database_Test {

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
        System.out.println("notifications");
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_notification) + "\n");
        System.out.println("saving");
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_saving_policy) + "\n");
        System.out.println("criteria");
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_criteria) + "\n");
        System.out.println("hardware");
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_hardware) + "\n");
        System.out.println("account");
        System.out.println(Database_Manager.get_string_from_database(Database_Manager.url_account) + "\n");
    }



    @Test
    public void test_database(){
        Database_Manager.test();
    }
    @Test
    public void test_database_account_get(){
        ArrayList<Database_Manager.Account_Configuration> accounts = (ArrayList<Database_Manager.Account_Configuration>) get_accounts_from_database();
        boolean accounts_error = false;
        for(int i = 0; i < accounts.size(); i++){
            Database_Manager.Account_Configuration temp = accounts.get(i);
            if(temp.timestamp.equals("")){
                accounts_error = true;
                break;
            }
            if(temp.token.equals("")){
                accounts_error = true;
                break;
            }
            if(temp.email.equals("")){
                accounts_error = true;
                break;
            }
            if(temp.password.equals("")){
                accounts_error = true;
                break;
            }
            if(temp.status.equals("")){
                accounts_error = true;
                break;
            }
            if(temp.username.equals("")){
                accounts_error = true;
                break;
            }
            if(temp.account_id == 0){
                accounts_error = true;
                break;
            }
        }
        System.out.println("accounts working");
        assertFalse(accounts_error);
    }
    @Test
    public void test_database_get(){
        ArrayList<Camera> cameras = convert_cameras((get_cameras_from_database()));
        ArrayList<Saving_Policy> saving_policies = convert_saving_policies(get_saving_policy_from_database(), cameras);
    }
    @Test
    public void test_database_notification_get(){
        ArrayList<Criteria> criterias = convert_criterias(get_criterias_from_database());
        ArrayList<Camera> cameras = convert_cameras((get_cameras_from_database()));
        ArrayList<Notification_Policy> notification_policies = convert_noitification_policies(get_notification_from_database(), cameras, criterias);
        boolean error = false;
        for(int i = 0; i < notification_policies.size(); i++){
            Notification_Policy temp = notification_policies.get(i);
            if(temp.get_id() == 0){
                error = true;
                break;
            }
            if(temp.get_type() == 0){
                error = true;
                break;
            }
            if(temp.get_criteria() == null){
                error = true;
                break;
            }
        }
        assertFalse(error);
    }
    @Test
    public void test_database_criteria_get(){
        ArrayList<Criteria> criterias = convert_criterias(get_criterias_from_database());
        boolean error = false;
        for(int i = 0; i < criterias.size(); i++){
            Criteria temp = criterias.get(i);
            if(temp.id == 0){
                error = true;
                break;
            }
            if(temp.duration == 0){
                error = true;
                break;
            }
            if(temp.magnitude == 0){
                error = true;
                break;
            }

            if(temp.type == 0){
                error = true;
                break;
            }
        }
        assertFalse(error);
    }
    @Test
    public void test_database_hardware_get(){
        ArrayList<Camera> cameras = convert_cameras((get_cameras_from_database()));
        boolean error = false;
        for(int i = 0; i < cameras.size(); i++){
            Camera temp = cameras.get(i);
            if(temp.id == 0){
                error = true;
                break;
            }
            if(temp.account_id == 0){
                error = true;
                break;
            }
            if(temp.resolution.equals("")){
                error = true;
                break;
            }
            if(temp.name.equals("")){
                error = true;
                break;
            }
        }
        assertFalse(error);
    }
    @Test
    public void test_database_saving_get(){
        ArrayList<Camera> cameras = convert_cameras((get_cameras_from_database()));
        ArrayList<Saving_Policy> saving_policies = convert_saving_policies(get_saving_policy_from_database(), cameras);
        boolean error = false;
        for(int i = 0; i < saving_policies.size(); i++){
            Saving_Policy temp = saving_policies.get(i);
            if(temp.id == 0){
                error = true;
                break;
            }
            if(temp.get_resolution() == null){
                error = true;
                break;
            }
            if(temp.get_max_time() == 0){
                error = true;
                break;
            }
        }
        assertFalse(error);
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

}
