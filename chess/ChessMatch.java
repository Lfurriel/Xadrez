package chess;

import board.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRow()][board.getColumn()];
        for (int i=0; i<board.getRow(); i++) {
            for (int j=0; j<board.getColumn(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        //Inicializando Torres
        placeNewPiece('a', 1, new Rook(board, Color.BLACK));
        placeNewPiece('h', 1, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new Rook(board, Color.WHITE));
        placeNewPiece('h', 8, new Rook(board, Color.WHITE));

        //Inicializando Reis
        placeNewPiece('e', 1, new King(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.WHITE));

    }
}