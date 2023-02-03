package chess.pieces;

import board.Board;
import board.Position;
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

        int i, j;
        boolean [][]mat = new boolean[getBoard().getRow()][getBoard().getColumn()];

        Position aux = new Position(0, 0);

        //Verificando acima
        aux.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
            aux.setRow(aux.getRow() - 1); //Caminha para cima até que encontre uma peça
        }
        if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
            mat[aux.getRow()][aux.getColumn()] = true; //A posição também é válida caso seja uma peça de oponente

        //Verificando abaixo
        aux.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
            aux.setRow(aux.getRow() + 1); //Caminha para baixo até que encontre uma peça
        }
        if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
            mat[aux.getRow()][aux.getColumn()] = true; //A posição também é válida caso seja uma peça de oponente

        //Verificando esquerda
        aux.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
            aux.setColumn(aux.getColumn() - 1); //Caminha para esquerda até que encontre uma peça
        }
        if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
            mat[aux.getRow()][aux.getColumn()] = true; //A posição também é válida caso seja uma peça de oponente

        //Verificando direita
        aux.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
            aux.setColumn(aux.getColumn() + 1); //Caminha para direita até que encontre uma peça
        }
        if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
            mat[aux.getRow()][aux.getColumn()] = true; //A posição também é válida caso seja uma peça de oponente


        return mat;
    }
}
