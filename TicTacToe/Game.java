import java.util.List;
import java.util.ArrayList;

public class Game{
    private Board board;

    private Player playerX;

    private Player playerO;

    private Player winner;
    
    private GameState gameState;
    
    private Player currentPlayer;

    private List<GameWinningStrategy> winningStrategies;

    public Game(int size){
        board = new Board(size);
        playerX = new Player(Symbol.X);
        playerO = new Player(Symbol.O);

        currentPlayer = playerX;

        winner = null;

        gameState = GameState.IN_PROGRESS;

        winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColumnWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());
    }

    public GameState getGameState(){
        return gameState;
    }

    public Player getWinner(){
        return winner;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean makeMove(int row, int col){
        if(gameState == GameState.WON || gameState == GameState.DRAW){
            System.out.println("Game is already over.");
            return false;
        }
        boolean moveMade = board.makeMove(row, col, currentPlayer.getSymbol());

        if(!moveMade){
            return false;
        }

        gameState = GameState.IN_PROGRESS;
        if(checkWin(currentPlayer)){
            return true;
        }else{
            currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        }

        return moveMade;
    }


    public void printBoard(){
        board.printBoard();
    }

    public boolean checkWin(Player player){
        
        for (GameWinningStrategy strategy : winningStrategies) {
            if (strategy.checkWin(player, board)) {
                gameState = GameState.WON;
                winner = player;
                return true;
            }
        }

        if (board.isFull()) {
            gameState = GameState.DRAW;
        }

        return false;
    }
}