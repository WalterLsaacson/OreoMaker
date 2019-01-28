package com.oreomaker.oreo;

import com.p.oreoview.Const;
import com.p.oreoview.PieceProperty;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    /*
     *** show oreo stuffing depending on what user like
     */
    public static String getDescription(List<PieceProperty> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (PieceProperty p :
                list) {
            if ((p.getType()) == Const.AO_SLICE) {
                stringBuilder.append("奥");
            } else if (p.getType() == Const.LI_SLICE) {
                stringBuilder.append("利");
            } else {
                stringBuilder.append("与");
            }
        }
        return stringBuilder.toString();
    }
}
