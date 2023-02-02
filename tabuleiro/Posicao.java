package tabuleiro;

public class Posicao {
    private  int coluna, linha;

    public Posicao(int linha, int coluna) {
        this.coluna = coluna;
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    @Override
    public String toString() {
        return linha + ", " + coluna;
    }
}
