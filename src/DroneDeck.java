import api.DroneApiInterface;
import api.dtos.DroneDynamics;

import java.util.ArrayList;

public class DroneDeck {


    /**
     * Erstmal nur ein ganz einfacher einsteigesspunkt zum Testen des Programms
     * (Also den Teil den ich gemacht habe -Gordon)
     */
    public static void main(String[] args) {

        //Nur ein paar test, ob mein Code auch wirklich funktioniert ...
        //Ihr muessted jedoch vorher den API-Token eingeben ... diesen bitte nicht in Git Pushen ...

        DroneApiInterface droneApiInterface = new DroneApiInterface("<API_KEY Here>");

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
