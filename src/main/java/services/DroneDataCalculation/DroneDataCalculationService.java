package main.java.services.DroneDataCalculation;

import main.java.exceptions.DroneApiException;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.DroneDynamics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A service that provides travel distance of drone
 */
public class DroneDataCalculationService implements IDroneDataCalculationService {

    private static final Logger logger = Logger.getLogger(DroneDataCalculationService.class.getName());

    private final IDroneApiService droneApiService;
    private static final int DATA_NUM = 500;
    private static final int LIMIT = 100;
    private static final int THREAD_NUM = DATA_NUM / LIMIT;
    private static final double MILLISECONDS_TO_HOURS = 3600000.0; // 1 hour = 3600000 milliseconds

    /**
     * Constructor for DroneDataCalculationService
     * @param droneApiService the impl object of IDroneApiService
     */
    public DroneDataCalculationService(IDroneApiService droneApiService) {
        this.droneApiService = droneApiService;
    }

    /**
     * Method that returns calculated data of total travel distance in kilometers
     * @param droneId the id of the drone
     * @return total travel distance in kilometers
     */
    @Override
    public double getTravelDistance(int droneId) {
        try {
            ArrayList<DroneDynamics> droneDynamics = getDroneDynamicsMultiThreaded(droneId);
            return calculateTravelDistance(droneDynamics);
        } catch (DroneApiException e) {
            logger.log(Level.SEVERE, "Failed to get drone dynamics.", e);
            return 0;
        }
    }

    /**
     * Method that returns calculated data of average speed in kilometers per hour
     * <br><br>
     * <b>NOTE</b>:<br>
     * Since this method needs to calculate the total travel distance,
     * it is recommended to use calculateTravelDistanceAndAverageSpeed method instead
     * @param droneId the id of the drone
     * @return average speed in kilometers per hour
     */
    @Override
    public double getAverageSpeed(int droneId) {
        try {
            ArrayList<DroneDynamics> droneDynamics = getDroneDynamicsMultiThreaded(droneId);
            double totalDistance = calculateTravelDistance(droneDynamics);
            return calculateAverageSpeed(droneDynamics, totalDistance);
        } catch (DroneApiException e) {
            logger.log(Level.SEVERE, "Failed to get drone dynamics.", e);
            return 0;
        }
    }

    /**
     * Method that returns calculated data of total travel distance (km) and average speed (km/h)
     * @param droneId the id of the drone
     * @return DataCalculationResult object that contains total travel distance and average speed
     */
    @Override
    public DataCalculationResult getTravelDistanceAndAverageSpeed(int droneId) {
        try {
            ArrayList<DroneDynamics> droneDynamics = getDroneDynamicsMultiThreaded(droneId);
            double totalDistance = calculateTravelDistance(droneDynamics);
            double averageSpeed = calculateAverageSpeed(droneDynamics, totalDistance);
            return new DataCalculationResult(totalDistance, averageSpeed);
        } catch (DroneApiException e) {
            logger.log(Level.SEVERE, "Failed to get drone dynamics.", e);
            return new DataCalculationResult(0, 0);
        }
    }

    private ArrayList<DroneDynamics> getDroneDynamicsMultiThreaded(int droneId) throws DroneApiException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);

        //Get the drone dynamics data using multiple threads
        List<CompletableFuture<ArrayList<DroneDynamics>>> futures = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            int taskId = i;
            futures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return droneApiService.getDroneDynamicsByDroneId(droneId, LIMIT, taskId * LIMIT);
                } catch (DroneApiException e) {
                    logger.log(Level.SEVERE, "Failed to get drone dynamics.", e);
                    return new ArrayList<>();
                }
            }, executorService));
        }

        // Combine all the results to a single list
        ArrayList<DroneDynamics> droneDynamics = new ArrayList<>();
        for (CompletableFuture<ArrayList<DroneDynamics>> future : futures) {
            try {
                droneDynamics.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.log(Level.SEVERE, "Failed to get drone dynamics.", e);
            }
        }

        executorService.shutdown();

        return droneDynamics;
    }

    private double calculateTravelDistance(ArrayList<DroneDynamics> droneDynamics) {
        //Only map/stream the data that we need e.g. from offset to offset + LIMIT
        List<Coordinate> coordinates = droneDynamics.stream()
                .map(d -> new Coordinate(d.longitude, d.latitude))
                .toList();

        double sum = 0.0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            sum += Coordinate.distance(coordinates.get(i), coordinates.get(i + 1));
        }

        return sum;
    }

    private double calculateAverageSpeed(ArrayList<DroneDynamics> droneDynamics, double totalDistance) {
        if (droneDynamics.isEmpty()) {
            return 0;
        }

        DroneDynamics first = droneDynamics.getFirst();
        DroneDynamics last = droneDynamics.getLast();
        Date firstTimestamp = first.timestamp;
        Date lastTimestamp = last.timestamp;

        double diff = lastTimestamp.getTime() - firstTimestamp.getTime();

        double totalTime = diff / MILLISECONDS_TO_HOURS;

        return totalDistance / totalTime;
    }
}