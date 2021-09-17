package com.earndero.chesserezade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ChessBoard {
    final static int marginWidth = 16;
    final int width = 8;
    final int height = 8;
    int enpassant = 0;
    boolean longCastlingWenabled = true;
    boolean shortCastlingWenabled = true;
    boolean longCastlingBenabled = true;
    boolean shortCastlingBenabled = true;
    public int enPassant;
    private int writePos;
    boolean turnBlack = false;
    private Piece[] squares;
    private HashMap<Character,PieceInfo> map = new HashMap<>();
    private List<Piece> whiteList = new ArrayList<>();
    private List<Piece> blackList = new ArrayList<>();
    Piece kingWhite,kingBlack;

    public ChessBoard() throws Exception {
        pieceKinds.add(new PieceInfo('K',false, PieceKind.King));
        pieceKinds.add(new PieceInfo('k',true, PieceKind.King));
        pieceKinds.add(new PieceInfo('Q',false,PieceKind.Queen));
        pieceKinds.add(new PieceInfo('q',true,PieceKind.Queen));
        pieceKinds.add(new PieceInfo('R',false,PieceKind.Rook));
        pieceKinds.add(new PieceInfo('r',true,PieceKind.Rook));
        pieceKinds.add(new PieceInfo('N',false, PieceKind.Knight));
        pieceKinds.add(new PieceInfo('n',true,PieceKind.Knight));
        pieceKinds.add(new PieceInfo('B',false, PieceKind.Bishop));
        pieceKinds.add(new PieceInfo('b',true,PieceKind.Bishop));
        pieceKinds.add(new PieceInfo('P',false, PieceKind.Pawn));
        pieceKinds.add(new PieceInfo('p',true, PieceKind.Pawn));
        pieceKinds.add(new PieceInfo('M',false, PieceKind.Marshal));
        pieceKinds.add(new PieceInfo('m',true,PieceKind.Marshal));
        pieceKinds.add(new PieceInfo('C',false, PieceKind.Cardinal));
        pieceKinds.add(new PieceInfo('c',true,PieceKind.Cardinal));
        for (int i=0; i<pieceKinds.size(); i++) {
            map.put(pieceKinds.get(i).sym,pieceKinds.get(i));
        }
        squares = new Piece[height* marginWidth];
        clear();
        Fen fen = new Fen();
        //fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", this);
    }

    public void clear() {
        writePos = 0;
        Arrays.fill(squares,null);
        whiteList.clear();
        blackList.clear();
    }

    private void check(int start, int count) throws Exception {
        int newline = marginWidth *((writePos / marginWidth)+1);
        if (start+count>newline)
            throw new Exception();
    }
    
    public void add(char c, int spacelen) throws Exception {
        if (c!='.') throw new Exception();
        check(writePos,spacelen);
        //was null, is null
        writePos +=spacelen;
    }

    List<PieceInfo> pieceKinds = new ArrayList<>();

    public int add(char c) throws Exception {
        check(writePos,1);
        PieceInfo info = map.get(c);
        Piece container = new Piece(info, this, writePos);
        squares[writePos]=container;
        if (info.colorBlack) {
            blackList.add(container);
            if (info.sym=='k') kingBlack = container;
        } else {
            whiteList.add(container);
            if (info.sym=='K') kingWhite = container;
        }
        writePos++;
        return writePos -1;
    }

    public void newLine() {
        int row = writePos / marginWidth;
        writePos = marginWidth *(row+1);
    }

    public MoveList gen(boolean onlyCaptured) throws IOException {
        MoveList moveList = new MoveList(this, onlyCaptured);
        List<Piece> pieceList;
        if (turnBlack)
            pieceList = blackList;
        else
            pieceList = whiteList;
        for (Piece piece:pieceList) {
            if (!piece.isCaptured) {
                if (piece.info.kind==PieceKind.King || !discovery(piece.pos))
                piece.gen(moveList);
            }
        }
        return moveList;
    }

    private boolean discovery(int pos) {
        return false;
    }

    public boolean check(int pos) {
        return true;
    }

    boolean isEmptyForLong(boolean black) {
        if (black)
            return isEmpty(1) && isEmpty(2) && isEmpty(3);
        else {
            int n = marginWidth*(height-1);
            return isEmpty(n+1) && isEmpty(n+2) && isEmpty(n+3);
        }
    }

    boolean isEmptyForShort() {
        if (turnBlack) { int n = width;
            return isEmpty(n-2) && isEmpty(n-3);
        }
        else {
            int n = marginWidth*(height-1)+width;
            return isEmpty(n-2) && isEmpty(n-3);
        }
    }

    public int getRow(int pos) {
        return pos / marginWidth;
    }

    boolean outOfBoard(int pos) {
         return  (pos<0 || pos >= marginWidth*height || (pos % marginWidth >=width));
    }

    public boolean isEmpty(int pos) {
        if (outOfBoard(pos)) return false;
        return squares[pos]==null;
    }

    //optimization
    public boolean isEmptyOnBoard(int pos) {
        return squares[pos] == null;
    }

    public boolean onWhiteSecond(int pos) {
        return getRow(pos)==height-2;
    }

    public boolean onBlackSecond(int pos) {
        return getRow(pos)==1;
    }

    public boolean isOpponent(boolean isBlack, int pos) {
        if (outOfBoard(pos)) return false;
        if (squares[pos]==null) return false;
        return squares[pos].info.colorBlack!=isBlack;
    }

    public boolean isBlocked(boolean isBlack, int pos) {
        if (outOfBoard(pos)) return true;
        if (squares[pos]==null) return false;
        return squares[pos].info.colorBlack==isBlack;
    }

    public Piece get(int pos) {
        return squares[pos];
    }

    public boolean isRook(int pos, boolean black) {
        Piece piece = get(pos);
        if (piece==null) return false;
        if (black)
            return piece.info.sym=='r';
        else
            return piece.info.sym=='R';
    }

    public void set(int newPos, Piece piece) {
        squares[newPos] = piece;
    }

    public boolean isColumnH(int pos) {
        return pos % marginWidth == width-1;
    }//@todo other chessed can be differ

    public boolean isColumnA(int pos) {
        return pos % marginWidth == 0;
    }

    public boolean outOf(int pos) {
        return pos<0 || pos>= marginWidth*height ||  (pos % marginWidth)>=width;
    }
}
