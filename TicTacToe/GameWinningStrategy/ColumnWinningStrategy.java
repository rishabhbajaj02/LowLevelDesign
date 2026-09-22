public class ColumnWinningStrategy implements GameWinningStrategy {
    @Override
    public boolean checkWin(Player player, Board board) {
        int size = board.getSize();
        for (int j = 0; j < size; j++) {
            boolean win = true;
            for (int i = 0; i < size; i++) {
                if (board.checkCell(i, j, player.getSymbol()) == false) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        return false;
    }
}