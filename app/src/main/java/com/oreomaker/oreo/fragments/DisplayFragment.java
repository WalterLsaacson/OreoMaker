package com.oreomaker.oreo.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.oreomaker.R;
import com.oreomaker.oreo.OreoViewModel;
import com.oreomaker.oreo.Utils;
import com.p.oreoview.OreoAdapter;
import com.p.oreoview.PieceProperty;

import java.util.ArrayList;
import java.util.Objects;

// TODO back key or button return to control fragment
public class DisplayFragment extends Fragment {

    private static final String TAG = "DisplayFragment";
    ArrayList<PieceProperty> mPieceList;
    ResetOreo mResetOreo;
    ListView mOreoView;
    OreoAdapter mOreoAdapter;

    TextView mContentDes;

    public void setResetOreo(ResetOreo mResetOreo) {
        this.mResetOreo = mResetOreo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //onPaused
        } else {
            //onResume
            mContentDes.setText(Utils.getDescription(mPieceList));
            mOreoAdapter.notifyDataSetChanged();
        }
    }

    private void initView(View view) {
        mContentDes = view.findViewById(R.id.gen_des);
        mOreoView = view.findViewById(R.id.oreo_display);
        mOreoView.setAdapter(mOreoAdapter);
        view.findViewById(R.id.reset_data).setOnClickListener((v) -> mResetOreo.resetData());
    }

    private void initData() {
        mPieceList = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(OreoViewModel.class).getPieceList();
        mOreoAdapter = new OreoAdapter(mPieceList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_oreo, container, false);
        initView(view);
        return view;
    }


    public interface ResetOreo {
        void resetData();
    }
}
