package com.example.layout_version.Library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layout_version.Account.Account_Page;
import com.example.layout_version.Library_Videos_Page;
import com.example.layout_version.MainActivity;
import com.example.layout_version.R;
import com.example.layout_version.Settings;

public class Library extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);

        ImageView btn;
        Button view;
        ImageView account;

        RecyclerView recyclerView = findViewById(R.id.videoRecyclerView);
        btn = findViewById(R.id.settings);
        account = findViewById(R.id.account);
        view = findViewById(R.id.view);


        String[] descriptions = {"Test1", "Test2", "Test3", "Test4"};

        VideoAdapter adapter = new VideoAdapter(descriptions);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Library.this);
        recyclerView.setLayoutManager(layoutManager);

        account.setOnClickListener(view13 -> {
            startActivity(new Intent (Library.this, Account_Page.class));
        });

        btn.setOnClickListener(view1 -> {
            startActivity(new Intent (Library.this, Settings.class));
        });

        view.setOnClickListener(view12 -> {
            startActivity( new Intent (Library.this, MainActivity.class));
        });




    }

}
