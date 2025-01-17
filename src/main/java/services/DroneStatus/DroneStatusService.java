package main.java.services.DroneStatus;

import main.java.controllers.DroneController;
import main.java.entity.DroneEntity;
import main.java.ui.dtos.DroneDto;

import java.util.List;

/**
 * Service to get the status of all drones.
 */
public class DroneStatusService {
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
        for (DroneEntity drone : drones) {
            DroneDto droneDto = droneController.getDroneDto(drone);
            String status = droneDto.getStatus();
            switch (status) {
                case "ON" -> onlineCount++;
                case "IS" -> issueCount++;
                case "OF" -> offlineCount++;
            }
        }
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