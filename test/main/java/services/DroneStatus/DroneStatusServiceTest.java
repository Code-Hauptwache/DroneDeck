package main.java.services.DroneStatus;

import main.java.controllers.DroneController;
import main.java.entity.DroneEntity;
import main.java.ui.dtos.DroneDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DroneStatusServiceTest {

    private DroneStatusService droneStatusService;

    private static class TestDroneController extends DroneController {
        @Override
        public List<DroneEntity> getAllDrones() {
            return Arrays.asList(
                    new DroneEntity(1, new Date(), "123", 10, "SEN"),
                    new DroneEntity(2, new Date(), "456", 15, "ACT"),
                    new DroneEntity(3, new Date(), "789", 20, "NONE"),
                    new DroneEntity(4, new Date(), "101", 25, "SEN"),
                    new DroneEntity(5, new Date(), "112", 30, "ACT"),
                    new DroneEntity(6, new Date(), "131", 35, "NONE"),
                    new DroneEntity(7, new Date(), "141", 40, "SEN"),
                    new DroneEntity(8, new Date(), "151", 45, "ACT"),
                    new DroneEntity(9, new Date(), "161", 50, "NONE")
            );
        }

        @Override
        public DroneDto getDroneDto(DroneEntity drone) {
            switch (drone.getId()) {
                case 1, 4, 7, 9 -> {
                    return new DroneDto(1, "Type1", "Manufacturer1", "ON", 100, 100, 50, 10.0, 20.0, "ON123", 10, "SEN", 50, "2023-10-10T00:00:00Z", 100, 200, 1000, "2023-10-10T00:00:00Z");
                }
                case 2, 5 -> {
                    return new DroneDto(2, "Type2", "Manufacturer2", "IS", 100, 100, 50, 10.0, 20.0, "IS456", 15, "ACT", 50, "2023-10-10T00:00:00Z", 100, 200, 1000, "2023-10-10T00:00:00Z");
                }
                case 3, 6, 8 -> {
                    return new DroneDto(3, "Type3", "Manufacturer3", "OF", 100, 100, 50, 10.0, 20.0, "OFF789", 20, "NONE", 50, "2023-10-10T00:00:00Z", 100, 200, 1000, "2023-10-10T00:00:00Z");
                }
                default -> throw new IllegalArgumentException("Unexpected drone ID: " + drone.getId());
            }
        }
    }

    @BeforeEach
    void setUp() {
        DroneController droneController = new TestDroneController();
        droneStatusService = new DroneStatusService(droneController);
    }

    @Test
    void testCheckDroneStatuses() {
        assertEquals(4, droneStatusService.getOnlineCount());
        assertEquals(2, droneStatusService.getIssueCount());
        assertEquals(3, droneStatusService.getOfflineCount());

        System.out.println("Online Count: " + droneStatusService.getOnlineCount());
        System.out.println("Issue Count: " + droneStatusService.getIssueCount());
        System.out.println("Offline Count: " + droneStatusService.getOfflineCount());
    }
}