package main.java.controllers;

import main.java.ui.dtos.DroneDto;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DroneDashboardControllerTest {

    @Test
    void test() {
        DroneDashboardController droneDashboardController = new DroneDashboardController();

        long start = System.currentTimeMillis();
        List<DroneDto> droneDashboardCards = droneDashboardController.getDroneThreads(6, 0);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");

        for (DroneDto droneDashboardCard : droneDashboardCards) {
            System.out.println("droneDashboardCard.toString() = " + droneDashboardCard.toString());
        }
    }
}
