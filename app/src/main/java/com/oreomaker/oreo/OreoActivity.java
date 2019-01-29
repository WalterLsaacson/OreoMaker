package com.oreomaker.oreo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.oreomaker.R;
import com.oreomaker.oreo.fragments.ControlFragment;
import com.oreomaker.oreo.fragments.DisplayFragment;
import com.p.oreoview.PieceProperty;

import java.util.ArrayList;

/*
 *** Double fragments : one for choice, other one for display oreo view
 * https://ddiu8081.github.io/oreooo/
 */
public class OreoActivity extends AppCompatActivity implements ControlFragment.CreateOreo, DisplayFragment.ResetOreo {

    private static final String TAG = "OreoActivity";

    public static final int EXIT_SIGNAL = 0;

    ControlFragment controlFragment;
    DisplayFragment displayFragment;

    FragmentManager mFragmentManager;

    ArrayList<PieceProperty> mOreoList;

    ExitHandler mExitHandler;

    //we need to know which fragment is currently displayed
    boolean isDisplayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oreo);
        mExitHandler = new ExitHandler(getMainLooper());
        //create view model
        OreoViewModel oreoViewModel = ViewModelProviders.of(this)
                .get(OreoViewModel.class);
        oreoViewModel.updatePieceList(new ArrayList<PieceProperty>());
        mOreoList = oreoViewModel.getPieceList();
        //add create view interface
        controlFragment = new ControlFragment();
        controlFragment.setCreateOreo(this);

        displayFragment = new DisplayFragment();
        displayFragment.setResetOreo(this);

        //fragment transaction
        mFragmentManager = getSupportFragmentManager();
        doTransaction(false);
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
        mOreoList.clear();
        doTransaction(true);
    }

    @Override
    public void onBackPressed() {
        if (isDisplayFragment) {
            resetData();
        } else {
            if (mExitHandler.hasMessages(EXIT_SIGNAL)) super.onBackPressed();
            else {
                mExitHandler.sendEmptyMessageDelayed(EXIT_SIGNAL, 4000);
                Toast.makeText(this, "再按一次退出。", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     *** generate a new oreo or re construct oreo
     */
    private void doTransaction(boolean create) {
        isDisplayFragment = !create;
        if (create) {
            mFragmentManager.beginTransaction().hide(displayFragment).show(controlFragment).commit();
        } else {
            mFragmentManager.beginTransaction().hide(controlFragment).show(displayFragment).commit();
        }
    }

    class ExitHandler extends Handler {
        ExitHandler(Looper mainLooper) {
            super(mainLooper);
        }
    }

}
