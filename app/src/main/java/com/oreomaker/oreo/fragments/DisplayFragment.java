package com.oreomaker.oreo.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oreomaker.oreo.OreoViewModel;

import java.util.Objects;

// TODO back key or button return to control fragment
public class DisplayFragment extends Fragment {

    OreoViewModel mOreoViewModel;
    ResetOreo resetOreo;

    public void setResetOreo(ResetOreo resetOreo) {
        this.resetOreo = resetOreo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        mOreoViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(OreoViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface ResetOreo {
        void resetData();
    }
}
