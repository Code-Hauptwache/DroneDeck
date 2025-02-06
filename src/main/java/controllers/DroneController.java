package main.java.controllers;

import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.entity.DroneEntity;
import main.java.entity.DroneTypeEntity;
import main.java.exceptions.DroneApiException;
import main.java.services.ApiToken.ApiTokenService;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.Drone;
import main.java.services.DroneApi.dtos.DroneDynamics;
import main.java.services.DroneApi.dtos.DroneDynamicsResponse;
import main.java.services.DroneApi.dtos.DroneType;
import main.java.ui.dtos.DroneDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A controller that provides List of DroneDashboardCardDto
 */
public class DroneController implements IDroneController {

    private static final Logger logger = Logger.getLogger(DroneController.class.getName());

    private static final String API_KEY = ApiTokenService.getApiToken();
    private final IDroneApiService droneApiService = new DroneApiService(API_KEY);
    private final ILocalDroneDao localDroneDao = new LocalDroneDao();

    /**
     * Gets the DroneApiService instance used by this controller.
     * This is used by the DataRefreshService to centralize API calls.
     *
     * @return The DroneApiService instance
     */
    public IDroneApiService getDroneApiService() {
        return droneApiService;
    }

    /**
     * Retrieves a paginated list of drones and generates a corresponding list of DroneDashboardCardDto objects.
     * This method processes the drone data asynchronously using a thread pool to enhance performance
     * when generating the dashboard card DTOs.
     *
     * @param limit the maximum number of drones to process and include in the result.
     * @param offset the starting index within the dataset from which to retrieve the drones.
     * @return a list of DroneDashboardCardDto objects corresponding to the drones retrieved,
     *         or an empty list if no drones are found within the specified range.
     */
    /**
     * Interface for progress updates during drone data fetching
     */
    public interface DroneFetchProgressCallback {
        void onProgress(int completed, int total, String status);
    }

    @Override
    public List<DroneDto> getDroneThreads(int limit, int offset) {
        return getDroneThreads(limit, offset, null);
    }

    public List<DroneDto> getDroneThreads(int limit, int offset, DroneFetchProgressCallback progressCallback) {
        // Get drones from local storage - no need to fetch static data again
        List<DroneEntity> updatedDrones = localDroneDao.loadDroneData().subList(offset, offset + limit);
        ExecutorService executorService = Executors.newFixedThreadPool(limit);
        List<CompletableFuture<DroneDto>> futures = new ArrayList<>();

        // Track progress
        final int[] completedCount = {0};
        final int totalDrones = updatedDrones.size();

        updatedDrones.parallelStream()
                .map(d -> CompletableFuture.supplyAsync(() -> {
                    try {
                        DroneDto dto = getDroneDto(d);
                        // Update progress
                        synchronized (completedCount) {
                            completedCount[0]++;
                            if (progressCallback != null) {
                                progressCallback.onProgress(
                                    completedCount[0], 
                                    totalDrones,
                                    String.format("Fetched dynamics for drone %d/%d", completedCount[0], totalDrones)
                                );
                            }
                        }
                        return dto;
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Failed to get drone data for ID: " + d.getId(), e);
                        return null;
                    }
                }, executorService))
                .forEach(futures::add);

        List<DroneDto> droneDtoList = new ArrayList<>();
        for (CompletableFuture<DroneDto> future : futures) {
            try {
                DroneDto droneDto = future.join();
                if (droneDto != null) {
                    droneDtoList.add(droneDto);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to join future", e);
            }
        }

        executorService.shutdown();
        return droneDtoList;
    }

    /**
     * Maps a list of Drone objects to a list of DroneEntity objects.
     *
     * @param droneEntityList the list of DroneEntity objects to populate.
     * @param drones the list of Drone objects to map to DroneEntity objects.
     * @param droneTypes the list of DroneType objects to map to DroneTypeEntity objects.
     */
    public static void mapDronesToEntities(List<DroneEntity> droneEntityList, List<Drone> drones, List<DroneType> droneTypes) {
        for (Drone drone : drones) {
            DroneEntity entity = drone.toEntity();
            DroneTypeEntity droneTypeEntity = droneTypes.stream()
                    .filter(droneType -> Objects.equals(droneType.id, drone.getDroneTypeId()))
                    .findFirst()
                    .map(DroneType::toEntity)
                    .orElse(null);

            if (droneTypeEntity == null) {
                // Log an error or handle the case where the DroneType is not found
                System.err.println("DroneType not found for Drone ID: " + drone.getId() + ", DroneType ID: " + drone.getDroneTypeId());
                continue;
            }

            entity.setDronetype(droneTypeEntity);
            droneEntityList.add(entity);
        }
    }

    public DroneDto getDroneDto(DroneEntity drone) {
        DroneDynamics droneDynamic;
        try {
            // First get the count of dynamics data
            DroneDynamicsResponse droneDynamicsResponse = droneApiService.getDroneDynamicsResponseByDroneId(drone.getId(), 1, 0);
            // Then get the latest dynamic data using (count - 1) as offset
            ArrayList<DroneDynamics> latestDroneDynamic = droneApiService.getDroneDynamicsByDroneId(drone.getId(), 1, droneDynamicsResponse.getCount() - 1);
            droneDynamic = latestDroneDynamic.getFirst();
        } catch (DroneApiException e) {
            logger.log(Level.SEVERE, "Failed to get drone dynamics for drone with ID: " + drone.getId(), e);
            throw new RuntimeException(e);
        }

        DroneTypeEntity droneType = drone.getDronetype();

        return new DroneDto(
                drone.getId(),
                droneType.typename,
                droneType.manufacturer,
                droneDynamic.status,
                droneDynamic.battery_status,
                droneType.battery_capacity,
                droneDynamic.speed,
                droneDynamic.longitude,
                droneDynamic.latitude,
                drone.getSerialNumber(),
                drone.getCarriage_weight(),
                drone.getCarriage_type(),
                droneType.max_carriage,
                droneDynamic.last_seen.toString(),
                droneType.weight,
                droneType.max_speed,
                droneType.control_range,
                droneDynamic.timestamp.toString()
        );
    }

    public List<DroneEntity> getAllDrones() {
        return localDroneDao.loadDroneData();
    }
}
