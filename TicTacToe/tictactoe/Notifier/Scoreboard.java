package tictactoe.Notifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import tictactoe.Game;

public class Scoreboard implements Observer {

    private Map<Integer, String> winners;
    private int totalGames;

    public Scoreboard() {
        winners = new ConcurrentHashMap<>();
        totalGames = 0;
    }

    @Override
    public void update(Game game) {
        if (game.getWinner() != null) {
            winners.put(totalGames, game.getWinner().getName());
            totalGames++;
        }
    }

    public void printScores() {
        System.out.println("Total Games: " + totalGames);
        for (Map.Entry<Integer, String> entry : winners.entrySet()) {
            System.out.println("Game " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
