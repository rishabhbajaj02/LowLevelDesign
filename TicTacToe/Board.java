public class Board{

    private int size;

    private Cell[][] cells;

    private int moves;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.cells[i][j] = new Cell();
            }
        }
        this.moves = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean checkCell(int i, int j, Symbol symbol) {
        return cells[i][j].getSymbol() == symbol;
    }

    public MoveResult makeMove(int i, int j, Symbol symbol) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            return MoveResult.INVALID_POSITION;
        }

        if (cells[i][j].getSymbol() != null) {
            return MoveResult.CELL_OCCUPIED;
        }

        cells[i][j].setSymbol(symbol);
        moves++;
        return MoveResult.SUCCESS;
    }

    public boolean isFull(){
        return moves == size * size;
    }

    public int getMoves(){
        return moves;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){

                Symbol symbol = cells[i][j].getSymbol();

                if(symbol == null){
                    output.append("_\t");
                    continue;
                }

                output.append(symbol).append("\t");
            }
            output.append(System.lineSeparator());
        }
        return output.toString();
    }

}
