package com.earndero.chesserezade.stones;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class History {
    class PairComparator implements Comparator<Pair> {
        @Override
        public int compare(Pair a, Pair b) {
            return a.number - b.number;
        }
    }
    class Pair {
        int number;
        int boardPos;
        Pair(int number, int boardPos) {
            this.number= this.number;
            this.boardPos = boardPos;
        }
    }

    void clear() {
        pairs.clear();
    }

    void done() {
        pairs.sort(new PairComparator());
    }

    private List<Pair> pairs = new ArrayList<>();
    void add(int number, int boardpos) {
        pairs.add(new Pair(number,boardpos));
    }
}
