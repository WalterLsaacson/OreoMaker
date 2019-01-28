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

    public OreoView(Context context) {
        this(context, null);
    }

    public OreoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
