package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Scanner;

public class Program {
    public static void main(String []args) {

        ChessMatch match = new ChessMatch();
        Scanner sc = new Scanner(System.in);

        while (true) {

            UI.printBoard(match.getPieces()); //Printa o Tabuleiro

            //Le a posição de origem e destino
            System.out.println();
            System.out.println("Source: ");
            ChessPosition source = UI.readChessPosition(sc);
            System.out.println();
            System.out.println("Target: ");
            ChessPosition target = UI.readChessPosition(sc);

            ChessPiece captured = match.performChessMove(source, target); //Executa a jogada
        }
    }
}
