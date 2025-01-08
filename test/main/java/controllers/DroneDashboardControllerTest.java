package main.java.controllers;

import main.java.ui.dtos.DroneDto;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DroneDashboardControllerTest {

    @Test
    void test() {
        DroneDashboardController droneDashboardController = new DroneDashboardController();

        long start = System.currentTimeMillis();
        List<DroneDto> droneDtoList = droneDashboardController.getDroneThreads(6, 0);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");

        for (DroneDto droneDto : droneDtoList) {
            System.out.println("droneDto.toString() = " + droneDto.toString());
        }
    }
}
