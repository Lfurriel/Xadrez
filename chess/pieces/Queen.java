package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean [][]mat = new boolean[getBoard().getRow()][getBoard().getColumn()];

        Position aux = new Position(0, 0);

        //Verificando diagonal cima direita
        aux.setValues(position.getRow() - 1, position.getColumn() + 1 );
        while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
            aux.setValues(aux.getRow() - 1, aux.getColumn() + 1);
        }
        if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
            mat[aux.getRow()][aux.getColumn()] = true; //A posição também é válida caso seja uma peça de oponente

        //Verificando cima esquerda
        aux.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
            aux.setValues(aux.getRow() - 1, aux.getColumn() - 1);
        }
        if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
            mat[aux.getRow()][aux.getColumn()] = true; //A posição também é válida caso seja uma peça de oponente

        //Verificando baixo direita
        aux.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
            aux.setValues(aux.getRow() + 1, aux.getColumn() + 1);
        }
        if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
            mat[aux.getRow()][aux.getColumn()] = true; //A posição também é válida caso seja uma peça de oponente

        //Verificando baixo esquerda
        aux.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
            aux.setValues(aux.getRow() + 1, aux.getColumn() - 1);
        }
        if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
            mat[aux.getRow()][aux.getColumn()] = true; //A posição também é válida caso seja uma peça de oponente

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

    @Override
    public String toString() {
        return "Q";
    }
}
