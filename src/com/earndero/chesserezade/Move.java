package com.earndero.chesserezade;

public class Move extends State {
    Piece piece;
    int newPos;
    char promotedTo;
    private boolean savCastlingShort;
    private boolean savCastlingLong;
    //private boolean savCastlingShort;
    //private boolean savCastlingLong;
    Piece captured = null;
    private int savEnPassant;

    public Move(ChessBoard board, Piece piece, int newPos, Piece captured, char promotedTo) {
        super(board);
        this.piece = piece;
        this.newPos = newPos;
        this.promotedTo = promotedTo;
        this.captured = captured;
    }

    @Override
    public void makeMove() throws Exception {
        if (board.turnBlack) {
            savCastlingLong = board.longCastlingBenabled;
            savCastlingShort = board.shortCastlingBenabled;
        }
        else {
            savCastlingLong = board.longCastlingWenabled;
            savCastlingShort = board.shortCastlingWenabled;
        }
        if (piece.info.sym=='p' && piece.pos / board.marginWidth ==1 && newPos == piece.pos + 2*board.marginWidth ||
                piece.info.sym=='P' && piece.pos / board.marginWidth ==board.height-2 && newPos == piece.pos - 2*board.marginWidth)
            board.enpassant = piece.pos - board.marginWidth;
        else
            board.enpassant = 0;
        if (captured!=null) {
            char symCap = captured.info.sym;
            captured.isCaptured = true;
            if (symCap=='R') {
                if (board.isColumnH(captured.pos))
                    board.shortCastlingWenabled = false;
                else if (board.isColumnA(captured.pos))
                    board.longCastlingWenabled = false;
            }
            if (symCap=='r') {
                if (board.isColumnH(captured.pos))
                    board.shortCastlingBenabled = false;
                else if (board.isColumnA(captured.pos))
                    board.longCastlingBenabled = false;
            }
        }
        board.set(newPos,piece);
        board.set(piece.pos,null);
        char sym = piece.info.sym;
        if (sym=='K') {
            board.shortCastlingWenabled = false;
            board.longCastlingWenabled = false;
        }
        if (sym=='k') {
            board.shortCastlingBenabled = false;
            board.longCastlingBenabled = false;
        }
        if (sym=='R') {
            if (board.isColumnH(piece.pos))
                board.shortCastlingWenabled = false;
            else if (board.isColumnA(piece.pos))
                board.longCastlingWenabled = false;
        }
        if (sym=='r') {
            if (board.isColumnH(piece.pos))
                board.shortCastlingBenabled = false;
            else if (board.isColumnA(piece.pos))
                board.longCastlingBenabled = false;
        }
        int temp = piece.pos;
        piece.pos = newPos;
        newPos = temp;
        savEnPassant = board.enpassant;
        board.turnBlack = !board.turnBlack;
    }

    @Override
    public void takeBackMove() {
        board.turnBlack = !board.turnBlack;
        board.enpassant = savEnPassant;
        board.set(newPos,piece);
        if (captured!=null)
            captured.isCaptured=false;
        board.set(piece.pos,captured);
        int temp = piece.pos;
        piece.pos = newPos;
        newPos = temp;
        if (board.turnBlack) {
            board.longCastlingBenabled = savCastlingLong;
            board.shortCastlingBenabled = savCastlingShort;
        }
        else {
            board.longCastlingWenabled = savCastlingLong;
            board.shortCastlingWenabled = savCastlingShort;
        }
    }
}
