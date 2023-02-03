package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position pos) {
        ChessPiece piece = (ChessPiece) getBoard().piece(pos);
        return piece == null || piece.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean [][]mat = new boolean[getBoard().getRow()][getBoard().getColumn()];

        Position aux = new Position(0, 0);

        //Acima
        aux.setValues(position.getRow() - 1, position.getColumn());
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        //Abaixo
        aux.setValues(position.getRow() + 1, position.getColumn());
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        //Esquerda
        aux.setValues(position.getRow(), position.getColumn() - 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        //Direita
        aux.setValues(position.getRow(), position.getColumn() + 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        //Cima-Esquerda
        aux.setValues(position.getRow() - 1, position.getColumn() - 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        //Cima-Direita
        aux.setValues(position.getRow() - 1, position.getColumn() + 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        //Baixo-Esquerda
        aux.setValues(position.getRow() + 1, position.getColumn() - 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;
        //Baixo-Direita
        aux.setValues(position.getRow() + 1, position.getColumn() + 1);
        if(getBoard().positionExists(aux) && canMove(aux))
            mat[aux.getRow()][aux.getColumn()] = true;

        return mat;
    }
}
