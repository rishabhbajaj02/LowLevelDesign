package tictactoe;

import tictactoe.Notifier.Scoreboard;

public class TicTacToeSystem {

    private Game game;

    private final Scoreboard scoreboard;
    
    private static TicTacToeSystem instance;

    public static synchronized TicTacToeSystem getInstance() {
        if (instance == null) {
            instance = new TicTacToeSystem();
        }
        return instance;
    }
    
    public TicTacToeSystem() {
        this.scoreboard = new Scoreboard();
    }

    public Game createGame(String a, String b) {
        this.game = new Game(a, b);
        this.game.registerObserver(scoreboard);
        return this.game;
    }

    public void printScoreBoard() {
        scoreboard.printScores();
    }
}
