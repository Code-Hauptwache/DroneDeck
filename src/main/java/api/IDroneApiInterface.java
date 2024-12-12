package main.java.api;

import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneDynamics;
import main.java.api.dtos.DroneType;

import java.net.http.HttpClient;
import java.util.ArrayList;

public interface IDroneApiInterface {
    default HttpClient buildHttpClient() {
        return HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
    }

    /**
     * @return currently used ApiKey
     */
    String getApiKey();

    /**
     * Set (new) ApiKey
     * @param apiKey the (new) ApiKey to set
     */
    void setApiKey(String apiKey);

    /**
     * Get a list of DroneDynamics
     * @param limit the number of DroneDynamics to fetch. Must be >= 1
     * @param offset the offset (from 0)
     * @return the list of DroneDynamics
     * @throws DroneApiException Strange Custom Exception XD
     */
    ArrayList<DroneDynamics> getDroneDynamics(int limit, int offset) throws DroneApiException;

    /**
     * Get 100 DroneDynamics
     * @param offset the offset (from 0)
     * @return 100 DroneDynamics
     * @throws DroneApiException Magic Exception :D
     */
    ArrayList<DroneDynamics> getDroneDynamics(int offset) throws DroneApiException;

    /**
     * Get 100 DroneDynamics
     * @return 100 DroneDynamics
     * @throws DroneApiException Magic Exception :)
     */
    ArrayList<DroneDynamics> getDroneDynamics() throws DroneApiException;

    /**
     * Gets a DroneDynamics by id
     * @param id the id to get the DroneDynamics of
     * @return the drone dynamics
     * @throws DroneApiException Magic Exception ;)
     */
    DroneDynamics getDroneDynamicsById(int id) throws DroneApiException;

    /**
     * Get a list of Drones
     * @param limit the number of Drones to get
     * @param offset the offset (from 0)
     * @return list of Drones
     * @throws DroneApiException Magic Exception D:
     */
    ArrayList<Drone> getDrones(int limit, int offset) throws DroneApiException;

    /**
     * Get a list of 100 Drones
     * @param offset the offset (from 0)
     * @return a list of 100 Drones
     * @throws DroneApiException Exception ...
     */
    ArrayList<Drone> getDrones(int offset) throws DroneApiException;

    /**
     * Get a list of 100 Drones
     * @return a list of 100 Drones
     * @throws DroneApiException ...
     */
    ArrayList<Drone> getDrones() throws DroneApiException;

    /**
     * Gets a drone by its id
     * @param id the id of the drone to get
     * @return the drone
     * @throws DroneApiException ...
     */
    Drone getDronesById(int id) throws DroneApiException;

    /**
     * Get a list of DroneTypes
     * @param limit the number of Types to get
     * @param offset the offset (from 0)
     * @return a list of DroneTypes
     * @throws DroneApiException ...
     */
    ArrayList<DroneType> getDroneTypes(int limit, int offset) throws DroneApiException;

    /**
     * Get a list of 100 DroneTypes
     * @param offset the offset from 0
     * @return A list with 100 DroneTypes
     * @throws DroneApiException ...
     */
    ArrayList<DroneType> getDroneTypes(int offset) throws DroneApiException;

    /**
     * Get a list of 100 DroneTypes
     * @return A list with 100 DroneTypes
     * @throws DroneApiException ...
     */
    ArrayList<DroneType> getDroneTypes() throws DroneApiException;

    /**
     * Get a DroneType by its id
     * @param id the id of the DroneType to get
     * @return the DroneType
     * @throws DroneApiException ...
     */
    DroneType getDroneTypeById(int id) throws DroneApiException;

    /**
     * Gets a drones DroneDynamics
     * @param id the id of the drone of wich to retrieve the DroneDynamics
     * @param limit the number of DroneDynamics to get
     * @param offset the offset
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException ...
     */
    ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int limit, int offset) throws DroneApiException;

    /**
     * Get 100 DroneDynamics by Drone id
     * @param id the id of the Drone
     * @param offset the offset
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException ...
     */
    ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int offset) throws DroneApiException;

    /**
     * Get 100 DroneDynamics by Drone id
     * @param id the id of the Drone
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException ...
     */
    ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id) throws DroneApiException;
}
