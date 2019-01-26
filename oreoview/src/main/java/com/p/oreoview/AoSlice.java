package com.p.oreoview;

import android.content.Context;
import android.util.AttributeSet;

import java.util.jar.Attributes;

class AoSlice extends SlicesViewBase {



    private boolean isTopPiece;

    public AoSlice(Context context) {
        this(context, null);
    }

    public AoSlice(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTopPiece(boolean isTopPiece) {
        this.isTopPiece = isTopPiece;
    }
}
