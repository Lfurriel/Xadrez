package board;

public class Position {
    private  int column, row;

    public Position(int linha, int coluna) {
        this.column = coluna;
        this.row = linha;
    }

    public int getColuna() {
        return column;
    }

    public void setColuna(int coluna) {
        this.column = coluna;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return row + ", " + column;
    }
}
