package board;

public class Position {
    private int column, row;

    public Position(int row, int column) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int coluna) {
        this.column = coluna;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return row + ", " + column;
    }
}
