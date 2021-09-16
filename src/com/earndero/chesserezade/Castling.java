package com.earndero.chesserezade;

public class Castling extends State {
    private int kingNewPos;
    private int rookNewPos;
    private Piece king;
    private Piece rook;
    private boolean savCastlingShortB;
    private boolean savCastlingLongB;
    private boolean savCastlingShortW;
    private boolean savCastlingLongW;

    public Castling(ChessBoard board, Piece king, int kingNewPos, Piece rook, int rookNewPos) {
        super(board);
        this.king = king;
        this.kingNewPos = kingNewPos;
        this.rook = rook;
        this.rookNewPos = rookNewPos;
    }

    private void exchange() {
        board.set(king.pos,null);
        board.set(kingNewPos,king);
        int temp = king.pos;
        king.pos = kingNewPos;
        kingNewPos = temp;

        board.set(rook.pos,null);
        board.set(rookNewPos,rook);
        temp = rook.pos;
        rook.pos = rookNewPos;
        rookNewPos = temp;
        board.turnBlack = !board.turnBlack;
    }

    @Override
    public void makeMove() {
        if (king.info.colorBlack) {
            savCastlingLongB = board.longCastlingBenabled;
            savCastlingShortB = board.shortCastlingBenabled;
            board.longCastlingBenabled = false;
            board.shortCastlingBenabled = false;
        } else {
            savCastlingLongW = board.longCastlingWenabled;
            savCastlingShortW = board.shortCastlingWenabled;
            board.longCastlingWenabled = false;
            board.shortCastlingWenabled = false;
        }
        exchange();
    }

    @Override
    public void takeBackMove() {
        exchange();
        if (board.turnBlack) {
            board.longCastlingBenabled = savCastlingLongB;
            board.shortCastlingBenabled = savCastlingShortB;
        } else {
            board.longCastlingWenabled = savCastlingLongW;
            board.shortCastlingWenabled = savCastlingShortW;
        }
    }
}
