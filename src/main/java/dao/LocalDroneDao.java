package main.java.dao;

import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneType;

import java.util.List;

public interface LocalDroneDao {
    void saveDroneData(List<Drone> drones);

    List<Drone> loadDroneData();

    void saveDroneTypeData(List<DroneType> drones);

    List<DroneType> loadDroneTypeData();
}
