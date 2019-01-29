package com.p.oreoview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.p.oreo.view.R;

class LiSlice extends SlicesViewBase {


    /*
     *** width will be zoom out based on this ratio.
     */
    public static final float WIDTH_RATIO = 0.8f;

    public LiSlice(Context context) {
        this(context, null);
    }

    public LiSlice(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getFloorColor() {
        return getResources().getColor(R.color.li_slice_floor_color, null);

    }

    @Override
    protected int getArcSideColor() {
        return getResources().getColor(R.color.li_slice_arc_color, null);

    }

    @Override
    protected int getBorderColor() {
        return getResources().getColor(R.color.li_slice_border_color, null);
    }

    @Override
    public float getZoomRatio() {
        return WIDTH_RATIO;
    }
}
