package connect4;

public abstract class Player {
    private char token;
    private String name;

    public Player(char token, String name){
        this.token = token;
        this.name = name;

    }
    public char getToken(){
        return token;
    }

    public String getName() {
        return name;
    }

    abstract public int play(Board board);
}
