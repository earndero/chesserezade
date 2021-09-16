package com.earndero.chesserezade;

import java.io.IOException;

public class State implements INode {
    ChessBoard board;
    static private long evalCount;
    public State(ChessBoard board) {
        this.board = board;
    }

    static public long getEvalCount() {
        return evalCount;
    }

    public static void zeroEvalCount() {
        evalCount = 0;
    }

    @Override
    public boolean is_a_terminal_node() {
        return false;
    }

    @Override
    public int heuristic_value_of() {
        evalCount++;
        return 0;
    }

    @Override
    public INodeList genChilds() throws IOException {
        return board.gen(false);
    }

    @Override
    public void takeBackMove() {

    }

    @Override
    public void makeMove() throws Exception {

    }
}
