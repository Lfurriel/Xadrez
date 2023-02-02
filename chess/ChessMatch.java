package chess;

import tabuleiro.Board;

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
    }

    public ChessPiece[][] getPieces() {
        int i, j;
        ChessPiece [][] mat = new ChessPiece[board.getRow()][board.getColumn()];
        for (i = 0; i < board.getRow(); i++) {
            for (j = 0; j < board.getColumn(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }

        return mat;
    }
}
