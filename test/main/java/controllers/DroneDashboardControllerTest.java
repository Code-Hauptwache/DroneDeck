package main.java.controllers;

import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.ReverseGeocode.ReverseGeocodeService;
import main.java.services.TravelDistance.ITravelDistanceService;
import main.java.services.TravelDistance.TravelDistanceService;
import main.java.ui.dtos.DroneDashboardCardDto;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DroneDashboardControllerTest {

    @Test
    void test() {
        IDroneApiService droneApiService = new DroneApiService(System.getenv("DRONE_API_KEY"));
        ITravelDistanceService travelDistanceService = new TravelDistanceService(droneApiService);
        ILocalDroneDao localDroneDao = new LocalDroneDao();
        IReverseGeocodeService reverseGeocodeService = new ReverseGeocodeService();
        DroneDashboardController droneDashboardController = new DroneDashboardController(
                travelDistanceService, droneApiService, localDroneDao,
                reverseGeocodeService);

        long start = System.currentTimeMillis();
        List<DroneDashboardCardDto> droneDashboardCards = droneDashboardController.getDroneDashboardCardsThreads(6, 0);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");

        for (DroneDashboardCardDto droneDashboardCard : droneDashboardCards) {
            System.out.println("droneDashboardCard.toString() = " + droneDashboardCard.toString());
        }
    }
}
