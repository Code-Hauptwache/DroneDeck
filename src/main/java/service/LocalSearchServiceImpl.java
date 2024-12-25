package main.java.service;

import main.java.api.IDroneApiService;
import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneType;
import main.java.api.exceptions.DroneApiException;
import main.java.dao.LocalDroneDao;

import java.util.List;
import java.util.stream.Collectors;

public class LocalSearchServiceImpl implements LocalSearchService {

    private final LocalDroneDao localDroneDao;
    private final IDroneApiService droneApiService;

    public LocalSearchServiceImpl(LocalDroneDao localDroneDao, IDroneApiService droneApiService) {
        this.localDroneDao = localDroneDao;
        this.droneApiService = droneApiService;
    }

    public void initLocalData() {
        List<Drone> drones = null;
        List<DroneType> droneTypes = null;
        try {
            drones = droneApiService.getDrones();
            droneTypes = droneApiService.getDroneTypes();
        } catch (DroneApiException e) {
            throw new RuntimeException(e);
        }

        localDroneDao.saveDroneData(drones);
        localDroneDao.saveDroneTypeData(droneTypes);
    }

    public List<Drone> getAllDrones() {
        return localDroneDao.loadDroneData();
    }

    public List<DroneType> getAllDroneTypes() {
        return localDroneDao.loadDroneTypeData();
    }

    public List<Drone> findDroneByKeyword(String keyword) {
        List<Drone> drones = localDroneDao.loadDroneData();

        return drones.stream().filter(drone ->
                        Integer.toString(drone.id).contains(keyword)
                                || drone.serialnumber.contains(keyword)
                                || drone.dronetype.contains(keyword))
                .collect(Collectors.toList());
    }
}
