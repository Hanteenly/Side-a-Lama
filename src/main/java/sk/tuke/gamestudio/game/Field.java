package sk.tuke.gamestudio.game;

public class Field {
    private Tile[][] tiles;
    private GameState state = GameState.PLAYING;

    public GameState getState() {
        return state;
    }
}
