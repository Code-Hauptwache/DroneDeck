package main.java.dao;

import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocalDroneDaoImpl implements LocalDroneDao {

    private static final String DRONE_FILE_NAME = "drone_data.bin";
    private static final String DRONE_TYPE_FILE_NAME = "drone_type_data.bin";

    public void saveDroneData(List<Drone> drones) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DRONE_FILE_NAME))) {
            oos.writeObject(drones);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Drone> loadDroneData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DRONE_FILE_NAME))) {
            return (List<Drone>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveDroneTypeData(List<DroneType> drones) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DRONE_TYPE_FILE_NAME))) {
            oos.writeObject(drones);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<DroneType> loadDroneTypeData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DRONE_TYPE_FILE_NAME))) {
            return (List<DroneType>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

