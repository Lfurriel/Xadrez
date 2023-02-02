package tabuleiro;

public class Board {

    private int row, column;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        this.row = rows;
        this.column = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Piece piece(int linha, int coluna) {
        return pieces[linha][coluna];
    }

    public Piece piece(Position pos) {
        return pieces[pos.getRow()][pos.getColuna()];
    }


}
