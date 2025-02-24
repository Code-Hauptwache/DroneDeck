package main.java.dao;

import main.java.entity.DroneEntity;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implemented Local Drone Saver/Loader Dao
 */
public class LocalDroneDao implements ILocalDroneDao {

    private static final Logger logger = Logger.getLogger(LocalDroneDao.class.getName());

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
            logger.log(Level.SEVERE, "Failed to save drone data.", e);
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

    @SuppressWarnings("unchecked")
    private List<DroneEntity> getDroneDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DRONE_FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?> list && !list.isEmpty() && list.getFirst() instanceof DroneEntity) {
                return (List<DroneEntity>) list;
            }
            logger.warning("Invalid data format in drone data file");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Failed to load drone data.", e);
            return new ArrayList<>();
        }
    }
}
