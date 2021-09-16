package com.sample.game;

import com.earndero.chesserezade.Searcher;
import com.earndero.chesserezade.ChessBoard;
import com.earndero.chesserezade.Fen;
import com.earndero.chesserezade.Move;
import com.earndero.chesserezade.State;
import com.earndero.chesserezade.stones.History;
import com.earndero.chesserezade.stones.gomoku.Config;

public class Main {

    public static void main(String[] args) throws Exception {
        History history = new History();
        Config config = new Config();
        Fen fen = new Fen();
        ChessBoard board = new ChessBoard();
        State initialState = new State(board);
        Searcher.minimax(initialState, 4, false); //warmup Java Machine
        fen.parse("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ", board);
        for (int i=4; i<=5; i++) {
            long start = System.nanoTime();
            Move.zeroEvalCount();
            Searcher.minimax(initialState, i, false);
            long end = System.nanoTime();
            double diff = end - start;
            long count = Move.getEvalCount();
            System.out.printf("%d %d\n",i,count);
            System.out.printf("time =%f  %f ns per alloc, %f mln/second\n", diff / 1e9, diff / count, count * 1e3 / diff);
        }
    }
}
