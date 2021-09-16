package com.earndero.chesserezade;

import java.util.List;

//binary map
public class PseudoAttacks {
    ChessBoard board;
    int[] attacksW;
    int[] attacksB;

    PseudoAttacks(ChessBoard board){
        this.board = board;
        attacksW = new int[board.height*board.marginWidth];
        attacksB = new int[board.height*board.marginWidth];
    }

    void init(List<Piece> pieceList) {
        for (Piece piece:pieceList) {
            piece.attacks(attacksW, false);
            piece.attacks(attacksB, false);
        }
    }
}

