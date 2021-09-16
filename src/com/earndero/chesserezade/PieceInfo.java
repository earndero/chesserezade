package com.earndero.chesserezade;

public class PieceInfo {
    char sym;
    public boolean colorBlack;
    PieceKind kind;
    public PieceInfo(char sym, boolean colorBlack,PieceKind kind) {
        this.sym = sym;
        this.colorBlack = colorBlack;
        this.kind = kind;
    }
}
