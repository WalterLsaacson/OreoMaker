package com.p.oreoview;

import android.content.Context;
import android.util.AttributeSet;

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
    public float getZoomRatio() {
        return WIDTH_RATIO;
    }
}
