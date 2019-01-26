package com.oreomaker.oreo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.p.oreoview.PieceProperty;

import java.util.ArrayList;


public class OreoViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<PieceProperty>> allPieces =
            new MutableLiveData<>();

    public void updatePieceList(ArrayList<PieceProperty> piecePropertyArrayList) {
        allPieces.setValue(piecePropertyArrayList);
    }

    public ArrayList<PieceProperty> getPieceList() {
        return allPieces.getValue();
    }
}
