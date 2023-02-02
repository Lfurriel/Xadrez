package chess;

import tabuleiro.Piece;
import tabuleiro.Board;

public class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board) {
        super(board);
    }

    public Color getColor() {
        return color;
    }
}
