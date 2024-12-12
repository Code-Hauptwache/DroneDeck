package main.java.api;

import main.java.services.api.DroneApiService;
import main.java.services.api.IDroneApiService;
import main.java.services.api.dtos.Drone;
import main.java.services.api.dtos.DroneDynamics;
import main.java.services.api.dtos.DroneType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DroneApiInterfaceTest {

    @Test
    void testDroneApiInterface() {
        IDroneApiService droneApiInterface = new DroneApiService(System.getenv("DRONE_API_KEY"));

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
            System.out.println(droneApiInterface.getDroneTypeById(drones.getFirst().getDronetypeId()));
            System.out.println("DroneDynamicsByDrone (only the first): ");
            System.out.println(droneApiInterface.getDroneDynamicsByDroneId(drones.getFirst().id).getFirst());

        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception occurred during test execution: " + ex.getMessage());
        }
    }
}