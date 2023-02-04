package chess;

import board.Board;
import board.Piece;
import board.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Board board;
    private Color currPalyer;
    private int turn;
    private List<Piece> piecesOnBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<Piece>();
    private boolean check, checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

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
    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
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

        ChessPiece movedPiece = (ChessPiece) board.piece(target);

        //Promoção peão
        promoted = null;
        if(movedPiece instanceof Pawn) {
            if((movedPiece.getColor() == Color.BRANCAS && target.getRow() == 0) || (movedPiece.getColor() == Color.PRETAS && target.getRow() == 7)){
                promoted = (ChessPiece) board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }

        check = (testeCheck(opponent(currPalyer)));

        if(testeCheckMate(opponent(currPalyer)))
            checkMate = true;
        else
            nexTurn();
        if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() + 2 || target.getRow() == source.getRow() - 2))
            enPassantVulnerable = movedPiece;
        else
            enPassantVulnerable = null;

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

        //Castling - roque pequeno
        if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceRook = new Position(source.getRow(), 7);
            Position targetRook = new Position(source.getRow(), 5);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increaseMoveCount();
        } else if(p instanceof King && target.getColumn() == source.getColumn() - 2) { //Castling - roque grande
            Position sourceRook = new Position(source.getRow(), 0);
            Position targetRook = new Position(source.getRow(), 3);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increaseMoveCount();
        }

        //En Passant
        if(p instanceof Pawn) {
            if(source.getColumn() != target.getColumn() && captured == null) {
                Position removedPawnPosition;
                if(p.getColor() == Color.BRANCAS)
                    removedPawnPosition = new Position(target.getRow() + 1, target.getColumn());
                else
                    removedPawnPosition = new Position(target.getRow() - 1, target.getColumn());

                captured = board.removePiece(removedPawnPosition);
                capturedPieces.add(captured);
                piecesOnBoard.remove(captured);
            }
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
        //Castling - roque pequeno
        if(piece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceRook = new Position(source.getRow(), 7);
            Position targetRook = new Position(source.getRow(), 5);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
            board.placePiece(rook, sourceRook);
            rook.decreaseMoveCount();
        } else if(piece instanceof King && target.getColumn() == source.getColumn() - 2) { //Castling - roque grande
            Position sourceRook = new Position(source.getRow(), 0);
            Position targetRook = new Position(source.getRow(), 3);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
            board.placePiece(rook, sourceRook);
            rook.decreaseMoveCount();
        }

        //En Passant
        if(piece instanceof Pawn) {
            if(source.getColumn() != target.getColumn() && captured == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position removedPawnPosition;
                if(piece.getColor() == Color.BRANCAS)
                    removedPawnPosition = new Position(3, target.getColumn());
                else
                    removedPawnPosition = new Position(4, target.getColumn());

                board.placePiece(pawn, removedPawnPosition);
            }
        }
    }

    public ChessPiece replacePromotedPiece(String tupe) {
        if(promoted == null)
            throw new IllegalStateException("Não há peça para ser promovida");
        else if (!tupe.equals("B") && !tupe.equals("Q") && !tupe.equals("N") && !tupe.equals("R"))
            return promoted;

        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        piecesOnBoard.remove(p);

        ChessPiece newPiece = newPiece(tupe, promoted.getColor());
        board.placePiece(newPiece, pos);
        piecesOnBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Color color) {

        if(type.equals("B"))
            return new Bishop(board, color);
        else if(type.equals("N"))
            return new Knight(board, color);
        else if(type.equals("Q"))
            return new Queen(board, color);
        else
            return new Rook(board, color);
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
        List<Piece> list = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if(p instanceof King)
                return (ChessPiece) p;
        }

        throw new IllegalStateException("NÃO ENCONTRADO REI " + color + " NO TABULEIRO");
    }

    private boolean testeCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentePieces = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
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
            List<Piece> list = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
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
        placeNewPiece('e', 1, new King(board, Color.BRANCAS, this));
        placeNewPiece('e', 8, new King(board, Color.PRETAS, this));

        //Inicializando Rainhas
        placeNewPiece('d', 1, new Queen(board, Color.BRANCAS));
        placeNewPiece('d', 8, new Queen(board, Color.PRETAS));

        //Inicializando Torres
        placeNewPiece('a', 1, new Rook(board, Color.BRANCAS));
        placeNewPiece('h', 1, new Rook(board, Color.BRANCAS));
        placeNewPiece('a', 8, new Rook(board, Color.PRETAS));
        placeNewPiece('h', 8, new Rook(board, Color.PRETAS));

        //Inicializando Peões
        placeNewPiece('a', 2, new Pawn(board, Color.BRANCAS,this));
        placeNewPiece('b', 2, new Pawn(board, Color.BRANCAS, this));
        placeNewPiece('c', 2, new Pawn(board, Color.BRANCAS, this));
        placeNewPiece('d', 2, new Pawn(board, Color.BRANCAS, this));
        placeNewPiece('e', 2, new Pawn(board, Color.BRANCAS, this));
        placeNewPiece('f', 2, new Pawn(board, Color.BRANCAS, this));
        placeNewPiece('g', 2, new Pawn(board, Color.BRANCAS, this));
        placeNewPiece('h', 2, new Pawn(board, Color.BRANCAS, this));
        placeNewPiece('a', 7, new Pawn(board, Color.PRETAS, this));
        placeNewPiece('b', 7, new Pawn(board, Color.PRETAS, this));
        placeNewPiece('c', 7, new Pawn(board, Color.PRETAS, this));
        placeNewPiece('d', 7, new Pawn(board, Color.PRETAS, this));
        placeNewPiece('e', 7, new Pawn(board, Color.PRETAS, this));
        placeNewPiece('f', 7, new Pawn(board, Color.PRETAS, this));
        placeNewPiece('g', 7, new Pawn(board, Color.PRETAS, this));
        placeNewPiece('h', 7, new Pawn(board, Color.PRETAS, this));

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