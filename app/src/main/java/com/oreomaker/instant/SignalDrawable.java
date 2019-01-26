package com.oreomaker.instant;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.graphics.Path.FillType;

public class SignalDrawable extends Drawable {

    final String TAG = "instant";

    final float PAINT_WIDTH = 5.0f;
    final int COUNT = 5;
    final float MIN_SIGNAL_HEIGHT = 20.0f;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path mPath = new Path();

    @Override
    public void draw(Canvas canvas) {

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(PAINT_WIDTH);
        mPaint.setColor(Color.BLACK);

        mPath.reset();
        mPath.setFillType(FillType.WINDING);

        int width = getBounds().width();
        int height = getBounds().height();
        Log.d(TAG, "width : " + width + "height : " + height);

        float width_offset = (width - COUNT * PAINT_WIDTH) / (COUNT - 1);
        float height_offset = (height - MIN_SIGNAL_HEIGHT) / (COUNT - 1);

        Log.d(TAG, "width_offset : " + width_offset + "height_offset : " + height_offset);

        for (int i = 0; i < COUNT; i++) {
            if (i >= 3){
                mPaint.setAlpha(126);
            }
            Log.d(TAG, "draw: " + i);
            canvas.drawLine(PAINT_WIDTH / 2 + i * width_offset, height,
                    PAINT_WIDTH / 2 + i * width_offset, height - MIN_SIGNAL_HEIGHT - i * height_offset,
                    mPaint);
        }
        /*mPath.moveTo(40, 45);// 此点为多边形的起点
        mPath.lineTo(60, 60);
        mPath.lineTo(40, 60);*/
        mPath.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(mPath, mPaint);


    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
