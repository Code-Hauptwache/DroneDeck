import api.DroneApiInterface;
import api.dtos.Drone;
import api.dtos.DroneDynamics;
import api.dtos.DroneType;

import java.util.ArrayList;

public class DroneApiInterfaceTest {

    /**
     * Simple test for the Drone API interface
     */
    public static void main(String[] args) {

        //Only some tests for my code.
        //If you want to "test" this then put your API Token here

        DroneApiInterface droneApiInterface = new DroneApiInterface("<API_TOKEN Here>");

        ArrayList<DroneDynamics> dynamics;
        ArrayList<Drone> drones;
        ArrayList<DroneType> droneTypes;
        ArrayList<DroneDynamics> droneDynamicsByDrone;

        try {
            dynamics = droneApiInterface.getDroneDynamics();
            drones = droneApiInterface.getDrones();
            droneTypes = droneApiInterface.getDroneTypes();

            System.out.println("DroneDynamics");
            //Just printing the first 3 responses
            for(int i = 0; i < dynamics.size() && i < 3; i++) {
                System.out.println(dynamics.get(i));
            }

            System.out.println("Drones");
            for(int i = 0; i < drones.size() && i < 3; i++) {
                System.out.println(drones.get(i));
            }

            System.out.println("DroneTypes");
            for(int i = 0; i < droneTypes.size() && i < 3; i++) {
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
        }
    }

}
