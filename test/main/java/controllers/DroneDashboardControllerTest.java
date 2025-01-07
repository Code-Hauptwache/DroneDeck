package main.java.controllers;

import main.java.ui.dtos.DroneDashboardDto;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DroneDashboardControllerTest {

    @Test
    void test() {
        DroneDashboardController droneDashboardController = new DroneDashboardController();

        long start = System.currentTimeMillis();
        List<DroneDashboardDto> droneDashboardCards = droneDashboardController.getDroneDashboardCardsThreads(6, 0);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");

        for (DroneDashboardDto droneDashboardCard : droneDashboardCards) {
            System.out.println("droneDashboardCard.toString() = " + droneDashboardCard.toString());
        }
    }
}
