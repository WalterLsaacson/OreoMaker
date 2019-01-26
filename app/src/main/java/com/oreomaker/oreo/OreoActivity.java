package com.oreomaker.oreo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.oreomaker.R;
import com.oreomaker.oreo.fragments.ControlFragment;
import com.oreomaker.oreo.fragments.DisplayFragment;
import com.p.oreoview.PieceProperty;

import java.util.ArrayList;
import java.util.Objects;

/*
 *** Double fragments : one for choice, other one for display oreo view
 * https://ddiu8081.github.io/oreooo/
 */
public class OreoActivity extends AppCompatActivity implements ControlFragment.CreateOreo, DisplayFragment.ResetOreo {

    ControlFragment controlFragment;
    DisplayFragment displayFragment;

    FragmentManager mFragmentManager;

    ArrayList<PieceProperty> piecePropertyArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oreo);
        //create view model
        piecePropertyArrayList = new ArrayList<>();
        ViewModelProviders.of(Objects.requireNonNull(this))
                .get(OreoViewModel.class).updatePieceList(piecePropertyArrayList);
        //add create view interface
        controlFragment = new ControlFragment();
        controlFragment.setCreateOreo(this);

        displayFragment = new DisplayFragment();
        //fragment transaction
        mFragmentManager = getSupportFragmentManager();
        //when replace method has been invoked will create new instance
        //this will cause oom
        //TODO need other fun to implements transaction
        //add,hide,show methods will improve this issue
        mFragmentManager.beginTransaction().add(R.id.oreo_container, controlFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.oreo_container, displayFragment).commit();
    }

    @Override
    public void createOreo() {
        doTransaction(false);
    }

    @Override
    public void resetData() {
        doTransaction(true);
    }

    /*
     *** generate a new oreo or re construct oreo
     */
    private void doTransaction(boolean create) {
        if (create) {
            mFragmentManager.beginTransaction().hide(displayFragment).show(controlFragment).commit();
        } else {
            mFragmentManager.beginTransaction().hide(controlFragment).show(displayFragment).commit();
        }
    }

}
