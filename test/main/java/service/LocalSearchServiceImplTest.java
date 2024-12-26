package main.java.service;

import main.java.api.DroneApiService;
import main.java.api.IDroneApiService;
import main.java.dao.LocalDroneDao;
import main.java.dao.LocalDroneDaoImpl;
import main.java.entity.DroneEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LocalSearchServiceImplTest {

    LocalSearchService localSearchService;

    @BeforeEach
    void setUp() {
        LocalDroneDao localDroneDao = new LocalDroneDaoImpl();
        IDroneApiService droneApiService = new DroneApiService(System.getenv("DRONE_API_KEY"));
        localSearchService = new LocalSearchServiceImpl(localDroneDao, droneApiService);

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
        List<DroneEntity> drones = localSearchService.findDronesByKeyword("Evo");

        for (DroneEntity drone : drones) {
            System.out.println(drone);
        }

        // singleton start
        long start = System.currentTimeMillis();
        List<DroneEntity> drones1 = localSearchService.findDronesByKeyword("Evo");
        for (DroneEntity drone : drones1) {
            System.out.println(drone);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");

    }
}
