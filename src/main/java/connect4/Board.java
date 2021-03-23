package connect4;

import java.util.Arrays;
import java.util.List;

public class Board {
    private char[][] grid;

    public Board(){
        this.grid = new char[6][7];
        for (char[] row : grid) {
            Arrays.fill(row, ' ');
        }
    }

    /*
    board[0].length = 7,   board.length=6
    column is between 0-6, row is between 0-5
     */

    public int placeToken(char playerToken, int column) {
        for (int row = grid.length-1; row>=0; row--) {
            if (grid[row][column] == ' '){
                grid[row][column] = playerToken;
                return row;
            }
        }
        throw new IllegalStateException("Column " + (column + 1) + " is full.");
    }

    public boolean isColumnFull(int column) {
        return grid[0][column] != ' ' ;
    }

    public char[][] asGrid() {
        return grid;
    }
}

