package seedu.duke.exception;

/**
 * Thrown when a date is provided that falls outside the allowed
 * application range (e.g., before 2026).
 */
public class IllegalDateException extends UniTaskerException {
    public IllegalDateException(String message) {
        super(message);
    }
}
