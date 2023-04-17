package com.example.layout_version;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class VideoViewOpencv extends View {
    private Bitmap mBitmap;

    public VideoViewOpencv(Context context) {
        super(context);
    }

    public VideoViewOpencv(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoViewOpencv(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }
}