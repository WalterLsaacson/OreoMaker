package com.oreomaker.oreo.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oreomaker.R;
import com.oreomaker.oreo.OreoViewModel;
import com.oreomaker.oreo.Utils;
import com.p.oreoview.Const;
import com.p.oreoview.PieceProperty;

import java.util.List;
import java.util.Objects;

public class ControlFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ControlFragment";

    private static final int MSG_UPDATE_STUFFING = 0;

    List<PieceProperty> mOreoList;
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            /*mHandler.sendEmptyMessage(MSG_UPDATE_STUFFING);*/
            oreoStuffingDes.setText(Utils
                    .getDescription(mOreoList));
        }
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
        mOreoList = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(OreoViewModel.class).getPieceList();
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
                Toast.makeText(getActivity(), /*"我建议你先用用脑子。"*/"为什么不先想想呢？", Toast.LENGTH_LONG).show();
                break;
            case R.id.add_ao:
                addAndRefreshStaffing(Const.AO_SLICE);
                break;
            case R.id.add_li:
                addAndRefreshStaffing(Const.LI_SLICE);
                break;
            case R.id.add_slice:
                //1.Nothing can be removed 2.last piece is and,dont allow double andPieces
                if (mOreoList.isEmpty()
                        || mOreoList.get(mOreoList.size() - 1).getType() == Const.AND_SLICE) {
                    break;
                }
                addAndRefreshStaffing(Const.AND_SLICE);
                break;
            case R.id.sub_piece:
                //when there is no piece has been added,do nothing
                if (mOreoList.isEmpty()) {
                    break;
                }
                mOreoList.remove(mOreoList.size() - 1);
                addAndRefreshStaffing(-1);
                break;
            case R.id.maker:
                if (mOreoList.isEmpty()) {
                    //TODO add time check
                    Toast.makeText(getActivity(), "巧妇难为无米之炊。", Toast.LENGTH_LONG).show();
                    break;
                }
                mCreateOreo.createOreo();
                break;
            case R.id.random_oreo:
                genRandomOreo();
                break;
            default:
                //do nothing
        }
    }

    private void genRandomOreo() {
        mOreoList.clear();
        PieceProperty pieceProperty;
        int count = (int) (Math.random() * 11) + 1;
        Log.d(TAG, "onClick: count : " + count);
        for (int i = 0; i < count; i++) {
            int type = (int) (Math.random() * 3);
            if (type == Const.AND_SLICE) {
                //1.empty list
                //2.double AND pieces
                //3.last piece
                if (mOreoList.isEmpty()) {
                    continue;
                }
                pieceProperty = mOreoList.get(mOreoList.size() - 1);
                //remove pre piece to fix case2&3 simultaneous
                if (pieceProperty.getType() == Const.AND_SLICE) {
                    mOreoList.remove(pieceProperty);
                }
                if (i == count - 1) {
                    continue;
                }
                Log.d(TAG, "onClick: " + i);
            }
            pieceProperty = new PieceProperty(type);
            mOreoList.add(pieceProperty);
        }
        if (mOreoList.isEmpty()) {
            genRandomOreo();
            return;
        }
        addAndRefreshStaffing(-1);
    }

    private void addAndRefreshStaffing(int type) {
        if (mOreoList.size() > 49) {
            //TODO add time check
            Toast.makeText(getActivity(), "一方面做不了，一方面怕你长胖。", Toast.LENGTH_SHORT).show();
        }
        if (type != -1) {
            PieceProperty pieceProperty = new PieceProperty(type);
            mOreoList.add(pieceProperty);
        }
        /*mHandler.sendEmptyMessage(MSG_UPDATE_STUFFING);*/
        oreoStuffingDes.setText(Utils
                .getDescription(mOreoList));
    }

    /*
     *** if not in background never need handler
     */
    private final class RefreshHandler extends Handler {
        RefreshHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_STUFFING:
                    oreoStuffingDes.setText(Utils
                            .getDescription(mOreoList));
                    break;
            }
        }
    }

    public interface CreateOreo {
        void createOreo();
    }
}
