public class DiagonalWinningStrategy implements GameWinningStrategy {
    @Override
    public boolean checkWin(Player player, Board board) {
        int size = board.getSize();
        // Check main diagonal
        boolean win = true;
        for (int i = 0; i < size; i++) {
            if (board.checkCell(i, i, player.getSymbol()) == false) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // Check anti-diagonal
        win = true;
        for (int i = 0; i < size; i++) {
            if (board.checkCell(i, size - 1 - i, player.getSymbol()) == false) {
                win = false;
                break;
            }
        }
        if (win) return true;

        return false;
    }
}