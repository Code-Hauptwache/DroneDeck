package main.java.services.DroneDataCalculation;

/**
 * An object that contains the result of the data calculation
 * like travel distance and average speed
 */
public class DataCalculationResult {

    /**
     * Constructor of the DataCalculationResult
     * @param travelDistance the total travel distance in kilometers
     * @param averageSpeed the average speed in kilometers per hour
     */
    public DataCalculationResult(double travelDistance, double averageSpeed) {
        this.travelDistance = travelDistance;
        this.averageSpeed = averageSpeed;
    }

    /**
     * The total travel distance in kilometers
     */
    public double travelDistance;

    /**
     * The average speed in kilometers per hour
     */
    public double averageSpeed;

}
