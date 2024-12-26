package main.java.service;

import main.java.entity.DroneEntity;

import java.util.List;

public interface LocalSearchService {

    /**
     * Method that Initializes / Updates Local Drone Data for Caching
     */
    void initLocalData();

    /**
     * Get All Drones from local cached drone data
     * @return All Drones
     */
    List<DroneEntity> getAllDrones();

    /**
     * Find Drones that matches with keyword
     * Especially for drone's id, serial number and type name.
     * @param keyword that user wants to find with
     * @return Drones that matches with keyword
     */
    List<DroneEntity> findDronesByKeyword(String keyword);
}
