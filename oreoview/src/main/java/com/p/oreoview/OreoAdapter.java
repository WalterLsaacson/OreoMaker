package com.p.oreoview;

import android.database.DataSetObserver;
import android.mtp.MtpObjectInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.p.oreo.view.R;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class OreoAdapter extends BaseAdapter {

    private ArrayList<PieceProperty> mPieceList;

    public OreoAdapter(ArrayList<PieceProperty> pieceList) {
        mPieceList = pieceList;
        handleProperties();
    }

    private void handleProperties() {
        for (int i = 0; i < mPieceList.size(); i++) {
            if (i == 0) mPieceList.get(i).setTopSlice(true);
            if (mPieceList.get(i).getType() == Const.AND_SLICE) {
                //TODO optimize out of index
                //The current practice is to ensure that the added logic is not empty afterwards
                mPieceList.get(i + 1).setPasteUpper(false);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        }
        PieceProperty pieceProperty = mPieceList.get(position);
        /*if (null != convertView.getTag()) {
            ((SlicesViewBase) convertView.getTag()).setProperty(pieceProperty);
            return convertView;
        }*/
        switch (getItemViewType(position)) {
            case Const.AO_SLICE:
                convertView = LayoutInflater.from(convertView.getContext()).inflate(R.layout.ao_slice_item, parent, false);
                ((AoSlice) convertView).setProperty(pieceProperty);
                break;
            case Const.LI_SLICE:
                convertView = new LiSlice(convertView.getContext());
                ((LiSlice) convertView).setProperty(pieceProperty);
                break;
            case Const.AND_SLICE:
                convertView = new LiSlice(convertView.getContext());
                ((LiSlice) convertView).setProperty(pieceProperty);
                break;
            default:
                throw new RuntimeException("Unknown piece type.");
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mPieceList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPieceList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return mPieceList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
