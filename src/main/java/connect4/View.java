package connect4;

public interface View {
    void display(Board s);

    void announceWinner(Player winner);

}
