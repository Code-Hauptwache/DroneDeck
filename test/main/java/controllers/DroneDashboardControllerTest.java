package main.java.controllers;

import main.java.ui.dtos.DroneDashboardCardDto;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DroneDashboardControllerTest {

    @Test
    void test() {
        DroneDashboardController droneDashboardController = new DroneDashboardController();

        long start = System.currentTimeMillis();
        List<DroneDashboardCardDto> droneDashboardCards = droneDashboardController.getDroneDashboardCardsThreads(6, 0);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");

        for (DroneDashboardCardDto droneDashboardCard : droneDashboardCards) {
            System.out.println("droneDashboardCard.toString() = " + droneDashboardCard.toString());
        }
    }
}
