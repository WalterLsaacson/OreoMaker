package com.oreomaker.oreo.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oreomaker.R;
import com.oreomaker.oreo.OreoViewModel;
import com.p.oreoview.Const;
import com.p.oreoview.PieceProperty;

import java.util.Objects;

public class ControlFragment extends Fragment implements View.OnClickListener {

    private static final int MSG_UPDATE_STUFFING = 0;

    OreoViewModel mOreoViewModel;
    CreateOreo mCreateOreo;
    Handler mHandler;
    TextView oreoStuffingDes;

    public void setCreateOreo(CreateOreo createOreo) {
        mCreateOreo = createOreo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.control_oreo, container, false);
        initView(view);
        return view;
    }

    private void initView(View contentView) {
        //make tips
        contentView.findViewById(R.id.tips).setOnClickListener(this);
        contentView.findViewById(R.id.add_ao).setOnClickListener(this);
        contentView.findViewById(R.id.add_li).setOnClickListener(this);
        contentView.findViewById(R.id.add_slice).setOnClickListener(this);
        contentView.findViewById(R.id.sub_piece).setOnClickListener(this);
        contentView.findViewById(R.id.random_oreo).setOnClickListener(this);
        contentView.findViewById(R.id.maker).setOnClickListener(this);
        oreoStuffingDes = contentView.findViewById(R.id.stuffing_des);
    }

    private void initData() {
        mOreoViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(OreoViewModel.class);
        mHandler = new RefreshHandler(getActivity().getMainLooper());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tips:
                //in my option toast is better than alert dialog
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                TextView textView = new TextView(getActivity());
                textView.setText("用用脑子");
                textView.setBackgroundColor(getResources().getColor(R.color.card_background));
                textView.setHeight(120);
                textView.setWidth(120);
                textView.setTextSize(25);
                textView.setGravity(Gravity.CENTER);
                builder.setView(textView);
                builder.setCancelable(true);
                builder.show();*/
                //TODO in the short time,give monkey some tips
                Toast.makeText(getActivity(), /*"我建议你先用用脑子。"*/"为什么不先思考一下呢？", Toast.LENGTH_LONG).show();
                break;
            case R.id.add_ao:
                addAndRefreshStaffing(Const.AO_SLICE);
                break;
            case R.id.add_li:
                addAndRefreshStaffing(Const.LI_SLICE);
                break;
            case R.id.add_slice:
                if (mOreoViewModel.getPieceList().size() < 1) {
                    break;
                }
                addAndRefreshStaffing(Const.AND_SLICE);
                break;
            case R.id.sub_piece:
                //when there is no piece has been added,do nothing
                if (mOreoViewModel.getPieceList().size() < 1) {
                    break;
                }
                mOreoViewModel.getPieceList().remove(mOreoViewModel.getPieceList().size() - 1);
                addAndRefreshStaffing(-1);
                break;
            case R.id.maker:
                //
                if (mOreoViewModel.getPieceList().size() < 1) {
                    //TODO add time check
                    Toast.makeText(getActivity(), "巧妇难为无米之炊。", Toast.LENGTH_LONG).show();
                    break;
                }
                mCreateOreo.createOreo();
                break;
            default:
                //do nothing
        }
    }

    private void addAndRefreshStaffing(int type) {
        if (mOreoViewModel.getPieceList().size() > 49) {
            //TODO add time check
            Toast.makeText(getActivity(), "一方面做不了，一方面怕你长胖。", Toast.LENGTH_SHORT).show();
        }
        if (type != -1) {
            PieceProperty pieceProperty = new PieceProperty(type);
            mOreoViewModel.getPieceList().add(pieceProperty);
        }
        mHandler.sendEmptyMessage(MSG_UPDATE_STUFFING);
    }

    /*
     *** show oreo stuffing depending on what user like
     */
    private void updateStuffingBasedOnManuallyAdd() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PieceProperty p :
                mOreoViewModel.getPieceList()) {
            if ((p.getType()) == Const.AO_SLICE) {
                stringBuilder.append("奥");
            } else if (p.getType() == Const.LI_SLICE) {
                stringBuilder.append("利");
            } else {
                stringBuilder.append("与");
            }
        }
        oreoStuffingDes.setText(stringBuilder.toString());
    }

    private final class RefreshHandler extends Handler {
        RefreshHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_STUFFING:
                    updateStuffingBasedOnManuallyAdd();
                    break;
            }
        }
    }

    public interface CreateOreo {
        void createOreo();
    }
}
