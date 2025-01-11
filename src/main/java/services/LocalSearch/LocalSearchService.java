package main.java.services.LocalSearch;

import main.java.controllers.DroneController;
import main.java.dao.ILocalDroneTypeDao;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.Drone;
import main.java.services.DroneApi.dtos.DroneType;
import main.java.exceptions.DroneApiException;
import main.java.dao.ILocalDroneDao;
import main.java.entity.DroneEntity;
import main.java.entity.DroneTypeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implemented Service for Searching in Cached Data
 */
public class LocalSearchService implements ILocalSearchService {

    private final ILocalDroneDao localDroneDao;
    private final ILocalDroneTypeDao localDroneTypeDao;
    private final IDroneApiService droneApiService;

    /**
     * Constructor for LocalSearchService
     * @param localDroneDao for accessing local drone data
     * @param droneApiService for accessing drone api
     */
    public LocalSearchService(ILocalDroneDao localDroneDao, IDroneApiService droneApiService) {
        this.localDroneDao = localDroneDao;
        this.localDroneTypeDao = null;
        this.droneApiService = droneApiService;
    }

    /**
     * Constructor for LocalSearchService
     * @param localDroneDao for accessing local drone data
     * @param localDroneTypeDao for accessing local drone type data
     * @param droneApiService for accessing drone api
     */
    public LocalSearchService(ILocalDroneDao localDroneDao, ILocalDroneTypeDao localDroneTypeDao, IDroneApiService droneApiService) {
        this.localDroneDao = localDroneDao;
        this.localDroneTypeDao = localDroneTypeDao;
        this.droneApiService = droneApiService;
    }

    /**
     * Method that Initializes / Updates Local Drone Data for Caching
     */
    @Override
    public void initLocalData() {
        List<Drone> drones;
        List<DroneType> droneTypes;
        try {
            drones = droneApiService.getDrones();
            droneTypes = droneApiService.getDroneTypes();
        } catch (DroneApiException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Number of drones fetched: " + drones.size());
        System.out.println("Number of drone types fetched: " + droneTypes.size());

        List<DroneEntity> droneEntityList = new ArrayList<>();
        List<DroneTypeEntity> droneTypeEntityList = droneTypes.stream()
                .map(DroneType::toEntity)
                .collect(Collectors.toList());

        DroneController.mapDronesToEntities(droneEntityList, drones, droneTypes);

        System.out.println("Number of drone entities mapped: " + droneEntityList.size());

        localDroneDao.updateDroneData(droneEntityList);
        if (localDroneTypeDao != null) {
            localDroneTypeDao.updateDroneTypeData(droneTypeEntityList);
        }
    }

    /**
     * Get All Drones from local cached drone data
     * @return All Drones
     */
    @Override
    public List<DroneEntity> getAllDrones() {
        return localDroneDao.loadDroneData();
    }

    /**
     * Get All Drone Types from local cached drone type data
     * @return All Drone Types
     */
    public List<DroneTypeEntity> getAllDroneTypes() {
        if (localDroneTypeDao == null) {
            throw new IllegalStateException("localDroneTypeDao is not initialized");
        }
        return localDroneTypeDao.loadDroneTypeData();
    }

    /**
     * Find Drones that matches with keyword
     * Especially for drone's id, serial number and type name.
     * @param keyword that user wants to find with
     * @return Drones that matches with keyword
     */
    @Override
    public List<DroneEntity> findDronesByKeyword(String keyword) {
        List<DroneEntity> drones = localDroneDao.loadDroneData();

        return drones.stream()
                .filter(droneEntity -> droneEntity.checkIfKeywordMatches(keyword))
                .collect(Collectors.toList());
    }
}