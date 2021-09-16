package com.earndero.chesserezade.stones.gomoku;

import java.util.ArrayList;
import java.util.List;

class LineInfo {
    Slope slope;
    int endx;
    int endy;
    LineInfo(Slope slope,int endx,int endy) {
        this.slope = slope;
        this.endx = endx;
        this.endy = endy;
    }
}

class LineBin {
    private int len;
    LineBin(int len) {
        this.len = len;
    }
    List<LineInfo> lines = new ArrayList<>();
    void add(LineInfo info) {
        lines.add(info);
    }
}

public class Lines {
    private int maxLen;
    private Config config;
    Character player;

    Lines(Config config, Character player) {
        this.config = config;
        this.maxLen = config.reqLen;
        this.player = player;
        for (int i=0; i<=maxLen; i++)
          bins.add(new LineBin(i));
    }
    List<LineBin> bins = new ArrayList<>();

    public void add(Slope slope, int len, int endx, int endy) {
        LineInfo info = new LineInfo(slope,endx,endy);
        if (len>maxLen) {
            if (!config.reqLenNoMore) {
                LineBin bin = bins.get(maxLen);
                bin.add(info);
            }
        } else {
            LineBin bin = bins.get(len);
            bin.add(info);
        }
    }
}
