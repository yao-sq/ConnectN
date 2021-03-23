package connect4;

import java.util.Random;

public class ComputerPlayer extends Player {
    Random rand;
    public ComputerPlayer(char token, String name){
        super(token, name);
        rand = new Random();
    }
    @Override
    //generate a random number between 0-6, return a move between 1-7
    public int play(Board board) {
        while (true) {
            int chosenColumn = rand.nextInt(7);
            if (board.isColumnFull(chosenColumn)) {
                continue;
            }
            return chosenColumn;
        }
    }
}
