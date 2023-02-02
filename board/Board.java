package board;

public class Board {

    private int rows, columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if(rows < 1 || columns < 1)
            throw new BoardException("ERRO AO CRIAR TABULEIRO");
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRow() {
        return rows;
    }


    public int getColumn() {
        return columns;
    }


    public Piece piece(int row, int column) {
        if(!positionExists(row, column))
            throw new BoardException("POSIÇÃO NÃO ESTÁ NO TABULEIRO");
        return pieces[row][column];
    }

    public Piece piece(Position pos) {
        if(!positionExists(pos))
            throw new BoardException("POSIÇÃO NÃO ESTÁ NO TABULEIRO");
        return pieces[pos.getRow()][pos.getColumn()];
    }

    public void placePiece(Piece piece, Position pos) {
        if(thereIsAPiece(pos))
            throw new BoardException("JÁ EXISTE UMA PEÇA NESSA POSIÇÃO");
        pieces[pos.getRow()][pos.getColumn()] = piece;
        piece.position = pos;
    }

    public boolean positionExists(int row, int column) {
        return row >=0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position pos) {
        return positionExists(pos.getRow(), pos.getColumn());
    }

    public boolean thereIsAPiece(Position pos) {
        if(!positionExists(pos))
            throw new BoardException("POSIÇÃO NÃO ESTÁ NO TABULEIRO");
        return piece(pos) != null;
    }

}
