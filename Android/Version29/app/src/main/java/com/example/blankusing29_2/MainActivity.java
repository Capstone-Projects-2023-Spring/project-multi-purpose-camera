package com.example.blankusing29_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int visible = View.VISIBLE;
    public static final int invisible = View.INVISIBLE;
    public static int background_color1;
    public static int background_color2;

    public ConstraintLayout main_layout;
    public Camera_Manager cameras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout constraintLayout = findViewById(R.id.main_layout);
        System.out.println("*******************\n\n\n" + constraintLayout);

        background_color1 = Color.DKGRAY;
        background_color2 = Color.GRAY;

        cameras = new Camera_Manager();


        main_layout = new ConstraintLayout(this);
        main_layout.setLayoutParams(createLayoutParams(200, 0, 0, 0, -1, -1 ));
        constraintLayout.addView(main_layout);

        ConstraintLayout camera_layout = construct_camera_layout(main_layout);

        construct_top_navigation_bar(constraintLayout);
    }

    // The Nav Bar
    // ConstraintLayout - Changing the current layout to something else
    public ConstraintLayout construct_top_navigation_bar(ConstraintLayout constraintLayout){
        ConstraintLayout nav_layout = new ConstraintLayout(this);
        nav_layout.setLayoutParams(createLayoutParams(0, -1, 0, 0, -1, 200));
        constraintLayout.addView(nav_layout);

        Button settings_button = new Button(this);
        settings_button.setLayoutParams(createLayoutParams(50, -1, 50, -1, 100, 100));
        settings_button.setAlpha(0.5f);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_layout.removeAllViews();
                construct_settings_layout(main_layout);
            }
        });
        nav_layout.addView(settings_button);

        Button camera_button = new Button(this);
        camera_button.setLayoutParams(createLayoutParams(50, -1, -1, 50, 100, 100));
        camera_button.setAlpha(0.5f);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_layout.removeAllViews();
                construct_camera_layout(main_layout);
            }
        });
        nav_layout.addView(camera_button);


        View top_navigator = fill_rectangle(background_color1);
        top_navigator.setLayoutParams(createLayoutParams(0, -1, 0, 0, -1, 200));
        nav_layout.addView(top_navigator);
        return null;
    }

    // Saving Policy Page (With the red box)
    public ConstraintLayout construct_saving_policy_layout(ConstraintLayout main_layout){
        ConstraintLayout layout = new ConstraintLayout(this);
        main_layout.addView(layout);

        View rect = round_rectangle(Color.RED);
        rect.setLayoutParams(createLayoutParams(50, -1, -1, 50, 400, 200));
        TextView text = new TextView(this);
        text.setLayoutParams(createLayoutParams(70, -1, -1, 60, 400, 200));
        text.setTextColor(Color.BLACK);
        layout.addView(text);

        text.setText(cameras.saving_policies.get(0).get_display_text());

        layout.addView(rect);

        return layout;
    }

    // Settings Page
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
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI == 0){
                        System.out.println("hello!!");
                        main_layout.removeAllViews();
                        construct_saving_policy_layout(main_layout);
                    }
                }
            });
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


    // Camera Layout
    public ConstraintLayout construct_camera_layout(ConstraintLayout main_layout){
        int width = get_width_of_screen();

        int border_color = Color.BLACK;

        ConstraintLayout camera_layout = new ConstraintLayout(this);
        camera_layout.setLayoutParams(createLayoutParams(0, 0, 0, 0, -1, -1));
        main_layout.addView(camera_layout);
        //camera_layout.setVisibility(View.INVISIBLE);
        int camera_vertical_start = 200;
        int camera_width = (int)(width*0.8);
        int camera_height = (int)(camera_width * 0.62);
        int camera_padding = 100;

        View background = fill_rectangle(background_color2);
        background.setLayoutParams(createLayoutParams(0, 0, 0, 0, -1, -1));
        camera_layout.addView(background);

        ScrollView scroll = new ScrollView(this);
        scroll.setLayoutParams(createLayoutParams(camera_vertical_start, 0, 100, 100, -1, -1));
        camera_layout.addView(scroll);

        ConstraintLayout scroll_container = new ConstraintLayout(this);
        scroll.addView(scroll_container);

        ConstraintLayout[] layouts = new ConstraintLayout[4];
        for(int i = 0; i < layouts.length; i++){
            ConstraintLayout constraintLayout_camera1 = new ConstraintLayout(this);
            constraintLayout_camera1.setLayoutParams(createLayoutParams(100 + (camera_height + camera_padding) * i, -1, 0, 0, camera_width, camera_height));
            scroll_container.addView(constraintLayout_camera1);
            layouts[i] = constraintLayout_camera1;
        }

        for(int i = 0; i < layouts.length; i++){
            View border = rectangle(border_color, 15);
            border.setLayoutParams(createLayoutParams(0, 0, 0, 0, -1, -1));
            View fill = fill_rectangle(background_color1);
            fill.setLayoutParams(createLayoutParams(0, 0, 0, 0, -1, -1));
            layouts[i].addView(fill);
            layouts[i].addView(border);

            Button settings = new Button(this);
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            settings.setLayoutParams(createLayoutParams(50, -1, 50, -1, 100, 100));
            layouts[i].addView(settings);
        }
        return camera_layout;
    }

    public View round_rectangle(int color){
        return new View(this){
            @Override
            protected void onDraw(Canvas canvas) {
                float radius = 20.0f;
                Paint paint = new Paint();
                paint.setColor(color);
                paint.setAlpha(128);
                System.out.println("round: " );
                System.out.println(getBottom() - 1 * radius);
                //public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, @NonNull Paint paint)
                canvas.drawRoundRect(0, 0, getRight() - getLeft(), getBottom() - getTop(), radius, radius, paint);
            }

        };
    }

    public View image_view(){
        return new View(this){
            @Override
            protected void onDraw(Canvas canvas) {


            }

        };
    }

    public static Paint paint_fill(int color){
        Paint paint = new Paint();
        paint.setColor(color);
        //paint.setAlpha(128);
        return paint;
    }

    public static Paint paint_stroke(int color, int thickness){
        Paint paint_stroke = new Paint();
        paint_stroke.setStyle(Paint.Style.STROKE);
        paint_stroke.setStrokeWidth(thickness);
        paint_stroke.setColor(color);
        //paint_stroke.setAlpha(128);
        return paint_stroke;
    }

    public View rectangle(int color, int thickness){
        return new View(this){
            @Override
            protected void onDraw(Canvas canvas) {
                int padding = 0;

                System.out.println("bounds: " + canvas.getClipBounds());
                float radius = 20.0f;


                int left = padding + thickness/2;//getLeft() + 50;
                int top = padding + thickness/2;//getTop() + 50;
                int right = getRight() - getLeft() - 2*padding - thickness/2;
                int bottom = getBottom() - getTop() - 2*padding - thickness/2;

                Paint paint_fill_blue = paint_stroke(color, thickness);

                RectF rect_blue = new RectF(left, top, right, bottom);//(int)(getRight()-getLeft()-2*thickness), (int)(getBottom()-getTop()-2*thickness));
                canvas.drawRect(rect_blue, paint_fill_blue);
            }

        };
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



    public int get_width_of_screen(){
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return screenWidth;
    }

    public int get_height_of_scree(){
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return screenHeight;
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