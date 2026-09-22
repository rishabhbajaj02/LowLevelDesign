import java.util.List;
import java.util.ArrayList;

public class Game extends Subject{
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

    public MoveResult makeMove(int row, int col){
        if(gameState == GameState.WON || gameState == GameState.DRAW){
            return MoveResult.GAME_OVER;
        }
        MoveResult moveResult = board.makeMove(row, col, currentPlayer.getSymbol());

        if (moveResult != MoveResult.SUCCESS) {
            return moveResult;
        }

        gameState = GameState.IN_PROGRESS;
        if(checkWin(currentPlayer)){
            return MoveResult.SUCCESS;
        }else{
            currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        }

        return MoveResult.SUCCESS;
    }


    public String getBoardView(){
        return board.toString();
    }

    private boolean checkWin(Player player){
        
        for (GameWinningStrategy strategy : winningStrategies) {
            if (strategy.checkWin(player, board)) {
                gameState = GameState.WON;
                winner = player;
                this.notifyObservers();
                return true;
            }
        }

        if (board.isFull()) {
            gameState = GameState.DRAW;
        }

        return false;
    }
}
