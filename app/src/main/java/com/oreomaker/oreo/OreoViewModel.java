package com.oreomaker.oreo;

import android.arch.lifecycle.ViewModel;

import com.p.oreoview.PieceProperty;

import java.util.ArrayList;


public class OreoViewModel extends ViewModel {
    private final ArrayList<PieceProperty> allPieces =
            new ArrayList<>();

    void updatePieceList(ArrayList<PieceProperty> piecePropertyArrayList) {
        allPieces.addAll(piecePropertyArrayList);
    }

    public ArrayList<PieceProperty> getPieceList() {
        return allPieces;
    }
}
