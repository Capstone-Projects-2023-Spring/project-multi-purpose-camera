package com.example.layout_version;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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

public class Database_Manager {

    static String url_account = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/";
    static String url_hardware = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/hardware/";
    static String url_criteria = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/criteria/";
    static String url_notification = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/notification/";
    static String url_saving_policy = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/saving_policy/";

    public static final int pass = 200;
    public static final int error = 500;

    //region data_classes
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
            Resolution resolution = Resolution.name_to_resolution(resolution_name);

            return new Saving_Policy(my_cameras, max_time, resolution, saving_policy_id);
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
            return new Camera(name, Resolution.name_to_resolution(max_resolution), hardware_id, account_id);
        }
        public String toString(){
            return "name: " + name + ", max_resolution: " + max_resolution + ", camera id, " + hardware_id + ", account: " + account_id;
        }
    }
    class Account_Configuration {
        String username;
        String password;
        String email;
        String status;
        String token;
        String timestamp;
        int account_id;

        public String toString(){
            return "username: " + username + ", password: " + password + ", account_id: " + account_id + ", email: " + email + "\n"+
                    "status: " + status + ", token: " + token + ", timestamp: " + timestamp;
        }
    }
    class Notification_Configuration {
        int notification_type;
        int criteria_id;
        int notification_id;
        List<Integer> hardware;
        public String toString(){
            return "notification_type: " + notification_type + ", criteria_id: " + criteria_id + ", id: " + notification_id + ", cameras: " + hardware;
        }

        public Notification_Policy notification_policy(ArrayList<Camera> cameras, ArrayList<Criteria> criterias){
            //System.out.println("trying to convert to notification: ");
            //System.out.println("criteria id: " + criteria_id);
//            for(int i = 0; i < criterias.size(); i++){
//                System.out.println(criterias.get(i).id);
//            }
            ArrayList<Camera> my_cameras = new ArrayList<>();
            Criteria my_criteria = null;
            for(int i = 0; i < hardware.size(); i++){
                for(int j = 0; j < cameras.size(); j++){
                    if(cameras.get(j).id == hardware.get(i).intValue()){
                        my_cameras.add(cameras.get(j));
                        break;
                    }
                }
            }
            for(int j = 0; j < criterias.size(); j++){
                if(criterias.get(j).id == criteria_id){
                    my_criteria = criterias.get(j);
                    break;
                }
            }
            if(hardware.size() < my_cameras.size()){
                System.out.println("did not get camera for notification");
                throw new RuntimeException();
            }
            //System.out.println("checking if we have criteria");
            if(my_criteria == null){
                System.out.println("did not get criteria for notification");
                throw new RuntimeException();
            }

            return new Notification_Policy(my_criteria, my_cameras, notification_type, notification_id);
        }


    }
    class Criteria_Configuration {
        int criteria_type;
        int magnitude;
        int duration;
        int criteria_id;


        public String toString(){
            return "criteria_type: " + criteria_type + ", magnitude: " + magnitude + ", duration: " + duration + ", id: " + criteria_id;
        }

        public Criteria criteria(){
            return new Criteria(criteria_type, magnitude, duration, criteria_id);
        }

    }
    public static ArrayList<Camera> convert_cameras(List<Camera_Configuration> old){
        ArrayList<Camera> result = new ArrayList<>();
        for(int i = 0; i < old.size(); i++){
            result.add(old.get(i).camera());
        }
        return result;
    }
    public static ArrayList<Saving_Policy> convert_saving_policies(List<Saving_Policy_Configuration> old, ArrayList<Camera> cameras){
        ArrayList<Saving_Policy> result = new ArrayList<>();
        for(int i = 0; i < old.size(); i++){
            result.add(old.get(i).saving_policy(cameras));
        }
        return result;
    }
    public static ArrayList<Notification_Policy> convert_noitification_policies(List<Notification_Configuration> old, ArrayList<Camera> cameras, ArrayList<Criteria> criterias){
        ArrayList<Notification_Policy> result = new ArrayList<>();
        for(int i = 0; i < old.size(); i++){
            result.add(old.get(i).notification_policy(cameras, criterias));
        }
        return result;
    }
    public static ArrayList<Criteria> convert_criterias(List<Criteria_Configuration> old){
        ArrayList<Criteria> result = new ArrayList<>();
        for(int i = 0; i < old.size(); i++){
            result.add(old.get(i).criteria());
        }
        return result;
    }
    //endregion

    public static boolean do_post(String url_input){
        String urlString = url_input;
        String postData = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(postData);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = urlConnection.getResponseCode();
            urlConnection.disconnect();
            if(responseCode == pass)
                return true;
            return false;

        } catch (IOException e) {
            Log.e("HttpPostTask", "Error: " + e.getMessage(), e);
            return false;
        }
    }



    public static boolean do_put(String url_string){

        String urlString = url_string;
        String postData = "";

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(postData);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = urlConnection.getResponseCode();
            urlConnection.disconnect();

            if(responseCode == pass)
                return true;
            return false;
        } catch (IOException e) {
            Log.e("HttpPostTask", "Error: " + e.getMessage(), e);
            return false;
        }

    }

    public static boolean do_delete(String url_string){

        String urlString = url_string;
        String postData = "";

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("DELETE");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(postData);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = urlConnection.getResponseCode();
            urlConnection.disconnect();

            if(responseCode == pass)
                return true;
            return false;
        } catch (IOException e) {
            Log.e("HttpPostTask", "Error: " + e.getMessage(), e);
            return false;
        }

    }

    //region get
    public static List<Account_Configuration> get_accounts_from_database(){
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
    public static List<Criteria_Configuration> get_criterias_from_database(){
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
    public static List<Saving_Policy_Configuration> get_saving_policy_from_database(){
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
    public static List<Camera_Configuration> get_cameras_from_database(/*int accounttoken*/){
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
    public static List<Notification_Configuration> get_notification_from_database(){
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
    public static String get_string_from_database(String url){
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
    //endregion
    public static void get_objects(){
        ArrayList<Account_Configuration> accounts = (ArrayList<Account_Configuration>) get_accounts_from_database();
        ArrayList<Criteria> criterias = convert_criterias(get_criterias_from_database());
        ArrayList<Camera> cameras = convert_cameras((get_cameras_from_database()));
        ArrayList<Saving_Policy> saving_policies = convert_saving_policies(get_saving_policy_from_database(), cameras);
        ArrayList<Notification_Policy> notification_policies = convert_noitification_policies(get_notification_from_database(), cameras, criterias);
    }
    //region edit
    public static void database_add_camera_to_notifcation(int notification_id, int camera_id){
        String test = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/notification/" + notification_id + "/add/" + camera_id;
        do_post(test);
    }
    public static void database_add_camera_to_saving_policy(int saving_policy_id, int camera_id){
        String test = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/saving_policy/" + saving_policy_id + "/add/" + camera_id;
        do_post(test);
    }


    //endregion

    //region add
    public static int add_saving_policy(int time, String resolution){
        List<Saving_Policy_Configuration> old = get_saving_policy_from_database();

        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/saving_policy?max_time="+time+"&resolution_name="+resolution;
        boolean result = do_post(url);
        System.out.println(result);
        if(!result)
            return -1;
        List<Saving_Policy_Configuration> new_list = get_saving_policy_from_database();
        System.out.println("old list: "+ old.size());
        for(int i = 0; i < old.size(); i++){
            System.out.println(old.get(i).saving_policy_id);
        }
        System.out.println("new list: "+ new_list.size());
        for(int i = 0; i < new_list.size(); i++){
            System.out.println(new_list.get(i).saving_policy_id);
        }

        for(int i = 0; i < new_list.size(); i++){
            int id = new_list.get(i).saving_policy_id;
            boolean found_id = false;
            for(int j = 0; j < old.size(); j++){
                if(old.get(j).saving_policy_id == id){
                    found_id = true;
                    break;
                }
            }
            if(!found_id)
                return id;
        }
        return -1;
    }
    public static void add_notification_policy(int type, int criteria_id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/notification?notification_type="+type+"&criteria_id="+criteria_id;
        do_post(url);
    }
    public static void add_camera(String name, String max_resolution){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/hardware?name="+name+"&max_resolution="+max_resolution;
        do_post(url);
    }
    public static void add_criteria(int criteria_type, int duration, int magnitude){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/path3/criteria?criteria_type="+criteria_type+"&duration="+duration+"&magnitude="+magnitude;
        do_post(url);
    }
    //endregion
    public static BackEnd create_BackEnd(){
        int my_account = 15;
        ArrayList<Account_Configuration> accounts = (ArrayList<Account_Configuration>) get_accounts_from_database();
        ArrayList<Criteria> criterias = convert_criterias(get_criterias_from_database());
        ArrayList<Camera> cameras = convert_cameras((get_cameras_from_database()));
        ArrayList<Saving_Policy> saving_policies = convert_saving_policies(get_saving_policy_from_database(), cameras);
        System.out.println(saving_policies);
        ArrayList<Notification_Policy> notification_policies = convert_noitification_policies(get_notification_from_database(), cameras, criterias);
        System.out.println(notification_policies);
        System.out.println(accounts);
        for(int i = 0; i < cameras.size(); i++){
            System.out.println("["+cameras.get(i).name + ", " + cameras.get(i).account_id +"] ");
        }
        //removes cameras that dont belong to account
        for(int i = cameras.size() - 1;  i >= 0; i--){
            if(cameras.get(i).account_id != my_account)
                cameras.remove(i);
        }
        //removes policies that dont have my cameras
        for(int i = saving_policies.size() - 1;  i >= 0; i--){
            boolean my_list = false;
            ArrayList<Camera> cameras_of_saving = saving_policies.get(i).get_cameras();
            for(int j = 0; j < cameras.size(); j++){
                if(cameras_of_saving.contains(cameras.get(j))){
                    my_list = true;
                    break;
                }
            }
            if(!my_list)
                saving_policies.remove(i);
        }
        //remove policies that dont have my camera
        for(int i = notification_policies.size() - 1;  i >= 0; i--){
            boolean my_list = false;
            ArrayList<Camera> cameras_of_policy = notification_policies.get(i).get_cameras();
            for(int j = 0; j < cameras.size(); j++){
                if(cameras_of_policy.contains(cameras.get(j))){
                    my_list = true;
                    break;
                }
            }
            if(!my_list)
                notification_policies.remove(i);
        }
        for(int i = 0; i < cameras.size(); i++){
            System.out.println("["+cameras.get(i).name + ", " + cameras.get(i).account_id +"] ");
        }
        System.out.println(notification_policies);
        System.out.println(saving_policies);

        BackEnd backEnd = new BackEnd(cameras, saving_policies, notification_policies);
        return backEnd;
    }

    public static void test(){
        //database_add_camera_to_saving_policy(6, 12);
        String result = get_string_from_database(url_account);
        List<Account_Configuration> accounts = get_accounts_from_database();
        for(int i = 0; i < accounts.size(); i++){
            System.out.println(accounts.get(i));
        }
        System.out.println(result);
    }

    public static boolean edit_criteria(int type, int duration, int magnitude, int id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/criteria/"+id+"?criteria_type="+type+"&duration="+duration+"&magnitude="+magnitude;
        System.out.println(url);
        boolean result_code = do_put(url);
        System.out.println(result_code);
        return result_code;
    }
    public static boolean edit_saving_policy(int max_time, String resolution_name, int id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/saving_policy/"+id+"?max_time="+max_time+"&resolution_name="+resolution_name;
        System.out.println(url);
        boolean result_code = do_put(url);
        System.out.println(result_code);
        return result_code;
    }
    public static boolean edit_notificaiton_policy(int type, int criteria_id, int id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/notification/"+id+"?notification_type="+type+"&criteria_id="+criteria_id;
        System.out.println(url);
        boolean result_code = do_put(url);
        System.out.println(result_code);
        return result_code;
    }
    public static boolean edit_camera(String new_name, String max_res, int camera_id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/hardware/"+camera_id+"?name="+new_name+"&max_resolution="+max_res;
        System.out.println(url);
        boolean result_code = do_put(url);
        System.out.println(result_code);
        return result_code;
    }

    public static boolean delete_criteria(int id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/criteria/"+id;
        System.out.println(url);
        boolean result_code = do_put(url);
        System.out.println(result_code);
        return result_code;
    }
    public static boolean delete_camera(int id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/hardware/"+id;
        System.out.println(url);
        boolean result_code = do_put(url);
        System.out.println(result_code);
        return result_code;
    }
    public static boolean delete_saving_policy(int id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/saving_policy/"+id;
        System.out.println(url);
        boolean result_code = do_put(url);
        System.out.println(result_code);
        return result_code;
    }
    public static boolean delete_notification_policy(int id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/notification/"+id;
        System.out.println(url);
        boolean result_code = do_put(url);
        System.out.println(result_code);
        return result_code;
    }

    public static boolean saving_policy_remove_camera(int saving_policy_id, int camera_id){
        String url = "https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/saving_policy/"+saving_policy_id+"/hardware/"+camera_id;
        System.out.println(url);
        boolean result_code = do_delete(url);
        System.out.println(result_code);
        return result_code;
    }
}
