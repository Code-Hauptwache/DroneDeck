package main.java.services.TravelDistance;

import main.java.exceptions.DroneApiException;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.DroneDynamics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * A service that provides travel distance of drone
 */
public class TravelDistanceService implements ITravelDistanceService {

    private final IDroneApiService droneApiService;
    private static final int DATA_NUM = 500;
    private static final int LIMIT = 100;
    private static final int THREAD_NUM = DATA_NUM / LIMIT;

    /**
     * Constructor for TravelDistanceService
     * @param droneApiService the impl object of IDroneApiService
     */
    public TravelDistanceService(IDroneApiService droneApiService) {
        this.droneApiService = droneApiService;
    }

    /**
     * Calculate distance from 500 data of dynamic drone list
     * Using threads for faster calculate
     * @param droneId drone's ID that we want to find
     * @return total travel distance of drone (km)
     */
    @Override
    public double getTravelDistance(int droneId) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);

        List<CompletableFuture<Double>> futures = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            int taskId = i;
            futures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return getDistanceSum(droneId, taskId * LIMIT);
                } catch (DroneApiException e) {
                    e.printStackTrace();
                    // TODO: Handle exception
                    return 0.0;
                }
            }, executorService));
        }

        double sum = futures.stream()
                .mapToDouble(CompletableFuture::join)
                .sum();

        executorService.shutdown();
        return sum;
    }

    private double getDistanceSum(int droneId, int offset) throws DroneApiException {
        System.out.println("Requesting drone dynamics for drone ID: " + droneId + " with offset: " + offset);
        ArrayList<DroneDynamics> droneDynamics = droneApiService.getDroneDynamicsByDroneId(droneId, LIMIT, offset);

        List<Coordinate> coordinates = droneDynamics.stream()
                .map(d -> new Coordinate(d.longitude, d.latitude))
                .toList();

        double sum = 0.0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            sum += Coordinate.distance(coordinates.get(i), coordinates.get(i + 1));
        }

        return sum;
    }
}