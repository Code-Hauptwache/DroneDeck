package main.java.dao;

import main.java.entity.DroneEntity;

import java.util.List;

public interface LocalDroneDao {
    void updateDroneData(List<DroneEntity> drones);

    List<DroneEntity> loadDroneData();
}
