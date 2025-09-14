package tom.exception;

/**
 * A custom exception class for handling errors specific to the Tom application.
 * <p>
 * This exception extends the standard {@link Exception} class and allows
 * passing a custom error message when thrown.
 * </p>
 * Example usage:
 * <pre>
 *     if (someConditionFails) {
 *         throw new TomException("An error occurred in Tom application.");
 *     }
 * </pre>
 */

public class TomException extends Exception {
    public TomException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
