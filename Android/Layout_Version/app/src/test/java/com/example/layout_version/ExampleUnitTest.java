package com.example.layout_version;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    static String url_account = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/account/";
    static String url_hardware = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/hardware/";
    static String url_criteria = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/criteria/";
    static String url_notification = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/notification/";
    static String url_saving_policy = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/saving_policy/";
    @Test
    public void addition_isCorrect() {
        System.out.println("starting connection");
        //Receiver_Client client = new Receiver_Client();
        Receiver_Client client = new Receiver_Client();
    }

    class Saving_Policy_Configuration {
        int max_time;
        String resolution_name;
        int saving_policy_id;
        List<Integer> hardware;

        public Saving_Policy saving_policy(ArrayList<Camera> cameras){
            ArrayList<Camera> my_cameras = new ArrayList<>();
            for(int i = 0; i < hardware.size(); i++){
                boolean got_camera = false;
                for(int j = 0; j < cameras.size(); j++){
                    if(cameras.get(j).id == hardware.get(i).intValue()){
                        my_cameras.add(cameras.get(j));
                        got_camera = true;
                        break;
                    }
                }
                if(!got_camera){
                    throw new RuntimeException();
                }
            }

            return new Saving_Policy(my_cameras, max_time, Resolution.name_to_resolution(resolution_name));
        }

        public ArrayList<Saving_Policy> convert(List<Saving_Policy_Configuration> old, ArrayList<Camera> cameras){
            ArrayList<Saving_Policy> result = new ArrayList<>();
            for(int i = 0; i < old.size(); i++){
                result.add(old.get(i).saving_policy(cameras));
            }
            return result;
        }
        public String toString(){
            return "max_time: " + max_time + ", resolution_name: " + resolution_name + ", saving_policy_id: " + saving_policy_id + ", cameras: " + hardware;
        }
    }

    class Camera_Configuration {
        String name;
        String max_resolution;
        int hardware_id;
        int account_id;

        public Camera camera(){
            return new Camera(name, Resolution.name_to_resolution(max_resolution), hardware_id);
        }
        public ArrayList<Camera> convert(List<Camera_Configuration> old){
            ArrayList<Camera> result = new ArrayList<>();
            for(int i = 0; i < old.size(); i++){
                result.add(old.get(i).camera());
            }
            return result;
        }
        public String toString(){
            return "name: " + name + ", max_resolution: " + max_resolution + ", camera id, " + hardware_id + ", account: " + account_id;
        }
    }

    class Account_Configuration {
        String username;
        String password;
        int account_id;

        public String toString(){
            return "username: " + username + ", password: " + password + ", account_id: " + account_id;
        }
    }

    class Notification_Configuration {
        int notification_type;
        int criteria_type;
        int notification_id;
        List<Integer> hardware;
        public String toString(){
            return "notification_type: " + notification_type + ", criteria_type: " + criteria_type + ", saving_policy_id: " + notification_id + ", cameras: " + hardware;
        }

        public Notification_Policy notification_policy(ArrayList<Camera> cameras){
            ArrayList<Camera> my_cameras = new ArrayList<>();
            for(int i = 0; i < hardware.size(); i++){
                boolean got_camera = false;
                for(int j = 0; j < cameras.size(); j++){
                    if(cameras.get(j).id == hardware.get(i).intValue()){
                        my_cameras.add(cameras.get(j));
                        got_camera = true;
                        break;
                    }
                }
                if(!got_camera){
                    throw new RuntimeException();
                }
            }

            return null;//new Notification_Policy();
        }

        public ArrayList<Saving_Policy> convert(List<Saving_Policy_Configuration> old, ArrayList<Camera> cameras){
            ArrayList<Saving_Policy> result = new ArrayList<>();
            for(int i = 0; i < old.size(); i++){
                result.add(old.get(i).saving_policy(cameras));
            }
            return result;
        }

        public ArrayList<Criteria> convert(List<Criteria_Configuration> old){
            ArrayList<Criteria> result = new ArrayList<>();
            for(int i = 0; i < old.size(); i++){
                result.add(old.get(i).criteria());
            }
            return result;
        }
    }

    class Criteria_Configuration {
        int criteria_type;
        int magnitude;
        int duration;


        public String toString(){
            return "criteria_type: " + criteria_type + ", magnitude: " + magnitude + ", duration: " + duration;
        }

        public Criteria criteria(){
            return new Criteria(criteria_type, magnitude, duration);
        }
        public ArrayList<Criteria> convert(List<Criteria_Configuration> old){
            ArrayList<Criteria> result = new ArrayList<>();
            for(int i = 0; i < old.size(); i++){
                result.add(old.get(i).criteria());
            }
            return result;
        }
    }
    @Test
    public void test_user_settings_query(){
        List<Account_Configuration> accounts = get_accounts_from_database();
        for(int i = 0; i < accounts.size(); i++)
            System.out.println(accounts.get(i));
        List<Notification_Configuration> notifications = get_notification_from_database();
        for(int i = 0; i < notifications.size(); i++)
            System.out.println(notifications.get(i));
        List<Saving_Policy_Configuration> saving_policies = get_saving_policy_from_database();
        for(int i = 0; i < saving_policies.size(); i++)
            System.out.println(saving_policies.get(i));
        List<Criteria_Configuration> criterias = get_criterias_from_database();
        for(int i = 0; i < criterias.size(); i++)
            System.out.println(criterias.get(i));
        List<Camera_Configuration> cameras = get_cameras_from_database();
        for(int i = 0; i < cameras.size(); i++)
            System.out.println(cameras.get(i));
    }

    @Test
    public void test_query(){
        String result = get_string_from_database(url_notification);
        System.out.println(result);
    }


    public List<Account_Configuration> get_accounts_from_database(){
        String result = null;
        try{
            result = sendGET(url_account, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("result: "+result);
        JsonObject json;
        List<Account_Configuration> parsedJson;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Account_Configuration>>() {}.getType();
            parsedJson = gson.fromJson(result, listType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parsedJson;
    }

    public List<Criteria_Configuration> get_criterias_from_database(){
        String result = null;
        try{
            result = sendGET(url_criteria, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("result: "+result);
        JsonObject json;
        List<Criteria_Configuration> parsedJson;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Criteria_Configuration>>() {}.getType();
            parsedJson = gson.fromJson(result, listType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parsedJson;
    }

    public List<Saving_Policy_Configuration> get_saving_policy_from_database(){
        String result = null;
        try{
            result = sendGET(url_saving_policy, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("result: "+result);
        JsonObject json;
        List<Saving_Policy_Configuration> parsedJson;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Saving_Policy_Configuration>>() {}.getType();
            parsedJson = gson.fromJson(result, listType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parsedJson;
    }

    public List<Camera_Configuration> get_cameras_from_database(){
        String result = null;
        try{
            result = sendGET(url_hardware, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("result: "+result);
        JsonObject json;
        List<Camera_Configuration> parsedJson;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Camera_Configuration>>() {}.getType();
            parsedJson = gson.fromJson(result, listType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parsedJson;
    }

    public List<Notification_Configuration> get_notification_from_database(){
        String result = null;
        try{
            result = sendGET(url_notification, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("result: "+result);
        JsonObject json;
        List<Notification_Configuration> parsedJson;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Notification_Configuration>>() {}.getType();
            parsedJson = gson.fromJson(result, listType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        System.out.println("json object: " + parsedJson);
//        for(int i = 0; i < parsedJson.size(); i++){
//            System.out.println(parsedJson.get(i));
//        }
        return parsedJson;
    }

    public String get_string_from_database(String url){
        String result = null;
        try{
            result = sendGET(url, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("result: "+result);
        return result;
    }

    private static String sendGET(String url, String parameter) throws IOException {
        String USER_AGENT = parameter;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        //System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            System.out.println("GET request did not work.");
        }
        return "no data";
    }
}