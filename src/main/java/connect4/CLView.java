package connect4;


public class CLView implements View {
    @Override
    public void display(Board board) {
        StringBuilder s = new StringBuilder();
        char[][] grid = board.asGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char c = grid[i][j];
                s.append("| ").append(c).append(" ");
            }
            s.append(("|") + "   ").append(i + 1).append("\n");
        }
        s.append("  1   2   3   4   5   6   7\n");

        System.out.println(s);
    }

    @Override
    public void announceWinner(Player winner) {
        System.out.println(winner.getName()  +  " won!!!");
    }
}
