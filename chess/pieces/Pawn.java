package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        int i, j;
        boolean [][]mat = new boolean[getBoard().getRow()][getBoard().getColumn()];

        Position aux = new Position(0, 0);


        if(getColor() == Color.BRANCAS) { //Brancas

            aux.setValues(position.getRow() - 1, position.getColumn());
            if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux))
                mat[aux.getRow()][aux.getColumn()] = true;
            aux.setValues(position.getRow() - 2, position.getColumn());
            Position aux2 = new Position(position.getRow() - 1, position.getColumn());
            if(getBoard().positionExists(aux) && getBoard().positionExists(aux2) && !getBoard().thereIsAPiece(aux)
                    && !getBoard().thereIsAPiece(aux2) && getMoveCount() == 0)
                mat[aux.getRow()][aux.getColumn()] = true;

            //diagonais
            aux.setValues(position.getRow() - 1, position.getColumn() - 1);
            if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
                mat[aux.getRow()][aux.getColumn()] = true;
            aux.setValues(position.getRow() - 1, position.getColumn() + 1);
            if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
                mat[aux.getRow()][aux.getColumn()] = true;

            //EnPassant
            if(position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn()-1);
                Position right = new Position(position.getRow(), position.getColumn()+1);
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable())
                    mat[left.getRow()-1][left.getColumn()] = true;
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable())
                    mat[right.getRow()-1][right.getColumn()] = true;
            }

        } else { //Pretas

            aux.setValues(position.getRow() + 1, position.getColumn());
            if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux))
                mat[aux.getRow()][aux.getColumn()] = true;
            aux.setValues(position.getRow() + 2, position.getColumn());
            Position aux2 = new Position(position.getRow() + 1, position.getColumn());
            if(getBoard().positionExists(aux) && getBoard().positionExists(aux2) && !getBoard().thereIsAPiece(aux)
                    && !getBoard().thereIsAPiece(aux2) && getMoveCount() == 0)
                mat[aux.getRow()][aux.getColumn()] = true;

            //diagonais
            aux.setValues(position.getRow() + 1, position.getColumn() - 1);
            if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
                mat[aux.getRow()][aux.getColumn()] = true;
            aux.setValues(position.getRow() + 1, position.getColumn() + 1);
            if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
                mat[aux.getRow()][aux.getColumn()] = true;

            //EnPassant
            if(position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn()-1);
                Position right = new Position(position.getRow(), position.getColumn()+1);
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable())
                    mat[left.getRow()+1][left.getColumn()] = true;
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable())
                    mat[right.getRow()+1][right.getColumn()] = true;
            }

        }


        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
