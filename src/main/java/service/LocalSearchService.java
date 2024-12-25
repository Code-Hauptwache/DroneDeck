package main.java.service;

import main.java.api.IDroneApiService;
import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneType;
import main.java.api.exceptions.DroneApiException;
import main.java.dao.LocalDroneDao;

import java.util.ArrayList;
import java.util.List;

public class LocalSearchService {

    private final LocalDroneDao localDroneDao;
    private final IDroneApiService droneApiService;

    public LocalSearchService(LocalDroneDao localDroneDao, IDroneApiService droneApiService) {
        this.localDroneDao = localDroneDao;
        this.droneApiService = droneApiService;
    }

    public void initLocalData() {
        ArrayList<Drone> drones = null;
        try {
            drones = droneApiService.getDrones();
            System.out.println("drones.size() = " + drones.size());
        } catch (DroneApiException e) {
            throw new RuntimeException(e);
        }
        ArrayList<DroneType> droneTypes = null;
        try {
            droneTypes = droneApiService.getDroneTypes();
            System.out.println("droneTypes.size() = " + droneTypes.size());
        } catch (DroneApiException e) {
            throw new RuntimeException(e);
        }

        localDroneDao.saveDroneData(drones);
        localDroneDao.saveDroneTypeData(droneTypes);
    }
}
