package main.java.exceptions;

/**
 * Custom exception for DroneApi
 */
public class DroneApiException extends Exception {

    /**
     * Constructor
     * @param message the message to be displayed
     */
    public DroneApiException(String message) {
        super(message);
    }
}