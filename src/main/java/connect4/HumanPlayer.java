package connect4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanPlayer extends Player {
    private BufferedReader in;

    public HumanPlayer(char token, String name){
        super(token, name);
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    // human player should come up with an input between 1-7 as there are 7 columns in total
    public int play(Board board) {
        while (true) {
            try {
                int chosenColumn = Integer.parseInt(in.readLine()) - 1;
                if (chosenColumn < 0 || chosenColumn > 6) {
                    System.out.println("chosenColumn must be between 1 and 7 (inclusive)");
                    continue;
                }
                if (board.isColumnFull(chosenColumn)) {
                    System.out.println("Column is full. Try another column.");
                    continue;
                }
                return chosenColumn;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, try again" + e);
            } catch (IOException e) {
                System.err.println("Failed to read input, please try again.");
            }
        }
    }
}
