package chess;

import board.Board;
import board.Piece;
import board.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    private Board board;
    private Color currPalyer;
    private int turn;
    private List<Piece> piecesOnBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<Piece>();
    private boolean check, checkMate;

    public ChessMatch() {
        board = new Board(8, 8);
        currPalyer = Color.BRANCAS;
        turn = 1;
        initialSetup();
    }

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPalyer() {
        return currPalyer;
    }

    public boolean getCheck() {
        return check;
    }
    public boolean getCheckMate() {
        return checkMate;
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

        if(testeCheck(currPalyer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("VOCÊ NÃO PODE SE COLOCAR EM XEQUE!");
        }

        check = (testeCheck(opponent(currPalyer)));

        if(testeCheckMate(opponent(currPalyer)))
            checkMate = true;
        else
            nexTurn();

        return (ChessPiece)capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source); // Remove da origem
        p.increaseMoveCount();
        Piece captured = board.removePiece(target); // Remove uma possível peça do destino
        board.placePiece(p, target);

        if(captured != null) {
            piecesOnBoard.remove(captured);
            capturedPieces.add(captured);
        }

        return captured;
    }

    private void undoMove(Position source, Position target, Piece captured) {
        ChessPiece piece = (ChessPiece) board.removePiece(target);
        piece.decreaseMoveCount();
        board.placePiece(piece, source);
        if(captured != null) {
            board.placePiece(captured, target);
            piecesOnBoard.add(captured);
            capturedPieces.remove((captured));
        }
    }

    private void validateSourcePosition(Position pos) {
        if(!board.thereIsAPiece(pos))
            throw new ChessException("NÃO EXISTE PEÇA NA POSIÇÃO DE ORIGEM");
        if(currPalyer != ((ChessPiece) board.piece(pos)).getColor())
            throw new ChessException("A PEÇA ESCOLHIDA É DO ADVERSÁRIO");
        if(!board.piece(pos).isThereAnyMove())
            throw new ChessException("NÃO EXISTE MOVIMENTO POSSÍVEL PARA ESSA PEÇA");
    }

    private void validateTargetPosition(Position source, Position target) {
        if(!board.piece(source).possibleMove(target))
            throw new ChessException("NÃO PODE SE MOVER PARA ESSA POSIÇÃO");
    }

    private void nexTurn() {
        turn++;
        if(turn % 2 == 0) //Turnos pares - Pretas
            currPalyer = Color.PRETAS;
        else //Turnos ímpares - Brancas
            currPalyer = Color.BRANCAS;
    }

    private Color opponent(Color color) {
        return (color == Color.BRANCAS) ? Color.PRETAS : Color.BRANCAS; //Ternário SE ? ENTÃO : SENÃO
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();
        for (Piece p : list) {
            if(p instanceof King)
                return (ChessPiece) p;
        }

        throw new IllegalStateException("NÃO ENCONTRADO REI " + color + " NO TABULEIRO");
    }

    private boolean testeCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentePieces = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).toList();
        for (Piece p : opponentePieces) {
            boolean [][]mat = p.possibleMoves();
            if(mat[kingPosition.getRow()][kingPosition.getColumn()])
                return true;
        }
        return false;
    }

    private boolean testeCheckMate(Color color) {
        /* Testa movimento de todas as peças para ver se um movimento é capaz de tirar o rei do xeque*/
        if(!testeCheck(color))
            return false;
        else {
            int i, j;
            Position kingPosition = king(color).getChessPosition().toPosition();
            List<Piece> list = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();
            for (Piece p : list) {
                boolean [][]mat = p.possibleMoves();
                for (i = 0; i < board.getRow(); i++) {
                    for (j = 0; j < board.getColumn(); j++) {
                        if(mat[i][j]) {
                            //A lógica realiza um movimento temporario e valida se o rei ainda está em xeque
                            Position source = ((ChessPiece) p).getChessPosition().toPosition();
                            Position target = new Position(i, j);
                            Piece capturedPiece = makeMove(source, target);
                            boolean testCheck = testeCheck(color);
                            undoMove(source, target, capturedPiece);
                            if(!testCheck)
                                return false;
                        }
                    }
                }

            }
            return true;

        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnBoard.add(piece);
    }

    private void initialSetup() {

        //Inicializando Reis
        placeNewPiece('e', 1, new King(board, Color.BRANCAS));
        placeNewPiece('e', 8, new King(board, Color.PRETAS));

        //Inicializando Rainhas
        placeNewPiece('d', 1, new Queen(board, Color.BRANCAS));
        placeNewPiece('d', 8, new Queen(board, Color.PRETAS));

        //Inicializando Torres
        placeNewPiece('a', 1, new Rook(board, Color.BRANCAS));
        placeNewPiece('h', 1, new Rook(board, Color.BRANCAS));
        placeNewPiece('a', 8, new Rook(board, Color.PRETAS));
        placeNewPiece('h', 8, new Rook(board, Color.PRETAS));

        //Inicializando Peões
        placeNewPiece('a', 2, new Pawn(board, Color.BRANCAS));
        placeNewPiece('b', 2, new Pawn(board, Color.BRANCAS));
        placeNewPiece('c', 2, new Pawn(board, Color.BRANCAS));
        placeNewPiece('d', 2, new Pawn(board, Color.BRANCAS));
        placeNewPiece('e', 2, new Pawn(board, Color.BRANCAS));
        placeNewPiece('f', 2, new Pawn(board, Color.BRANCAS));
        placeNewPiece('g', 2, new Pawn(board, Color.BRANCAS));
        placeNewPiece('h', 2, new Pawn(board, Color.BRANCAS));
        placeNewPiece('a', 7, new Pawn(board, Color.PRETAS));
        placeNewPiece('b', 7, new Pawn(board, Color.PRETAS));
        placeNewPiece('c', 7, new Pawn(board, Color.PRETAS));
        placeNewPiece('d', 7, new Pawn(board, Color.PRETAS));
        placeNewPiece('e', 7, new Pawn(board, Color.PRETAS));
        placeNewPiece('f', 7, new Pawn(board, Color.PRETAS));
        placeNewPiece('g', 7, new Pawn(board, Color.PRETAS));
        placeNewPiece('h', 7, new Pawn(board, Color.PRETAS));

        //Inicializando Bispos
        placeNewPiece('c', 1, new Bishop(board, Color.BRANCAS));
        placeNewPiece('f', 1, new Bishop(board, Color.BRANCAS));
        placeNewPiece('c', 8, new Bishop(board, Color.PRETAS));
        placeNewPiece('f', 8, new Bishop(board, Color.PRETAS));

        //Inicializando Cavalos
        placeNewPiece('b', 1, new Knight(board, Color.BRANCAS));
        placeNewPiece('g', 1, new Knight(board, Color.BRANCAS));
        placeNewPiece('b', 8, new Knight(board, Color.PRETAS));
        placeNewPiece('g', 8, new Knight(board, Color.PRETAS));



    }
}