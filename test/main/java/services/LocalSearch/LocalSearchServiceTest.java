package main.java.services.LocalSearch;

import main.java.dao.ILocalDroneDao;
import main.java.dao.ILocalDroneTypeDao;
import main.java.dao.LocalDroneDao;
import main.java.dao.LocalDroneTypeDao;
import main.java.entity.DroneEntity;
import main.java.entity.DroneTypeEntity;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Test Class for LocalSearchService
 */
class LocalSearchServiceTest {
    ILocalSearchService localSearchService;
    ILocalDroneTypeDao localDroneTypeDao;

    @BeforeEach
    void setUp() {
        ILocalDroneDao localDroneDao = new LocalDroneDao();
        localDroneTypeDao = new LocalDroneTypeDao();
        IDroneApiService droneApiService = new DroneApiService(System.getenv("DRONE_API_KEY"));
        localSearchService = new LocalSearchService(localDroneDao, localDroneTypeDao, droneApiService);

        localSearchService.initLocalData();
    }

    @Test
    void loadDrones() {
        List<DroneEntity> drones = localSearchService.getAllDrones();

        // Print the count of drones
        System.out.println("drones.size() = " + drones.size());

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
        List<DroneEntity> drones1 = localSearchService.findDronesByKeyword("YuTy-2027.033F54");
        for (DroneEntity drone : drones1) {
            System.out.println(drone);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
    }

    @Test
    void testDroneTypeData() {
        List<DroneTypeEntity> droneTypes = localDroneTypeDao.loadDroneTypeData();

        // Print the count of drone types
        System.out.println("droneTypes.size() = " + droneTypes.size());

        // Print the list of drone types
        for (DroneTypeEntity droneType : droneTypes) {
            System.out.println("droneType = " + droneType);
        }
    }
}