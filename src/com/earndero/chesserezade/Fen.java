package com.earndero.chesserezade;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

//"3r2k/pb1r3p/1p4p/3nRpq/2BQ/6N/PP3PPP/3R2K/ w"
public class Fen {
    Character playerToMove;
    public void parse(String fen, ChessBoard board) throws Exception {
        board.clear();
        for (int pos = 0; pos < fen.length();) {
            char c = fen.charAt(pos);
            int number = 0;
            if (isDigit(c)) {
                while (isDigit(c)) {
                    number = 10 * number + (c - '0');
                    pos++;
                    c = fen.charAt(pos);
                }
                if (number<1) throw new Exception();
                board.add('.',number);
            } else {
                if (isAlphabetic(c)) board.add(c);
                else if (c == '/') board.newLine();
                else if (c == ' ') {
                    pos++;
                    playerToMove = fen.charAt(pos);
                    break;
                } else throw new Exception();
                pos++;
            }
        }
    }
}
