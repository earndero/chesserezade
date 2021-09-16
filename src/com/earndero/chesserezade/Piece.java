package com.earndero.chesserezade;

import java.io.IOException;

public class Piece {
    ChessBoard board;
    int pos;
    PieceInfo info;
    boolean isCaptured = false;

    Piece(PieceInfo info, ChessBoard board, int pos) {
        this.info = info;
        this.board = board;
        this.pos = pos;
    }

    void gen(MoveList moveList) throws IOException {
        switch (info.kind) {
            case King: {
                genKing(moveList);
                genCastlingLong(moveList);
                genCastlingShort(moveList);
            }break;
            case Queen: genQueen(moveList);break;
            case Cardinal: genCardinal(moveList);break;
            case Marshal: genMarshal(moveList);break;
            case Rook: genRook(moveList);break;
            case Bishop: genBishop(moveList);break;
            case Knight: genKnight(moveList);break;
            case Pawn: genPawn(moveList);break;
        }
    }

    private void genCastlingLong(MoveList moveList) {
        if (!board.isEmptyForLong(info.colorBlack)) return;
        if (info.colorBlack) {
            if (!board.longCastlingBenabled) return;
        } else {
            if (!board.longCastlingWenabled) return;
        }
        if (board.isRook(pos-4, info.colorBlack)) {
            if (board.check(pos) || board.check(pos-1) || board.check(pos-2) ) return;
            moveList.addCastling(this, pos - 2, board.get(pos - 4), pos - 1);
        }
    }

    private void genCastlingShort(MoveList moveList) {
        if (!board.isEmptyForShort()) return;
        if (info.colorBlack) {
            if (!board.shortCastlingBenabled) return;
        } else {
            if (!board.shortCastlingWenabled) return;
        }
        if (board.isRook(pos+3, info.colorBlack)) {
            if (board.check(pos) || board.check(pos+1) || board.check(pos+2) ) return;
            moveList.addCastling(this, pos + 2, board.get(pos + 3), pos + 11);
        }
    }


    private int[] dir90degree = {1,-1,16,-16};
    private int[] dir45degree = {-17,-15,15,17};
    private int[] dirKnight = {-33,-31,-18,-14,14,18,31,33};
    private int[] dirPawnW = {-17,-15};
    private int[] dirPawnB = {17,15};

    private long genKing(MoveList moveList) throws IOException {
        return genMoves(moveList, dir90degree, false)+
               genMoves(moveList, dir45degree, false);
    }

    private long genQueen(MoveList moveList) throws IOException {
        return genMoves(moveList, dir90degree, true) +
               genMoves(moveList, dir45degree, true);
    }
    private long genMarshal(MoveList moveList) throws IOException {
        return genMoves(moveList, dir90degree, true)+
              genMoves(moveList, dirKnight, false);
    }
    private long genCardinal(MoveList moveList) throws IOException {
        return genMoves(moveList, dir45degree, true) +
               genMoves(moveList, dirKnight, false);
    }
    private long genRook(MoveList moveList) throws IOException {
        return genMoves(moveList, dir90degree, true);
    }
    private long genBishop(MoveList moveList) throws IOException {
        return genMoves(moveList, dir45degree, true);
    }
    private long genKnight(MoveList moveList) throws IOException {
        return genMoves(moveList, dirKnight, false);
    }

    private long genPawn(MoveList moveList) throws IOException {
        //if (pos==50 && board.get(50)==null && Move.istatic==6)
        long savCount = moveList.getCount();
        if (info.colorBlack) {
            boolean ifProme = board.onWhiteSecond(pos);
            Promotion promotion = ifProme?Promotion.PROMOTE:Promotion.NOT_PROMOTE;
            if (board.isEmpty(pos+16))
                moveList.addMoveIfPromotion(this,pos+16, null, promotion);
            if (board.onBlackSecond(pos) && board.isEmpty(pos+16) && board.isEmpty(pos+32))
                moveList.addMove(this,pos+32, null);
            if (board.isOpponent(info.colorBlack, pos+15)) {
                moveList.addMoveIfPromotion(this,pos+15, board.get(pos+15), promotion);
            }
            if (board.isOpponent(info.colorBlack, pos+17)) {
                moveList.addMoveIfPromotion(this,pos+17,board.get(pos+17), promotion);
            }
            //En passant
            if (board.enpassant>0) {
                if (pos+15==board.enpassant||pos+17==board.enpassant)
                    moveList.addMove(this,board.enpassant,board.get(board.enpassant-board.marginWidth));
            }
        }
        else {
            boolean ifProme = board.onBlackSecond(pos);
            Promotion promotion = ifProme?Promotion.PROMOTE:Promotion.NOT_PROMOTE;
            if (board.isEmpty(pos-16))
                moveList.addMoveIfPromotion(this,pos-16, null, promotion);
            if (board.onWhiteSecond(pos) && board.isEmpty(pos-16) && board.isEmpty(pos-32))
                moveList.addMove(this,pos-32, null);
            if (board.isOpponent(info.colorBlack, pos-15)) {
                moveList.addMoveIfPromotion(this,pos-15,board.get(pos-15), promotion);
            }
            if (board.isOpponent(info.colorBlack, pos-17)) {
                moveList.addMoveIfPromotion(this,pos-17,board.get(pos-17), promotion);
            }

            //En passant
            if (board.enpassant>0) {
                if (pos-15==board.enpassant||pos-17==board.enpassant)
                    moveList.addMove(this,board.enpassant,board.get(board.enpassant+board.marginWidth));
            }
        }
        return moveList.getCount()-savCount;
    }

    private long genMoves(MoveList moveList, int[] directions, boolean canFar) throws IOException {
        long savCount = moveList.list.size();
        boolean colorBlack = info.colorBlack;
        for (int delta:directions) {
            int newPos = pos+delta;
            while (!board.isBlocked(colorBlack, newPos)) {
                Piece captured = board.isEmptyOnBoard(newPos)?null:board.get(newPos);
                moveList.addMove(this,newPos, captured);
                if (captured!=null) break;
                if (!canFar) break;
                newPos+=delta;
            }
        }
        return moveList.list.size()-savCount;
    }

    public void attacks(int[] attacks, boolean black) {
        switch (info.kind) {
            case King: attacksByKing(attacks, black); break;
            case Queen: attacksByQueen(attacks, black); break;
            case Cardinal: attacksByCardinal(attacks, black); break;
            case Marshal: attacksByMarshal(attacks, black); break;
            case Rook: attacksByRook(attacks, black); break;
            case Bishop: attacksByBishop(attacks, black); break;
            case Knight: attacksByKnight(attacks, black); break;
            case Pawn: attacksByPawn(attacks, black); break;
        }
    }

    private void attacksByPawn(int[] attacks, boolean black) {
    }

    private void attacksByKnight(int[] attacks, boolean black) {
    }

    private void attacksByBishop(int[] attacks, boolean black) {
    }

    private void attacksByRook(int[] attacks, boolean black) {
    }

    private void attacksByMarshal(int[] attacks, boolean black) {
    }

    private void attacksByCardinal(int[] attacks, boolean black) {
    }

    private void attacksByQueen(int[] attacks, boolean black) {
    }

    private void attacksByKing(int[] attacks, boolean black) {
    }
}
