package sk.tuke.gamestudio.game;

public class Game {
    private Board board;
    private GameState state;
    private int rows;
    private int cols;

    public Game(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new Board(rows, cols);
        this.state = GameState.PLAYING;
    }
}
