package main.java.services.TravelDistance;

/**
 * A service that provides travel distance of drone
 */
public interface ITravelDistanceService {
    /**
     * Method that returns calculated data of total travel distance (km)
     * Number of the dynamic data is 500
     */
    double getTravelDistance(int droneId);
}
