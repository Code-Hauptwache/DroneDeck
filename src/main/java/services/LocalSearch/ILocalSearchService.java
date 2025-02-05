package main.java.services.LocalSearch;

import main.java.entity.DroneEntity;
import main.java.entity.DroneTypeEntity;

import java.util.List;

/**
 * Service for Searching in Cached Data
 */
public interface ILocalSearchService {
    /**
     * Sets the progress listener for loading updates.
     * @param listener The progress listener to use
     */
    void setProgressListener(LoadingProgressListener listener);

    /**
     * Method that Initializes / Updates Local Drone Data for Caching
     * Must be called once when program starts
     */
    void initLocalData();

    /**
     * Get All Drones from local cached drone data
     * @return All Drones
     */
    List<DroneEntity> getAllDrones();

    /**
     * Get All Drone Types from local cached drone type data
     * @return All Drone Types
     */
    List<DroneTypeEntity> getAllDroneTypes();

    /**
     * Find Drones that matches with keyword
     * Especially for drone's id, serial number and type name.
     * @param keyword that user wants to find with
     * @return Drones that matches with keyword
     */
    List<DroneEntity> findDronesByKeyword(String keyword);

    /**
     * Find Drone Types that matches with keyword
     * Especially for drone type's name.
     * @param keyword that user wants to find with
     * @return Drone Types that matches with keyword
     */
    List<DroneTypeEntity> findDroneTypesByKeyword(String keyword);
}
