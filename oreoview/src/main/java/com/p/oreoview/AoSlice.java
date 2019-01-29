package com.p.oreoview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.p.oreo.view.R;

import java.util.jar.Attributes;

class AoSlice extends SlicesViewBase {


    public AoSlice(Context context) {
        this(context, null);
    }

    public AoSlice(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getFloorColor() {
        return getResources().getColor(R.color.ao_slice_floor_color, null);
    }

    @Override
    protected int getArcSideColor() {
        return getResources().getColor(R.color.ao_slice_arc_color, null);
    }

    @Override
    protected int getBorderColor() {
        return getResources().getColor(R.color.ao_slice_border_color, null);

    }

}
