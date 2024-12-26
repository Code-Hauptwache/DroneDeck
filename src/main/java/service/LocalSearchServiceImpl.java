package main.java.service;

import main.java.api.IDroneApiService;
import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneType;
import main.java.api.exceptions.DroneApiException;
import main.java.dao.LocalDroneDao;
import main.java.entity.DroneEntity;
import main.java.entity.DroneTypeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        List<DroneEntity> droneEntityList = new ArrayList<>();

        for (Drone drone : drones) {
            DroneEntity entity = drone.toEntity();
            DroneTypeEntity droneTypeEntity = droneTypes.stream()
                    .filter(droneType -> Objects.equals(droneType.id, drone.getDronetypeId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException())
                    .toEntity();

            entity.setDronetype(droneTypeEntity);

            droneEntityList.add(entity);
        }

        localDroneDao.updateDroneData(droneEntityList);
    }

    public List<DroneEntity> getAllDrones() {
        return localDroneDao.loadDroneData();
    }

    public List<DroneEntity> findDroneByKeyword(String keyword) {
        List<DroneEntity> drones = localDroneDao.loadDroneData();

        return drones.stream()
                .filter(droneEntity -> droneEntity.checkIfKeywordMatches(keyword))
                .collect(Collectors.toList());
    }
}
