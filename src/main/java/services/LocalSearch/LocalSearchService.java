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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Implemented Service for Searching in Cached Data
 */
public class LocalSearchService implements ILocalSearchService {

    private static final Logger logger = Logger.getLogger(LocalSearchService.class.getName());

    private static LocalSearchService instance;

    private final ILocalDroneDao localDroneDao;
    private final ILocalDroneTypeDao localDroneTypeDao;
    private final IDroneApiService droneApiService;
    private LoadingProgressListener progressListener;

    /**
     * Sets the progress listener for loading updates.
     * @param listener The progress listener to use
     */
    public void setProgressListener(LoadingProgressListener listener) {
        this.progressListener = listener;
    }

    /**
     * Updates the loading progress if a listener is set.
     * @param progress The current progress (0-100)
     * @param status The current status message
     */
    private void updateProgress(int progress, String status) {
        if (progressListener != null) {
            progressListener.onProgressUpdate(progress, status);
        }
    }

    /**
     * Private constructor for LocalSearchService
     * @param localDroneDao for accessing local drone data
     * @param localDroneTypeDao for accessing local drone type data
     * @param droneApiService for accessing drone api
     */
    private LocalSearchService(ILocalDroneDao localDroneDao, ILocalDroneTypeDao localDroneTypeDao, IDroneApiService droneApiService) {
        this.localDroneDao = localDroneDao;
        this.localDroneTypeDao = localDroneTypeDao;
        this.droneApiService = droneApiService;
    }

    /**
     * Create a new instance of LocalSearchService
     * @param localDroneDao for accessing local drone data
     * @param localDroneTypeDao for accessing local drone type data
     * @param droneApiService for accessing drone api
     * @return the new instance of LocalSearchService
     */
    public static LocalSearchService createInstance(ILocalDroneDao localDroneDao, ILocalDroneTypeDao localDroneTypeDao, IDroneApiService droneApiService) {
        instance = new LocalSearchService(localDroneDao, localDroneTypeDao, droneApiService);
        return instance;
    }

    /**
     * Get the current instance of LocalSearchService
     * @return the current instance of LocalSearchService
     */
    public static LocalSearchService getCurrentInstance() {
        if (instance == null) {
            throw new IllegalStateException("LocalSearchService instance is not created yet. Call createInstance() first.");
        }
        return instance;
    }

    /**
     * Method that Initializes / Updates Local Drone Data for Caching
     */
    @Override
    public void initLocalData() {
        updateProgress(0, "📡 Fetching drone data from API...");
        List<Drone> drones;
        List<DroneType> droneTypes;
        try {
            updateProgress(10, "🚁 Fetching basic drone information...");
            drones = droneApiService.getDrones();
            logger.log(Level.INFO, "Number of drones fetched: " + drones.size());
            
            updateProgress(30, "📋 Fetching drone type information...");
            droneTypes = droneApiService.getDroneTypes();
            logger.log(Level.INFO, "Number of drone types fetched: " + droneTypes.size());
            
        } catch (DroneApiException e) {
            logger.log(Level.SEVERE, "Failed to fetch drone data from API", e);
            throw new RuntimeException(e);
        }

        updateProgress(50, "🔄 Processing drone type data...");
        List<DroneEntity> droneEntityList = new ArrayList<>();
        List<DroneTypeEntity> droneTypeEntityList = droneTypes.stream()
                .map(DroneType::toEntity)
                .collect(Collectors.toList());

        updateProgress(70, "🔄 Processing drone data...");
        DroneController.mapDronesToEntities(droneEntityList, drones, droneTypes);
        logger.log(Level.INFO, "Number of drone entities mapped: " + droneEntityList.size());

        updateProgress(85, "💾 Saving drone data to local storage...");
        localDroneDao.updateDroneData(droneEntityList);
        
        updateProgress(95, "💾 Saving drone type data to local storage...");
        if (localDroneTypeDao != null) {
            localDroneTypeDao.updateDroneTypeData(droneTypeEntityList);
        }

        updateProgress(100, "✅ Data initialization complete");
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

    /**
     * Find Drone Types that matches with keyword
     * Especially for drone type's manufacturer and typename.
     * @param keyword that user wants to find with
     * @return Drone Types that matches with keyword
     */
    @Override
    public List<DroneTypeEntity> findDroneTypesByKeyword(String keyword) {
        if (localDroneTypeDao == null) {
            throw new IllegalStateException("localDroneTypeDao is not initialized");
        }
        List<DroneTypeEntity> droneTypes = localDroneTypeDao.loadDroneTypeData();

        return droneTypes.stream()
                .filter(droneTypeEntity -> droneTypeEntity.checkIfKeywordMatches(keyword))
                .collect(Collectors.toList());
    }
}
