package connect4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Game {
    private List<Player> players;
    private int winSize;
    private Board board = new Board();
    private View view = new CLView();

    /**
     * Expects one argument: N
     * If N is not provided, a Connect4 game will be configured.
     * If N is provided, a ConnectN game will be configured, with 3 players and needing N in a row to win.
     * @param args
     */
    public static void main(String[] args){
        Game game = args.length > 0 ? configureConnectNGame(Integer.parseInt(args[0])) : configureConnect4Game();

        System.out.printf("The game has set the number of players to %d, and win size to %d%n",
                game.getNumberOfPlayers(), game.getWinSize());

        game.playGame();
    }

    private static Game configureConnectNGame(int n) {
        Game game = new Game();
        game.setNumberOfPlayers(3);
        game.setWinSize(n);
        return game;
    }

    private static Game configureConnect4Game() {
        Game game = new Game();
        game.setNumberOfPlayers(2);
        game.setWinSize(4);
        return game;
    }

    private static Game configureGame() {
        Game game = new Game();
        retryingRead("Type number of players - a number between 2 and 3: ", game::setNumberOfPlayers);
        retryingRead("Type winSize - a number between 3 and 6: ", game::setWinSize );
        return game;
    }

    private Game(){
    }

    public Game(int numberOfPlayers, List<Player> players, int winSize) {
        setNumberOfPlayers(numberOfPlayers);
        setWinSize(winSize);
    }

    public void setNumberOfPlayers(int numberOfPlayers){
        if (numberOfPlayers < 2 || numberOfPlayers > 3) {
            throw new IllegalArgumentException("numberOfPlayers must be between 2 and 3 (inclusive)");
        }

        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer('X', "Player 1 (Human)"));
        players.add(new ComputerPlayer('O', "Player 2 (Bot)"));

        if (numberOfPlayers == 3){
            players.add(new ComputerPlayer('A', "Player 3 (Bot)"));
        }

        setPlayers(players);
    }

    public void setPlayers(List<Player> players) {
        if (players.size() < 2) {
            throw new IllegalArgumentException("There should be at least 2 players");
        }
        this.players = players;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        setNumberOfPlayers(Integer.parseInt(numberOfPlayers));
    }

    public void setWinSize(int winSize){
        if (winSize < 3 || winSize > 6) {
            throw new IllegalArgumentException("winSize must be between 3 and 6 (inclusive)");
        }
        this.winSize = winSize;
    }

    public void setWinSize(String winSize){
        setWinSize(Integer.parseInt(winSize));
    }

    protected int getNumberOfPlayers(){
        return players.size();
    }

    protected int getWinSize(){
        return winSize;
    }


    protected boolean checkVictory(Player player) {
        char token = player.getToken();
        return checkHorizontal(token, winSize)
                || checkVertical(token, winSize)
                || checkDiagonalRising(token, winSize)
                || checkDiagonalFalling(token, winSize);
    }

    private boolean checkHorizontal(char playerToken, int winSize) {
        char[][] grid = board.asGrid();
        for (int i = 0; i < grid.length; i++) {
            if (checkLine(playerToken, winSize, i, 0, 1, 0)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkVertical(char playerToken, int winSize) {
        char[][] grid = board.asGrid();
        for (int i = 0; i < grid[0].length; i++) {
            if (checkLine(playerToken, winSize, 0, i, 0, 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalFalling(char playerToken, int winSize) {
        char[][] grid = board.asGrid();
        for (int i = 0; i < grid.length; i++) {
            if (checkLine(playerToken, winSize, i, 0, 1, -1)) {
                return true;
            }
        }

        for (int j = 0; j < grid[0].length; j++) {
            if (checkLine(playerToken, winSize, 5, j, 1, -1)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalRising(char playerToken, int winSize) {
        char[][] grid = board.asGrid();
        for (int i = 0; i < grid.length; i++) {
            if (checkLine(playerToken, winSize,  i, 0, 1, 1)){
                return true;
            }
        }

        for (int j = 0; j < grid[0].length; j++) {
            if (checkLine(playerToken, winSize, 0, j, 1, 1)){
                return true;
            }
        }
        return false;
    }

    private boolean checkLine(char playerToken, int winSize, int row, int col, int xStep, int yStep) {
        char[][] grid = board.asGrid();
        int count = 0;
        while ( row>=0 && row < grid.length && col>=0 && col < grid[row].length) {
            if (grid[row][col] == playerToken) {
                count = count + 1;
                if (count >= winSize) {
                    return true;
                }
            } else {
                count = 0;
            }
            row += yStep;
            col += xStep;
        }
        return false;
    }

    public void playGame() {
        view.display(board);

        Player currentPlayer = players.get(0);

        while (true){
            Player player = currentPlayer;

            //board works with 0-6 (column) and 0-5 (row) with all its method
            board.placeToken(player.getToken(), player.play(board));

            view.display(board);


            if (checkVictory(player)){
                view.announceWinner(player);
                break;
            }

            currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
        }
    }

    private static String readInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new IllegalStateException("Could not read a line: " + e.getMessage(), e);
        }
    }

    private static String retryingRead(String prompt, Consumer<String> setter) {
        while (true) {
            try {
                System.out.println(prompt);
                String input = readInput();
                setter.accept(input);
                return input;
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
            }
        }
    }

}
