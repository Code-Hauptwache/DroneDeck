package main.java.services.TravelDistance;

import main.java.exceptions.DroneApiException;
import main.java.services.ApiToken.ApiTokenService;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class TravelDistanceTest {

    @BeforeAll
    static void setup() {
        //Initialize the parent frame for the dialog
        JFrame frame = new JFrame();
        ApiTokenService.setParent(frame);
    }

    @Test
    void getTravelDistance() {

        String apiToken = ApiTokenService.getApiToken();

        IDroneApiService droneApiKey = new DroneApiService(apiToken);
        TravelDistanceService travelDistanceService = new TravelDistanceService(droneApiKey);

        double travelDistance = travelDistanceService.getTravelDistance(96);

        System.out.println("travelDistance = " + travelDistance);
    }
}
