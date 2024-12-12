package main.java.api;

import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneDynamics;
import main.java.api.dtos.DroneType;

import java.util.ArrayList;

public interface IDroneApiInterface {
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
     * @param limit  the number of DroneDynamics to fetch. Must be between 10_000 and 1
     * @param offset the offset (from 0)
     * @return the list of DroneDynamics
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<DroneDynamics> getDroneDynamics(int limit, int offset) throws DroneApiException;

    /**
     * Get 100 DroneDynamics
     * @param offset the offset (from 0)
     * @return 100 DroneDynamics
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<DroneDynamics> getDroneDynamics(int offset) throws DroneApiException;

    /**
     * Get 100 DroneDynamics
     * @return 100 DroneDynamics
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<DroneDynamics> getDroneDynamics() throws DroneApiException;

    /**
     * Gets a DroneDynamics by id
     * @param id the id to get the DroneDynamics of
     * @return the drone dynamics
     * @throws DroneApiException Api Exceptions
     */
    DroneDynamics getDroneDynamicsById(int id) throws DroneApiException;

    /**
     * Get a list of Drones
     * @param limit  the number of Drones to get. Must be between 10_000 and 1
     * @param offset the offset (from 0)
     * @return list of Drones
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<Drone> getDrones(int limit, int offset) throws DroneApiException;

    /**
     * Get a list of 100 Drones
     * @param offset the offset (from 0)
     * @return a list of 100 Drones
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<Drone> getDrones(int offset) throws DroneApiException;

    /**
     * Get a list of 100 Drones
     * @return a list of 100 Drones
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<Drone> getDrones() throws DroneApiException;

    /**
     * Gets a drone by its id
     * @param id the id of the drone to get
     * @return the drone
     * @throws DroneApiException Api Exceptions
     */
    Drone getDronesById(int id) throws DroneApiException;

    /**
     * Get a list of DroneTypes
     * @param limit  the number of Types to get. Must be between 10_000 and 1
     * @param offset the offset (from 0)
     * @return a list of DroneTypes
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<DroneType> getDroneTypes(int limit, int offset) throws DroneApiException;

    /**
     * Get a list of 100 DroneTypes
     * @param offset the offset from 0
     * @return A list with 100 DroneTypes
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<DroneType> getDroneTypes(int offset) throws DroneApiException;

    /**
     * Get a list of 100 DroneTypes
     * @return A list with 100 DroneTypes
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<DroneType> getDroneTypes() throws DroneApiException;

    /**
     * Get a DroneType by its id
     * @param id the id of the DroneType to get
     * @return the DroneType
     * @throws DroneApiException Api Exceptions
     */
    DroneType getDroneTypeById(int id) throws DroneApiException;

    /**
     * Gets a drones DroneDynamics
     * @param id     the id of the drone of which to retrieve the DroneDynamics
     * @param limit  the number of DroneDynamics to get. Must be between 10_000 and 1
     * @param offset the offset from 0
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int limit, int offset) throws DroneApiException;

    /**
     * Get 100 DroneDynamics by Drone id
     * @param id     the id of the Drone
     * @param offset the offset from 0
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int offset) throws DroneApiException;

    /**
     * Get 100 DroneDynamics by Drone id
     * @param id the id of the Drone
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException Api Exceptions
     */
    ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id) throws DroneApiException;
}
