package com.p.oreoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

import com.p.oreo.view.R;


/*
 ***Hold item views based on adapter.
 *
 */
public class OreoView extends ListView {

    private LayoutParams mAoParams;
    private LayoutParams mLiParams;

    public OreoView(Context context) {
        this(context, null);
    }

    public OreoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams();
        addPieces(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void initParams() {
        mAoParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //set gap between pieces
        //mAoParams.setMargins();
        mLiParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void addPieces(Context context) {
        //add top piece
        AoSlice aoSlice = new AoSlice(context);
        aoSlice.setPasteUpperPiece(true);
        aoSlice.setTopPiece(true);
        aoSlice.setFloorColor(R.color.top_floor_light_color);
        mAoParams.height = 200;
        aoSlice.setLayoutParams(mAoParams);
        addView(aoSlice);
        //add middle piece
    }
}
