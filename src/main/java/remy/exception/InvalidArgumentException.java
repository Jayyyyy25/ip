package remy.exception;

/**
 * Thrown when a command argument is invalid or missing.
 */
public class InvalidArgumentException extends RemyException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
