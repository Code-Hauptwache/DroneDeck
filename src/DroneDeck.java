import api.DroneApiInterface;
import api.dtos.DroneDynamics;

import java.util.ArrayList;

public class DroneDeck {


    /**
     * Main entry point of the program.
     */
    public static void main(String[] args) {

        //Only some thest for my code. Will be moved to spesific testing files later on.
        //If you want to "test" this then create the environment variable DRONE_API_KEY: <API TOKEN HERE>
        DroneApiInterface droneApiInterface = new DroneApiInterface(System.getenv("DRONE_API_KEY"));
        try {
            ArrayList<DroneDynamics> dynamics = droneApiInterface.getDroneDynamics();

            //Just printing the first 3 responses

            for(int i = 0; i < dynamics.size() && i < 3; i++) {
                System.out.println(dynamics.get(i));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
