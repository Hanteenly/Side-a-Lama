package sk.tuke.gamestudio.service;

public class GameStateException extends RuntimeException {
    public GameStateException(String message) {
        super(message);
    }

    public GameStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
