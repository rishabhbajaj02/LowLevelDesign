package tictactoe.GameWinningStrategy;

import tictactoe.Board;
import tictactoe.Player;

public interface GameWinningStrategy {
    boolean checkWin(Player player, Board board);
}
