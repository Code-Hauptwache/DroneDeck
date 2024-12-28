package main.java.services;

import main.java.api.DroneApiService;
import main.java.api.IDroneApiService;
import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.entity.DroneEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Test Class for LocalSearchService
 */
public class LocalSearchServiceTest {

    ILocalSearchService localSearchService;

    @BeforeEach
    void setUp() {
        ILocalDroneDao localDroneDao = new LocalDroneDao();
        IDroneApiService droneApiService = new DroneApiService(System.getenv("DRONE_API_KEY"));
        localSearchService = new LocalSearchService(localDroneDao, droneApiService);

        localSearchService.initLocalData();
    }

    @Test
    void loadDrones() {
        List<DroneEntity> drones = localSearchService.getAllDrones();

        for (DroneEntity drone : drones) {
            System.out.println("drone = " + drone);
        }

    }

    @Test
    void searchDrones() {
        List<DroneEntity> drones = localSearchService.findDronesByKeyword("evo");

        for (DroneEntity drone : drones) {
            System.out.println(drone);
        }

        // singleton start
        long start = System.currentTimeMillis();
        List<DroneEntity> drones1 = localSearchService.findDronesByKeyword("hub");
        for (DroneEntity drone : drones1) {
            System.out.println(drone);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");

    }
}
