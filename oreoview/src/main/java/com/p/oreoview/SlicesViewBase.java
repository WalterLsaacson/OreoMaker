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

    protected PieceProperty mProperty;
    static final float OVAL_RATIO = 0.8f;
    protected float mHeight;
    protected float mWidth;
    //custom floor color
    //todo why dont use same paint(change color)?
    protected Paint mFloorPaint;
    protected Paint mBorderPaint;
    protected Paint mArcSidePaint;

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

    //TODO bad code
    public void setProperty(PieceProperty property) {
        this.mProperty = property;
    }

    private void initPaints() {
        mFloorPaint = new Paint();
        mFloorPaint.setStyle(Paint.Style.FILL);
        mFloorPaint.setColor(getFloorColor());

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.FILL);
        mBorderPaint.setColor(getBorderColor());

        mArcSidePaint = new Paint();
        mArcSidePaint.setStyle(Paint.Style.FILL);
        mArcSidePaint.setColor(getArcSideColor());
    }

    protected abstract int getFloorColor();

    protected abstract int getArcSideColor();

    protected abstract int getBorderColor();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mHeight = getHeight();
        mWidth = getWidth();

        //parameter check
        float zoomRatio = getZoomRatio();
        if (zoomRatio < 0.1f || zoomRatio > 1.0f) {
            throw new IllegalArgumentException("Zoom ratio must between [0.1,1.0f]");
        }
        mWidth = mWidth * zoomRatio;
        mHeight = mHeight * zoomRatio;

        //will draw floor if this piece separate from upper piece
        if (!mProperty.isPasteUpper() || mProperty.isTopSlice()) {
            drawFloor(canvas);
        }
        drawArcSide(canvas);
        drawBorder(canvas);
    }

    protected void drawBorder(Canvas canvas) {

    }

    protected void drawArcSide(Canvas canvas) {

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

    /*
     *** Override this method to scale piece
     * must between [0.1,1.0]
     * maybe more bigger or smaller
     *
     * @return Scaling ratio
     */
    protected float getZoomRatio() {
        return 1.0f;
    }

}
