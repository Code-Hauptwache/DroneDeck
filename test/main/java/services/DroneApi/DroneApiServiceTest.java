package main.java.services.DroneApi;

import main.java.services.ApiToken.ApiTokenService;
import main.java.services.DroneApi.dtos.Drone;
import main.java.services.DroneApi.dtos.DroneDynamics;
import main.java.services.DroneApi.dtos.DroneType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for DroneApiService
 */
class DroneApiServiceTest {

    @BeforeAll
    static void setup() {
        //Initialize the parent frame for the dialog
        JFrame frame = new JFrame();
        ApiTokenService.setParent(frame);
    }

    @Test
    void testDroneApiInterface() {

        String apiToken = ApiTokenService.getApiToken();

        IDroneApiService droneApiInterface = new DroneApiService(apiToken);

        ArrayList<DroneDynamics> dynamics;
        ArrayList<Drone> drones;
        ArrayList<DroneType> droneTypes;

        try {
            dynamics = droneApiInterface.getDroneDynamics();
            drones = droneApiInterface.getDrones();
            droneTypes = droneApiInterface.getDroneTypes();

            System.out.println("DroneDynamics");
            for (int i = 0; i < dynamics.size() && i < 3; i++) {
                System.out.println(dynamics.get(i));
            }

            System.out.println("Drones");
            for (int i = 0; i < drones.size() && i < 3; i++) {
                System.out.println(drones.get(i));
            }

            System.out.println("DroneTypes");
            for (int i = 0; i < droneTypes.size() && i < 3; i++) {
                System.out.println(droneTypes.get(i));
            }

            System.out.println("Drone by id: " + drones.getFirst().id);
            System.out.println(droneApiInterface.getDronesById(drones.getFirst().id));
            System.out.println("DroneType: ");
            System.out.println(droneApiInterface.getDroneTypeById(drones.getFirst().getDroneTypeId()));
            System.out.println("DroneDynamicsByDrone (only the first): ");
            System.out.println(droneApiInterface.getDroneDynamicsByDroneId(drones.getFirst().id).getFirst());

        } catch (Exception ex) {
            //No Logging because this is a Test
            fail("Exception occurred during test execution: " + ex.getMessage());
        }
    }
}