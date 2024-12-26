package main.java.dao;

import main.java.entity.DroneEntity;

import java.util.List;

public interface LocalDroneDao {

    /**
     * Initialize or Update Drone Information
     * @param drones from API
     */
    void updateDroneData(List<DroneEntity> drones);

    /**
     * Get cached drone data from file or heap memory
     * @return List of all drone data
     */
    List<DroneEntity> loadDroneData();
}
