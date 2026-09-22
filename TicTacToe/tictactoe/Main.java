
package tictactoe;

import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        System.out.println("Welcome to Tic Tac Toe!");
        TicTacToeSystem system = TicTacToeSystem.getInstance();

        playGame(system.createGame("John", "Rishabh"));
        playGame(system.createGame("Alice", "Bob"));
        playGame(system.createGame("Ryan", "Jake"));

        system.printScoreBoard();
    }

    private static void playGame(Game game) {
        while (game.getGameState() == GameState.IN_PROGRESS) {
            System.out.print(game.getBoardView());

            System.out.println(
                "Player " + game.getCurrentPlayer().getName() + " (" + game.getCurrentPlayer().getSymbol() + ")'s turn."
            );

            Scanner sc = new Scanner(System.in);
            int row = sc.nextInt();
            int col = sc.nextInt();

            MoveResult result = game.makeMove(row, col);
            if (result != MoveResult.SUCCESS) {
                System.out.println(messageFor(result));
            }
        }

        System.out.print(game.getBoardView());

        if (game.getGameState() == GameState.WON) {
            System.out.println("Player " + game.getWinner().getName() + " (" + game.getWinner().getSymbol() + ") has won!");
        } else {
            System.out.println("The game is a draw.");
        }
    }

    private static String messageFor(MoveResult result) {
        switch (result) {
            case INVALID_POSITION:
                return "Position is outside the board.";
            case CELL_OCCUPIED:
                return "Cell is already occupied.";
            case GAME_OVER:
                return "Game is already over.";
            default:
                return "Invalid move.";
        }
    }
}
