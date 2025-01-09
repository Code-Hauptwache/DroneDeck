package main.java.controllers;

import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.entity.DroneEntity;
import main.java.entity.DroneTypeEntity;
import main.java.exceptions.DroneApiException;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.DroneDynamics;
import main.java.services.DroneApi.dtos.DroneDynamicsResponse;
import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.ReverseGeocode.ReverseGeocodeService;
import main.java.services.TravelDistance.ITravelDistanceService;
import main.java.services.TravelDistance.TravelDistanceService;
import main.java.ui.dtos.DroneDto;

import java.util.ArrayList;
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
    public List<DroneDto> getDroneThreads(int limit, int offset) {
        List<DroneEntity> drones = localDroneDao.loadDroneData().subList(offset, offset + limit);

        ExecutorService executorService = Executors.newFixedThreadPool(limit);

        List<CompletableFuture<DroneDto>> futures = new ArrayList<>();

        drones.parallelStream()
                .map(d -> CompletableFuture.supplyAsync(() -> getDroneDto(d), executorService))
                .forEach(futures::add);

        List<DroneDto> droneDtoList = new ArrayList<>();
        for (CompletableFuture<DroneDto> future : futures) {
            DroneDto droneDto = future.join();
            if (droneDto != null) {
                droneDtoList.add(droneDto);
            }
        }

        executorService.shutdown();

        return droneDtoList;
    }

    private DroneDto getDroneDto(DroneEntity drone) {
        ArrayList<DroneDynamics> latestDroneDynamic;
        try {
            // get latest drone dynamic info
            DroneDynamicsResponse droneDynamicsResponse = droneApiService.getDroneDynamicsResponseByDroneId(drone.getId(), 1, 0);

            latestDroneDynamic = droneApiService.getDroneDynamicsByDroneId(drone.getId(), 1, droneDynamicsResponse.getCount() - 1);
        } catch (DroneApiException e) {
            // TODO : Exception needs detail
            throw new RuntimeException(e);
        }

        DroneDynamics droneDynamic = latestDroneDynamic.getFirst();

        DroneTypeEntity droneType = drone.getDrone_type();

        DroneDto droneDto = new DroneDto(
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
        droneDto.setTravelDistance(travelDistanceService.getTravelDistance(drone.getId()));
        droneDto.setLocation(reverseGeocodeService.getCityAndCountry(droneDynamic.latitude, droneDynamic.longitude));

        return droneDto;
    }
}
