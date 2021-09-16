package com.earndero.chesserezade.stones.gomoku;

import com.earndero.chesserezade.stones.StoneBoard;

public class Config {
    public int boardSize = 6;
    public int reqLen = 5;
    public boolean reqLenNoMore = true;

    public StoneBoard createBoard() {
        return new StoneBoard(this);
    }

    public Lines createLines(char player) {
        return new Lines(this, player);
    }
}
