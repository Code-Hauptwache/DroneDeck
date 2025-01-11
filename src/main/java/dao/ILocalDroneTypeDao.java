package main.java.dao;

import main.java.entity.DroneTypeEntity;

import java.util.List;

/**
 * Interface for Local DroneType Saver/Loader Dao
 */
public interface ILocalDroneTypeDao {
    void updateDroneTypeData(List<DroneTypeEntity> droneTypes);
    List<DroneTypeEntity> loadDroneTypeData();
    int getDroneTypeDataCount();
}