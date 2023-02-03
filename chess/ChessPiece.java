package chess;

import board.Piece;
import board.Board;
import board.Position;

public abstract class ChessPiece extends Piece {

    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() { return moveCount; }

    public void increaseMoveCount() { moveCount++; }

    public void decreaseMoveCount() { moveCount++; }

    protected boolean isThereOpponentPiece(Position pos) {

        ChessPiece piece = (ChessPiece) getBoard().piece(pos);

        return piece != null && piece.getColor() != color;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);

    }
}
