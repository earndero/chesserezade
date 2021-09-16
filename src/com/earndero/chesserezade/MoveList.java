package com.earndero.chesserezade;

import java.util.ArrayList;
import java.util.List;

public class MoveList implements INodeList {
//    List<Move> moveList = new ArrayList<>();
//    List<Move> captureList = new ArrayList<>();
    ChessBoard board;
    List<INode> list = new ArrayList<>();

    public int getCount() {return list.size();}

    boolean onlyCaptured;
    private int moveIndex = 0;

    MoveList(ChessBoard board, boolean onlyCaptured) {
        this.board = board;
        this.onlyCaptured = onlyCaptured;
    }

    public void doMove() {
        moveIndex++;
    }

    public void undoMove() {
        moveIndex--;
    }

    public void addMoveIfPromotion(Piece pawn, int newPos, Piece captured, Promotion promotion) {
        if (promotion==Promotion.PROMOTE)
            addMovePromotion(pawn, newPos, captured);
        else
            addMove(pawn, newPos, captured);
    }

    private void addMovePromotion(Piece pawn, int newPos, Piece captured) {
        if (!onlyCaptured || captured!=null) {
            String promostr;
            if (pawn.info.colorBlack)
                promostr = "nbrq";
            else
                promostr = "NBRQ";
            for (char c: promostr.toCharArray())
                list.add(new Move(board, pawn, newPos, captured, (char)0));
        }
    }

    public void addMove(Piece piece, int newPos, Piece captured) {
        if (!onlyCaptured || captured!=null) {
            Move move = new Move(board, piece, newPos, captured, (char)0);
            list.add(move);
        }
    }

    @Override
    public INode getNext() {
        INode node;
        if (moveIndex< list.size())
            node= list.get(moveIndex);
        else
            node = null;
        moveIndex++;
        return node;
    }

    public void addCastling(Piece king, int kingNewPos , Piece rook, int rookNewPos) {
        Castling castling = new Castling(board, king, kingNewPos, rook, rookNewPos);
        list.add(castling);
    }
}
