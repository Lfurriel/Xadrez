package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{

    public Knight(Board board, Color color) { super(board, color); }

    private boolean canMove(Position pos) {
        ChessPiece piece = (ChessPiece) getBoard().piece(pos);
        return piece == null || piece.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {

        boolean [][]mat = new boolean[getBoard().getRow()][getBoard().getColumn()];

        Position aux = new Position(0, 0);


        aux.setValues(position.getRow() - 1, position.getColumn() - 2);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        aux.setValues(position.getRow() - 1, position.getColumn() + 2);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        aux.setValues(position.getRow() + 1, position.getColumn() - 2);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        aux.setValues(position.getRow() + 1, position.getColumn() + 2);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        aux.setValues(position.getRow() - 2, position.getColumn() - 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        aux.setValues(position.getRow() - 2, position.getColumn() + 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        aux.setValues(position.getRow() + 2, position.getColumn() - 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        aux.setValues(position.getRow() + 2, position.getColumn() + 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;

        return mat;
    }

    @Override
    public String toString() {
        return "N";
    }

}
