package com.p.oreoview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.p.oreo.view.R;

import java.util.logging.Level;

abstract class SlicesViewBase extends AppCompatImageView {

    static final float OVAL_RATIO = 0.8f;
    //is paste upper piece
    private boolean mPasteUpperPiece;
    private float mHeight;
    private float mWidth;
    //custom floor color
    private Paint mFloorPaint;

    public SlicesViewBase(Context context) {
        this(context, null);
    }

    public SlicesViewBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlicesViewBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints() {
        mFloorPaint = new Paint();
        mFloorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFloorPaint.setColor(getContext().getColor(R.color.gap_floor_color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mHeight = getHeight();
        mWidth = getWidth();

        mWidth = mWidth * getZoomRatio();
        mHeight = mHeight * getZoomRatio();

        //will draw floor if this piece separate from upper piece
        if (!mPasteUpperPiece) {

            drawFloor(canvas);
        }
    }

    /**
     * set floor color
     *
     * @param floorColor target color
     */
    public void setFloorColor(int floorColor) {
        mFloorPaint.setColor(floorColor);
    }

    /**
     * if paste upper piece will draw colorful floor
     *
     * @param pasteUpperPiece boolean value
     */
    public void setPasteUpperPiece(boolean pasteUpperPiece) {
        mPasteUpperPiece = pasteUpperPiece;
    }

    /*
     ***draw floor for this piece.
     */
    protected void drawFloor(Canvas canvas) {
        //Zoom padding caused by aspect ratio
        //default width > height
        //right = (w-h)/2 + h = (w + h) / 2
        //bottom = hr + h * (1 -r ) / 2 = h * (r + 1) / 2
        float right = (mWidth + mHeight) / 2;
        float bottom = mHeight * (1 + OVAL_RATIO) / 2;
        if (mWidth < mHeight) {
        }

        RectF rectF = new RectF();
        //Scale padding caused by overall scale
        //these two values are scaled values, so use this method to calculate
        float scalePaddingLeft = mWidth * (1 - getZoomRatio()) / 2;
        float scalePaddingTop = mHeight * (1 - getZoomRatio()) / 2;
        rectF.set(getPaddingLeft() + scalePaddingLeft + (mWidth - right) / 2,
                getPaddingTop() + scalePaddingTop + (mHeight - bottom) / 2,
                right + scalePaddingLeft,
                bottom + getPaddingTop() + scalePaddingTop);
        canvas.drawOval(rectF, mFloorPaint);
    }

    protected float getZoomRatio() {
        return 1.0f;
    }

}
