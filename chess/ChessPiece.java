package chess;

import board.Piece;
import board.Board;
import board.Position;

public abstract class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected boolean isThereOpponentPiece(Position pos) {

        ChessPiece piece = (ChessPiece) getBoard().piece(pos);

        return piece != null && piece.getColor() != color;
    }
}
