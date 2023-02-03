package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;
    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position pos) {
        ChessPiece piece = (ChessPiece) getBoard().piece(pos);
        return piece == null || piece.getColor() != getColor();
    }

    private boolean rookCastling(Position pos) {
        ChessPiece piece = (ChessPiece) getBoard().piece(pos);
        return piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
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

        //Castling - Roque
        if(getMoveCount() == 0 && !chessMatch.getCheck()) {
            //Roque do rei (pequeno)
            Position rook1 = new Position(position.getRow(), 7);
            if(rookCastling(rook1)) {
                Position position1 = new Position(position.getRow(), 5);
                Position position2 = new Position(position.getRow(), 6);
                if(getBoard().piece(position1) == null && getBoard().piece(position2) == null)
                    mat[position.getRow()][6] = true;


            }
            //Roque da rainha (grande)
            Position rook2 = new Position(position.getRow(), 0);
            if(rookCastling(rook2)) {
                Position position1 = new Position(position.getRow(), 1);
                Position position2 = new Position(position.getRow(), 2);
                Position position3 = new Position(position.getRow(), 3);
                if(getBoard().piece(position1) == null && getBoard().piece(position2) == null && getBoard().piece(position3) == null)
                    mat[position.getRow()][2] = true;


            }
        }

        return mat;
    }
}
