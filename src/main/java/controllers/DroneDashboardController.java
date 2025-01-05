package main.java.controllers;

import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.entity.DroneEntity;
import main.java.entity.DroneTypeEntity;
import main.java.exceptions.DroneApiException;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.Drone;
import main.java.services.DroneApi.dtos.DroneDynamics;
import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.ReverseGeocode.ReverseGeocodeService;
import main.java.services.TravelDistance.ITravelDistanceService;
import main.java.services.TravelDistance.TravelDistanceService;
import main.java.ui.dtos.DroneDashboardCardDto;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A controller that provides List of DroneDashboardCardDto
 */
public class DroneDashboardController implements IDroneDashboardController {

    private static final String API_KEY = System.getenv("DRONE_API_KEY");
    private final IDroneApiService droneApiService = new DroneApiService(API_KEY);
    private final ITravelDistanceService travelDistanceService = new TravelDistanceService(droneApiService);
    private final ILocalDroneDao localDroneDao = new LocalDroneDao();
    private final IReverseGeocodeService reverseGeocodeService = new ReverseGeocodeService();

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
    @Override
    public List<DroneDashboardCardDto> getDroneDashboardCardsThreads(int limit, int offset) {
        List<DroneEntity> drones = localDroneDao.loadDroneData().subList(offset, offset + limit);

        ExecutorService executorService = Executors.newFixedThreadPool(limit);

        List<CompletableFuture<DroneDashboardCardDto>> futures = new ArrayList<>();

        drones.parallelStream()
                .map(d -> CompletableFuture.supplyAsync(() -> getDroneDashboardCardDto(d), executorService))
                .forEach(futures::add);

        List<DroneDashboardCardDto> droneDashboardCardDtoList = new ArrayList<>();
        for (CompletableFuture<DroneDashboardCardDto> future : futures) {
            DroneDashboardCardDto droneDashboardCardDto = future.join();
            if (droneDashboardCardDto != null) {
                droneDashboardCardDtoList.add(droneDashboardCardDto);
            }
        }

        executorService.shutdown();

        return droneDashboardCardDtoList;
    }

    private DroneDashboardCardDto getDroneDashboardCardDto(DroneEntity drone) {
        ArrayList<DroneDynamics> latestDroneDynamic = null;
        try {
            // get latest drone dynamic info
            latestDroneDynamic = droneApiService.getDroneDynamicsByDroneId(drone.getId(), 1, 0);
        } catch (DroneApiException e) {
            // TODO : Exception needs detail
            throw new RuntimeException(e);
        }

        if (latestDroneDynamic.isEmpty()) {
            return null;
        }
        DroneDynamics droneDynamic = latestDroneDynamic.get(0);

        DroneTypeEntity droneType = drone.getDronetype();

        return new DroneDashboardCardDto(
                droneType.typename,
                droneType.manufacturer,
                droneDynamic.status,
                droneDynamic.battery_status,
                droneType.battery_capacity,
                droneDynamic.speed,
                droneDynamic.longitude,
                droneDynamic.latitude,
                drone.getSerialnumber(),
                travelDistanceService.getTravelDistance(drone.getId()),
                reverseGeocodeService.getCityAndCountry(droneDynamic.latitude, droneDynamic.longitude)
        );
    }
}
