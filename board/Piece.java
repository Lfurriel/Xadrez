package board;

public abstract class Piece {

    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves(); //Preenche uma matriz com verdadeiro/falso dos movimentos possíveis

    public boolean possibleMove(Position pos) {
        return possibleMoves()[pos.getRow()][pos.getColumn()];
    }

    public boolean isThereAnyMove() {
        boolean [][]mat = possibleMoves();
        int i, j;
        for(i = 0; i < mat.length; i++) {
            for (j = 0; j < mat.length; j++) {
                if(mat[i][j])
                    return true;
            }
        }
        return false;
    }
}
