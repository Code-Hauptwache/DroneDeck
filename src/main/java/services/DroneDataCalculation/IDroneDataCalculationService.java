package main.java.services.DroneDataCalculation;

/**
 * A service that provides travel distance of drone
 */
public interface IDroneDataCalculationService {
    /**
     * Method that returns calculated data of total travel distance (km)
     * Number of the dynamic data is 500
     */
    double getTravelDistance(int droneId);

    /**
     * Method that returns calculated data of average speed (km/h)
     * Since this method needs to calculate the total travel distance,
     * it is recommended to use calculateTravelDistanceAndAverageSpeed method instead
     * since the travel distance does not need to be calculated twice.
     */
    double getAverageSpeed(int droneId);

    /**
     * Method that returns calculated data of total travel distance (km) and average speed (km/h)
     */
    DataCalculationResult getTravelDistanceAndAverageSpeed(int droneId);
}
