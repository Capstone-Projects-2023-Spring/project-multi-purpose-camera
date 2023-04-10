package com.example.layout_version;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


/**
 * The class Video view Opencv extends view. OpenCV helps display the video on the app.
 */
public class VideoViewOpencv extends View {
    private Bitmap mBitmap;


    /**
     *
     * Video view opencv
     *
     * @param context  the context.
     */
    public VideoViewOpencv(Context context) {

        super(context);
    }


    /**
     *
     * Video view opencv
     *
     * @param context  the context.
     * @param attrs  the attrs.
     */
    public VideoViewOpencv(Context context, AttributeSet attrs) {

        super(context, attrs);
    }


    /**
     *
     * Video view opencv
     *
     * @param context  the context.
     * @param attrs  the attrs.
     * @param defStyle  the def style.
     */
    public VideoViewOpencv(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }


    @Override

/**
 *
 * On draw
 *
 * @param canvas  the canvas.
 */
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }
}
