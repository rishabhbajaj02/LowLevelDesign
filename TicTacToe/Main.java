
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        System.out.println("Welcome to Tic Tac Toe!");

        Game game = new Game(3);
        Scanner sc = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> sc.close()));

        while (game.getGameState() == GameState.IN_PROGRESS) {
            game.printBoard();

            System.out.println(
                "Player " + game.getCurrentPlayer().getSymbol() + "'s turn."
            );

            int row = sc.nextInt();
            int col = sc.nextInt();

            if (!game.makeMove(row, col)) {
                System.out.println("Invalid move.");
            }
        }

        game.printBoard();

        if (game.getGameState() == GameState.WON) {
            System.out.println("Player " + game.getWinner().getSymbol() + " has won!");
        } else {
            System.out.println("The game is a draw.");
        }
    }
}