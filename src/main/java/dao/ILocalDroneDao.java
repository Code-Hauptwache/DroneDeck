package main.java.dao;

import main.java.entity.DroneEntity;

import java.util.List;

/**
 * Local Drone Saver/Loader Dao
 */
public interface ILocalDroneDao {

    /**
     * Initialize or Update Drone Information
     * @param drones from API
     */
    void updateDroneData(List<DroneEntity> drones);

    /**
     * Get cached drone data from file or heap memory
     * Using Singleton List inside, call initLocalData() from LocalSearchService once before using this method.
     * @return List of all drone data
     */
    List<DroneEntity> loadDroneData();
}
