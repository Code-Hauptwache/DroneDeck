package main.java.dao;

import main.java.entity.DroneTypeEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implemented Local DroneType Saver/Loader Dao
 */
public class LocalDroneTypeDao implements ILocalDroneTypeDao {

    private static final Logger logger = Logger.getLogger(LocalDroneTypeDao.class.getName());
    private static final String DRONE_TYPE_FILE_NAME = "Drone_type_data.bin";
    private static List<DroneTypeEntity> singletonList = new ArrayList<>();

    /**
     * Initialize or Update DroneType Information
     * @param droneTypes from API
     */
    public void updateDroneTypeData(List<DroneTypeEntity> droneTypes) {
        saveDroneTypeData(droneTypes);
        getDroneTypeDataFromFile();
    }

    private void saveDroneTypeData(List<DroneTypeEntity> droneTypes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DRONE_TYPE_FILE_NAME))) {
            oos.writeObject(droneTypes);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save drone type data.", e);
        }
    }

    /**
     * Get cached drone type data from file or heap memory
     * @return List of all drone type data
     */
    public List<DroneTypeEntity> loadDroneTypeData() {
        if (singletonList.isEmpty()) {
            singletonList = getDroneTypeDataFromFile();
        }
        return singletonList;
    }

    /**
     * Get count of drone type data
     * @return count of drone type data
     */
    public int getDroneTypeDataCount() {
        return loadDroneTypeData().size();
    }

    private List<DroneTypeEntity> getDroneTypeDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DRONE_TYPE_FILE_NAME))) {
            return (List<DroneTypeEntity>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Failed to load drone type data.", e);
            return new ArrayList<>();
        }
    }
}