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

    public boolean makeMove(int i, int j, Symbol symbol) {

        if(cells[i][j].getSymbol() == null){
            cells[i][j].setSymbol(symbol);
            moves++;
        }else{
            System.out.println("Cell is already occupied");
            return false;
        }
        return true;
    }

    public boolean isFull(){
        return moves == size * size;
    }

    public int getMoves(){
        return moves;
    }

    public void printBoard(){

        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){

                Symbol symbol = cells[i][j].getSymbol();

                if(symbol == null){
                    System.out.print("_\t");
                    continue;
                }

                System.out.print(cells[i][j].getSymbol() + "\t");
            }
            System.out.println();
        }

    }

}