package com.example.layout_version.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.layout_version.Account.Account;
import com.example.layout_version.Account.Account_Page;
import com.example.layout_version.Account.Account_Page_Profile;
import com.example.layout_version.Bluetooth_Page;
import com.example.layout_version.MainActivity;
import com.example.layout_version.R;


public class Settings extends AppCompatActivity {

    public static int background_color1;
    public static int background_color2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        ImageView back_home_im;
        TextView back_home_txt;

        back_home_im = (ImageView) findViewById(R.id.back_home_btn_setting);
        back_home_txt = (TextView) findViewById(R.id.back_home_text_setting);

        TextView saving_policy = (TextView) findViewById(R.id.cloud_saving_settings);
        TextView bluetooth = (TextView) findViewById(R.id.bluetooth_settings);

        LinearLayout accountLayout = findViewById(R.id.account_layout);

        accountLayout.setOnClickListener(view -> {
            Account account = Account.getInstance();
            Intent intent;
            if(account.isSignedIn())
                intent = new Intent (Settings.this, Account_Page_Profile.class);
            else
                intent = new Intent (Settings.this, Account_Page.class);

            startActivity(intent);
        });

        saving_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Settings.this, Saving_Policy_Page.class);
                startActivity(intent);
            }
        });

        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Settings.this, Bluetooth_Page.class);
                startActivity(intent);
            }
        });

        back_home_im.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });

        back_home_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Settings.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }



    public ConstraintLayout construct_settings_layout(ConstraintLayout main_layout){
        ConstraintLayout settings_layout = new ConstraintLayout(this);
        main_layout.addView(settings_layout);

        int space_between_settings = 10;
        int setting_height = 200;
        int height_factor = 200+10;

        View background = fill_rectangle(background_color2);
        background.setLayoutParams(createLayoutParams(0, 0, 0, 0, -1, -1));
        settings_layout.addView(background);

        String[] setting_texts = {
                "saving",
                "notifications",
                "setting 3",
                "setting 4"
        };

        for(int i = 0; i < 4; i++){
            int finalI = i;
            ConstraintLayout setting = new ConstraintLayout(this);

            setting.setLayoutParams(createLayoutParams(height_factor*i, -1, 0, 0, -1, 200));
            settings_layout.addView(setting);

            View rectangle = fill_rectangle(background_color1);
            setting.addView(rectangle);

            TextView text = new TextView(this);
            text.setText(setting_texts[i]);
            text.setTextSize(5, 5);
            setting.addView(text);

        }

        return settings_layout;
    }

    public View fill_rectangle(int color){
        return new View(this){
            @Override
            protected void onDraw(Canvas canvas) {
                int padding = 0;

                System.out.println("bounds: " + canvas.getClipBounds());
                float radius = 20.0f;


                int left = padding;//getLeft() + 50;
                int top = padding;//getTop() + 50;
                int right = getRight() - getLeft() - 2*padding;
                int bottom = getBottom() - getTop() - 2*padding;

                Paint paint_fill_blue = paint_fill(color);

                RectF rect_blue = new RectF(left, top, right, bottom);//(int)(getRight()-getLeft()-2*thickness), (int)(getBottom()-getTop()-2*thickness));
                canvas.drawRect(rect_blue, paint_fill_blue);}

        };
    }

    public static Paint paint_fill(int color){
        Paint paint = new Paint();
        paint.setColor(color);
        //paint.setAlpha(128);
        return paint;
    }

    public static ConstraintLayout.LayoutParams createLayoutParams(
            int top, int  bottom, int right, int left,
            int width, int height
    ){
        // Create a new ConstraintLayout.LayoutParams object
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );

        // Set the view's layout parameters

        // Set the constraints for the view
        if(left != -1)
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        if(top != -1)
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        if(right != -1)
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        if(bottom != -1)
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;

        // Set the view's margin
        int margin = 16; // in pixels
        layoutParams.setMargins(left, top, right, bottom);
        layoutParams.width = width;
        layoutParams.height = height;
        return layoutParams;
    }

}
