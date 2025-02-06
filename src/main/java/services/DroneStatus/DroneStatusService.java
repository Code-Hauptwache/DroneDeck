package main.java.services.DroneStatus;

import main.java.controllers.DroneController;
import main.java.entity.DroneEntity;
import main.java.ui.dtos.DroneDto;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service to get the status of all drones.
 */
public class DroneStatusService {

    private static final int THREAD_NUM = 40;

    private final Logger logger = Logger.getLogger(DroneStatusService.class.getName());

    private final DroneController droneController;
    private int onlineCount;
    private int issueCount;
    private int offlineCount;

    /**
     * Constructor for DroneStatusService.
     *
     * @param droneController the drone controller
     */
    public DroneStatusService(DroneController droneController) {
        this.droneController = droneController;
        checkDroneStatuses();
    }

    private void checkDroneStatuses() {
        List<DroneEntity> drones = droneController.getAllDrones();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);

        ArrayList<CompletableFuture<String>> futures = new ArrayList<>();

        for (DroneEntity drone : drones) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                DroneDto droneDto = droneController.getDroneDto(drone);
                logger.log(Level.INFO, "Got status for drone " + droneDto.getId());
                return droneDto.getStatus();
            }, executorService);
            futures.add(future);
        }

        for (CompletableFuture<String> future : futures) {
            try {
                String status = future.get();
                switch (status) {
                    case "ON" -> onlineCount++;
                    case "IS" -> issueCount++;
                    case "OF" -> offlineCount++;
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to get drone status.", e);
            }
        }
        logger.log(Level.INFO, "Loaded Drone Statuses; Online: " + onlineCount + ", Issue: " + issueCount + ", Offline: " + offlineCount);
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public int getOfflineCount() {
        return offlineCount;
    }
}