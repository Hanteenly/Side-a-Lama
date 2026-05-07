package sk.tuke.gamestudio.service;

public class AdditionalCommentException extends RuntimeException {
    public AdditionalCommentException(String message) {
        super(message);
    }
    public AdditionalCommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
