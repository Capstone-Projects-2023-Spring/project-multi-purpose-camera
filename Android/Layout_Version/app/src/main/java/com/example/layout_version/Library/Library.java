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
        btn = (ImageView) findViewById(R.id.settings);
        account = (ImageView) findViewById(R.id.account);
        view = (Button) findViewById(R.id.view);


        String[] descriptions = {"Test1", "Test2", "Test3", "Test4"};

        VideoAdapter adapter = new VideoAdapter(descriptions);
        recyclerView.setAdapter(adapter);

        account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Library.this, Account_Page.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Library.this, Settings.class);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Library.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }

}
