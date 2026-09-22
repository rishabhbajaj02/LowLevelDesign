package tictactoe;

public class Cell{
    private Symbol symbol;
    public Cell() {
        this.symbol = null;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
