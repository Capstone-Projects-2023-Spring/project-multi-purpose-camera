package com.example.layout_version;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

import static com.example.layout_version.Database_Manager.*;

import android.accounts.Account;

public class ExampleUnitTest {



    @Test
    public void addition_isCorrect() {
        System.out.println("starting connection");
        //Receiver_Client client = new Receiver_Client();
        Receiver_Client.custom_run();
        //client.execute(new Object[1]);
    }

    @Test
    public void test_saving_policy_duplicate_checker(){
        ArrayList<Saving_Policy> policies = new ArrayList<>();
        Saving_Policy_Page.is_policies_valid(policies);
    }


}