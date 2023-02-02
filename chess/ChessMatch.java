package chess;

import board.Board;
import board.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
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

    private void initialSetup() {
        //Inicializando Torres
        board.placePiece(new Rook(board, Color.BLACK), new Position(0, 0));
        board.placePiece(new Rook(board, Color.BLACK), new Position(0, 7));
        board.placePiece(new Rook(board, Color.WHITE), new Position(7, 0));
        board.placePiece(new Rook(board, Color.WHITE), new Position(7, 7));

        //Inicializando Reis
        board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
        board.placePiece(new King(board, Color.BLACK), new Position(0, 4));

    }
}
