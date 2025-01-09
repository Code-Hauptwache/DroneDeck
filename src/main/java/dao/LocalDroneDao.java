package main.java.dao;

import main.java.entity.DroneEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implemented Local Drone Saver/Loader Dao
 */
public class LocalDroneDao implements ILocalDroneDao {

    private static final String DRONE_FILE_NAME = "drone_data.bin";
    private static List<DroneEntity> singletonList = new ArrayList<>();

    /**
     * Initialize or Update Drone Information
     * @param drones from API
     */
    public void updateDroneData(List<DroneEntity> drones) {
        saveDroneData(drones);
        getDroneDataFromFile();
    }

    private void saveDroneData(List<DroneEntity> drones) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DRONE_FILE_NAME))) {
            oos.writeObject(drones);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get cached drone data from file or heap memory
     * @return List of all drone data
     */
    public List<DroneEntity> loadDroneData() {
        if (singletonList.isEmpty()) {
            singletonList = getDroneDataFromFile();
        }
        return singletonList;
    }

    /**
     * Get count of drone data
     * @return count of drone data
     */
    public int getDroneDataCount() {
        return loadDroneData().size();
    }

    private List<DroneEntity> getDroneDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DRONE_FILE_NAME))) {
            return (List<DroneEntity>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

