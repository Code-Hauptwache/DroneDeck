package main.java.services.TravelDistance;

import main.java.exceptions.DroneApiException;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import org.junit.jupiter.api.Test;

public class TravelDistanceTest {

    @Test
    void getTravelDistance() {
        IDroneApiService droneApiKey = new DroneApiService(System.getenv("DRONE_API_KEY"));
        TravelDistanceService travelDistanceService = new TravelDistanceService(droneApiKey);

        double travelDistance = travelDistanceService.getTravelDistance(96);

        System.out.println("travelDistance = " + travelDistance);
    }
}
