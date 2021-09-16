package com.earndero.chesserezade.stones;
/*
rows mają size+1 bo gdy ostatni element i jest po nim /
* */

import com.earndero.chesserezade.stones.gomoku.Config;
import com.earndero.chesserezade.stones.gomoku.Lines;
import com.earndero.chesserezade.stones.gomoku.Slope;

import java.util.Arrays;

public class StoneBoard {
    private Config config;
    private char[] squares;
    private final int size;
    private static final int margin=1;
    private final int marginSize;
    private int writePos;

    public StoneBoard(Config config) {
        this.config = config;
        this.size = config.boardSize;
        marginSize = size+2*margin;
        squares = new char[marginSize* marginSize];
        clear();
    }

    void clear() {
        writePos = margin*marginSize+margin;
        Arrays.fill(squares, '.');
        zeroMargin();
    }

    private void zeroMargin() {
        int pos0 = 0;
        int pos1 = marginSize*(margin+size);
        for (int i=0; i<margin; i++) {
            for (int j = 0; j < marginSize; j++) {
                squares[pos0] = 0;
                squares[pos1] = 0;
                pos0++;
                pos1++;
            }
        }
        pos0 = 0;
        pos1 = margin+size;
        for (int i=0; i<marginSize; i++) {
            for (int j = 0; j < margin; j++) {
                squares[pos0] = 0;
                squares[pos1] = 0;
                pos0+=marginSize;
                pos1+=marginSize;
            }
        }
    }

    public int add(char c) throws Exception {
        check(writePos,1);
        squares[writePos]=c;
        writePos++;
        return writePos -1;
    }

    private void check(int start, int count) throws Exception {
        int newline = marginSize *((writePos / marginSize)+1);
        if (start+count>=newline)
            throw new Exception();
    }

    public void add(char c, int spacelen) throws Exception {
        check(writePos,spacelen);
        for (int i=0; i<spacelen; i++)
            squares[writePos +i]=c;
        writePos +=spacelen;
    }

    public void newLine() {
        int row = writePos / marginSize;
        writePos = marginSize *(row+1)+margin;
    }

    int getLineLen1(Character c, Character lastChar, int lineLen, int i, int j,
                    Lines linesX, Lines linesO, Slope slope) {
        if ((c=='x'||c=='o')&&(c.equals(lastChar))) {
            lineLen++;
        }
        else
        {
            if (lineLen>0) {
                Lines lines;
                if (lastChar=='x')
                    lines = linesX;
                else
                    lines = linesO;
                lines.add(slope, lineLen, i, j);
            }

            if (lineLen>0)
            System.out.printf("%s %c %c len=%d pos=%d,%d\n",slope.name(),c,lastChar, lineLen,i,j);
            if (c=='x'||c=='o')
                lineLen = 1;
            else
                lineLen = 0;
        }
        return lineLen;
    }

    int getLineLen(Character c, Character lastChar,int lineLen, int i, int j,
                   Lines linesX, Lines linesO, Slope slope) {
        if ((c=='.')&&(c.equals(lastChar))) {
            lineLen++;
        }
        else
        {
            if (lineLen>0) {
                /*Lines lines;
                if (lastChar=='x')
                    lines = linesX;
                else
                    lines = linesO;
                lines.add(slope, lineLen, i, j);*/
            }

            if (lineLen>0)
                ;//System.out.printf("SP %s %c %c len=%d pos=%d,%d\n",slope.name(),c,lastChar, lineLen,i,j);
            if (c=='.')
                lineLen = 1;
            else
                lineLen = 0;
        }
        return lineLen;
    }

    void computeLinesH(Lines linesX, Lines linesO) {
        for (int i=0; i<size; i++) {
            int pos = (i+margin)* marginSize+margin;
            Character lastChar='.';
            int lineLen = 0;
            for (int j = 0; j< size+1; j++) {
                Character c = squares[pos];
                lineLen = getLineLen(c,lastChar,lineLen,i,j, linesX, linesO, Slope.HORIZONTAL);
                lastChar = c;
                pos++;
            }
        }
    }

    void computeLinesV(Lines linesX, Lines linesO) {
        for (int i=0; i<size; i++) {
            int pos = i+margin*marginSize+margin;
            char lastChar='.';
            int lineLen = 0;
            for (int j = 0; j< size+1; j++) {
                Character c = squares[pos];
                lineLen = getLineLen(c,lastChar,lineLen,i,j, linesX, linesO, Slope.VERTICAL);
                lastChar = c;
                pos+=marginSize;
            }
        }
    }

    void computeLinesAsc(Lines linesX, Lines linesO) {
        for (int i=0; i<size; i++) {
            int diagonalLen = i+1;
            int pos = (i+margin)* marginSize+margin;
            //System.out.printf("pos1=%d\n",pos);
            char lastChar='.';
            int lineLen = 0;
            for (int j = 0; j<=diagonalLen; j++) {
                Character c = squares[pos];
                lineLen = getLineLen(c,lastChar,lineLen,i,j, linesX, linesO, Slope.ASCENDED);
                lastChar = c;
                pos -= marginSize-1;
            }
        }
        for (int i=size; i<2*size-1; i++) {
            int diagonalLen = 2*size-i-1;
            int pos = marginSize*(size+margin-1)+margin+1+i-size;
            //System.out.printf("pos2=%d\n",pos);
            char lastChar='.';
            int lineLen = 0;
            for (int j = 0; j<=diagonalLen; j++) {
                Character c = squares[pos];
                lineLen = getLineLen(c,lastChar,lineLen,i,j, linesX, linesO, Slope.ASCENDED);
                lastChar = c;
                pos -= marginSize-1;
            }
        }
    }

    void computeLinesDesc(Lines linesX, Lines linesO) {
        for (int i=0; i<size; i++) {
            int diagonalLen = i+1;
            int pos = (margin+size-1-i)*marginSize+margin;
            //System.out.printf("pos1=%d\n",pos);
            char lastChar='.';
            int lineLen = 0;
            for (int j = 0; j<=diagonalLen; j++) {
                Character c = squares[pos];
                lineLen = getLineLen(c,lastChar,lineLen,i,j, linesX, linesO, Slope.DESCENDED);
                lastChar = c;
                pos += marginSize+1;
            }
        }
        for (int i=size; i<2*size-1; i++) {
            int diagonalLen = 2*size-i-1;
            int pos = margin*marginSize+margin+1+i-size;
            //System.out.printf("pos2=%d\n",pos);
            char lastChar='.';
            int lineLen = 0;
            for (int j = 0; j<=diagonalLen; j++) {
                Character c = squares[pos];
                lineLen = getLineLen(c,lastChar,lineLen,i,j,linesX, linesO, Slope.DESCENDED);
                lastChar = c;
                pos += marginSize+1;
            }
        }
    }

    public void print() {
        int pos = margin*marginSize+margin;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++){
                System.out.print(squares[pos]);
                pos++;
            }
            pos+=2*margin;
            System.out.println();
        }
    }

    public void computeLines(Lines linesX, Lines linesO) {
        computeLinesH(linesX, linesO);
        computeLinesV(linesX, linesO);
        computeLinesAsc(linesX, linesO);
        computeLinesDesc(linesX, linesO);
    }

    public void diff(StoneBoard board1) {
        if (squares.length!=board1.squares.length)
            System.out.println("diff len");
        else {
            for (int i = 0; i< squares.length; i++) {
                if (squares[i]!=board1.squares[i]) {
                    System.out.printf("diff at %d: %c:%c\n",i, squares[i],board1.squares[i]);
                    return;
                }
            }
        }
        System.out.println("ok");
    }
}
