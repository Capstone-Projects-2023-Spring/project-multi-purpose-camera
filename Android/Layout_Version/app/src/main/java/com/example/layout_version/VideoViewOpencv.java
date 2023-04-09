package com.example.layout_version;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import org.opencv.android.Utils;
import org.opencv.core.Mat;


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
     * @return public
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
     * @return public
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
     * @return public
     */
    public VideoViewOpencv(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }


    /**
     *
     * Sets the image
     *
     * @param mat  the mat.
     */
    public void setImage(Mat mat) {

        if (mBitmap == null || mBitmap.getWidth() != mat.width() || mBitmap.getHeight() != mat.height()) {
            mBitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
        }
        Utils.matToBitmap(mat, mBitmap);
        postInvalidate();
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
