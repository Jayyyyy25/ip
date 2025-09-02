package remy.exception;

/**
 * Thrown when a command is invalid.
 */
public class InvalidCommandException extends RemyException {
    public InvalidCommandException(String message) {
        super(message);
    }
}
