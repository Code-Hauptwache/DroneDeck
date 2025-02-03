package main.java.services.DroneDataCalculation;

import main.java.exceptions.DroneApiException;
import main.java.services.ApiToken.ApiTokenService;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class DroneDataCalculationTest {

    /**
     * The id of the drone for the tests
     * This ensures that the tests are consistent
     */
    private static int droneId = 0;

    @BeforeAll
    static void setup() throws DroneApiException {
        //Initialize the parent frame for the dialog
        JFrame frame = new JFrame();
        ApiTokenService.setParent(frame);

        //Get a valid drone Id
        String apiToken = ApiTokenService.getApiToken();
        IDroneApiService droneApiService = new DroneApiService(apiToken);
        droneId = droneApiService.getDrones(1, 0).getFirst().id;
    }

    @Test
    void getTravelDistance() {

        String apiToken = ApiTokenService.getApiToken();

        IDroneApiService droneApiService = new DroneApiService(apiToken);

        DroneDataCalculationService droneDataCalculationService = new DroneDataCalculationService(droneApiService);

        double travelDistance = droneDataCalculationService.getTravelDistance(droneId);

        System.out.println("travelDistance = " + travelDistance);
    }

    @Test
    void getAverageSpeed() {

        String apiToken = ApiTokenService.getApiToken();

        IDroneApiService droneApiKey = new DroneApiService(apiToken);
        DroneDataCalculationService droneDataCalculationService = new DroneDataCalculationService(droneApiKey);

        double averageSpeed = droneDataCalculationService.getAverageSpeed(droneId);

        System.out.println("averageSpeed = " + averageSpeed);
    }

    @Test
    void getTravelDistanceAndAverageSpeed() {

        String apiToken = ApiTokenService.getApiToken();

        IDroneApiService droneApiKey = new DroneApiService(apiToken);
        DroneDataCalculationService droneDataCalculationService = new DroneDataCalculationService(droneApiKey);

        DataCalculationResult result = droneDataCalculationService.getTravelDistanceAndAverageSpeed(droneId);

        System.out.println("travelDistance = " + result.travelDistance);
        System.out.println("averageSpeed = " + result.averageSpeed);
    }
}
