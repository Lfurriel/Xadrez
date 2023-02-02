package chess;

import board.Position;

public class ChessPosition {

    private char column;
    private int row;

    public ChessPosition(char column, int row) {
        if(column < 'a' || column > 'h' || row < 0 || row > 8)
            throw new ChessException("ERRO INSTANCIANDO CHESS POSITION");
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    protected Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    protected static ChessPosition fromPosition(Position pos) {
        return new ChessPosition((char)('a' - pos.getColumn()), 8 - pos.getRow());
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
