package main.java.dao;

import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneType;
import main.java.entity.DroneEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocalDroneDaoImpl implements LocalDroneDao {

    private static final String DRONE_FILE_NAME = "drone_data.bin";
    private static List<DroneEntity> singletonList = new ArrayList<>();

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

    public List<DroneEntity> loadDroneData() {
        if (singletonList.isEmpty()) {
            singletonList = getDroneDataFromFile();
        }
        return singletonList;
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

