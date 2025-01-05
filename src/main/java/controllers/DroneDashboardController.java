package main.java.controllers;

import main.java.dao.ILocalDroneDao;
import main.java.entity.DroneEntity;
import main.java.entity.DroneTypeEntity;
import main.java.exceptions.DroneApiException;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.Drone;
import main.java.services.DroneApi.dtos.DroneDynamics;
import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.TravelDistance.ITravelDistanceService;
import main.java.ui.dtos.DroneDashboardCardDto;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DroneDashboardController {

    private final ITravelDistanceService travelDistanceService;
    private final IDroneApiService droneApiService;
    private final ILocalDroneDao localDroneDao;
    private final IReverseGeocodeService reverseGeocodeService;

    public DroneDashboardController(ITravelDistanceService travelDistanceService, IDroneApiService droneApiService, ILocalDroneDao localDroneDao, IReverseGeocodeService reverseGeocodeService) {
        this.travelDistanceService = travelDistanceService;
        this.droneApiService = droneApiService;
        this.localDroneDao = localDroneDao;
        this.reverseGeocodeService = reverseGeocodeService;
    }

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
