package com.p.oreoview;

/*
 *** Defined piece properties
 * Such as : oreo, li, paste upper
 */
public class PieceProperty {
    int type;
    boolean isPasteUpper;

    public PieceProperty(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPasteUpper() {
        return isPasteUpper;
    }

    public void setPasteUpper(boolean pasteUpper) {
        isPasteUpper = pasteUpper;
    }
}
