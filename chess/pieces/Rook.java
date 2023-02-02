package chess.pieces;

import board.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean [][]mat = new boolean[getBoard().getRow()][getBoard().getColumn()];

        return mat;
    }
}
