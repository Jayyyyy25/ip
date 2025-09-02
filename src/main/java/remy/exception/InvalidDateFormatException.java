package remy.exception;

/**
 * Thrown when an input date/time string is in wrong format
 */
public class InvalidDateFormatException extends RemyException {
    public InvalidDateFormatException(String message) {
        super(message);
    }
}
