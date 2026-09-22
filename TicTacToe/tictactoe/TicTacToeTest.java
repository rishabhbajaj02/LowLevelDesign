package tictactoe;

public class TicTacToeTest {
    public static void main(String[] args) {
        Game game = new Game("Player X", "Player O");

        expect(MoveResult.SUCCESS, game.makeMove(0, 0));
        expect(MoveResult.CELL_OCCUPIED, game.makeMove(0, 0));
        expect(MoveResult.INVALID_POSITION, game.makeMove(-1, 0));

        Game completedGame = new Game("Player X", "Player O");
        completedGame.makeMove(0, 0);
        completedGame.makeMove(1, 0);
        completedGame.makeMove(0, 1);
        completedGame.makeMove(1, 1);
        completedGame.makeMove(0, 2);

        expect(MoveResult.GAME_OVER, completedGame.makeMove(2, 2));

        System.out.println("All tests passed.");
    }

    private static void expect(MoveResult expected, MoveResult actual) {
        if (expected != actual) {
            throw new AssertionError(
                "Expected " + expected + " but got " + actual
            );
        }
    }
}
