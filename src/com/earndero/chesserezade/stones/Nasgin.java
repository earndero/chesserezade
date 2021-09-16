package com.earndero.chesserezade.stones;

import static java.lang.Character.isDigit;

/*
Numeric Augmented Stone Games Interchange Notation
przykłady
https://en.wikipedia.org/wiki/Gomoku#/media/File:Gomoku-game-1.svg
//////6xxx/5ooxo/5oxxxo/5oxo1o/4oxxx/6o
//////6(x15)(x13)(x21)/5(o8)(o2)x(o10)/5(o4)(x3)(x5)(x11)(o12)/5(o6)(x7)(o14)1(o16)/4(o20)(x9)(x17)(x19)/6(o18)"
https://en.wikipedia.org/wiki/Gomoku#/media/File:Gomoku-game-2.svg
///7x/4o1xo2o/5x1x1x/4xoxxxo/3x1ooxoo/2o2oxxxo/5oxo1o/4oxxxox/6o
///7(x37)/4(o28)1(x29)(o26)2(o24)/5(x27)1(x25)1(x23)/4(x33)(o34)xxx(o22)/3(x35)1ooxo(o30)/2(o36)2oxxxo/5oxo1o/4oxxx(o32)(x31)/6o
https://en.wikipedia.org/wiki/Gomoku#/media/File:Gomoku-game-3.svg
////4o1oo2o1o/5xoxxxox/5oxxxox/5ooxox/5oxxxox/5oxo1o1x/4oxxx/6o
////4(o24)1(o38)(o26)2(o28)1(o36)/5(x23)(o30)(x25)(x29)(x27)(o32)(x31)/5(o22)xxx(o34)(x33)/5ooxo(x35)/5oxxxo(x37)/5oxo1o1(x39)/4oxxx/6o
* */
public class Nasgin {
    public void parse(String nasgin, StoneBoard board) throws Exception {
        board.clear();
        for (int pos = 0; pos < nasgin.length(); ) {
            char c = nasgin.charAt(pos);
            int number = 0;
            if (isDigit(c)) {
                while (isDigit(c)) {
                    number = 10 * number + (c - '0');
                    pos++;
                    c = nasgin.charAt(pos);
                }
                if (number<1) throw new Exception();
                board.add('.',number);
            }
            else if (c=='(') {
                pos = addAugmented(nasgin,pos,board);
            }
            else {
                switch(c) {
                    case 'x': case 'o': board.add(c);break;
                    case '/': board.newLine(); break;
                    default: throw new Exception();
                }
                pos++;
            }
        }
    }

    private int addAugmented(String fen, int pos, StoneBoard board) throws Exception {
        pos++;
        board.add(fen.charAt(pos));
        pos++;
        int pos2 = fen.indexOf(")",pos);
        String numStr = fen.substring(pos,pos2);
        return pos2+1;
    }
}
