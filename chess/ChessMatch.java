package chess;

import board.Board;
import board.Piece;
import board.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRow()][board.getColumn()];
        for (int i=0; i<board.getRow(); i++) {
            for (int j=0; j<board.getColumn(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition source) {
       Position pos = source.toPosition();
       validateSourcePosition(pos);
       return board.piece(pos).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition from, ChessPosition to) {
        Position source = from.toPosition();
        Position target = to.toPosition();

        validateSourcePosition(source);
        validateTargetPosition(source, target);

        Piece capturedPiece = makeMove(source, target);

        return (ChessPiece)capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source); // Remove da origem
        Piece captured = board.removePiece(target); // Remove uma possível peça do destino
        board.placePiece(p, target);

        return captured;
    }

    private void validateSourcePosition(Position pos) {
        if(!board.thereIsAPiece(pos))
            throw new ChessException("NÃO EXISTE PEÇA NA POSIÇÃO DE ORIGEM");
        if(!board.piece(pos).isThereAnyMove())
            throw new ChessException("NÃO EXISTE MOVIMENTO POSSÍVEL PARA ESSA PEÇA");
    }

    private void validateTargetPosition(Position source, Position target) {
        if(!board.piece(source).possibleMove(target))
            throw new ChessException("NÃO PODE SE MOVER PARA ESSA POSIÇÃO");
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        //Inicializando Torres
        placeNewPiece('a', 1, new Rook(board, Color.BLACK));
        placeNewPiece('h', 1, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new Rook(board, Color.WHITE));
        placeNewPiece('h', 8, new Rook(board, Color.WHITE));

        //Inicializando Reis
        placeNewPiece('e', 1, new King(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.WHITE));

    }
}