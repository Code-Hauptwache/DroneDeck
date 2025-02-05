package main.java.exceptions;

import java.io.Serial;

/**
 * Custom exception class for handling drone API related errors.
 * This exception is thrown when there are issues with drone API operations
 * such as communication failures, invalid responses, or authentication problems.
 */
public class DroneApiException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DroneApiException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public DroneApiException(String message) {
        super(message);
    }
}
