package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String []args) {

        ChessMatch match = new ChessMatch();
        Scanner sc = new Scanner(System.in);
        List<ChessPiece> captured = new ArrayList<>();

        while (!match.getCheckMate()) {
            try {
                UI.clearScreen();

                UI.printMatch(match, captured); //Printa o Tabuleiro

                //Le a posição de origem e destino
                System.out.print("\nORIGEM: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean [][]mat = match.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(match.getPieces(), mat);

                System.out.print("\nDESTINO: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = match.performChessMove(source, target); //Executa a jogada

                if(capturedPiece != null)
                        captured.add(capturedPiece);

            } catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }

        }

        UI.clearScreen();
        UI.printMatch(match, captured);
    }
}
