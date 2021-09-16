package com.earndero.chesserezade.stones.gomoku;

public class Eval {
    public int evaluate(Lines lines) {
        int weight = 1;
        int sum = 0;
        for (int i = 1; i<lines.bins.size()-1; i++) {
            LineBin bin = lines.bins.get(i);
            sum += bin.lines.size()*weight;
            weight*=10;
        }
        if (lines.bins.get(lines.bins.size()-1).lines.size()>0)
            sum += 1e9;
        return sum;
    }
}
