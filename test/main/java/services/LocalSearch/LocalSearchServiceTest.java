package main.java.services.LocalSearch;

import main.java.dao.ILocalDroneDao;
import main.java.dao.ILocalDroneTypeDao;
import main.java.dao.LocalDroneDao;
import main.java.dao.LocalDroneTypeDao;
import main.java.entity.DroneEntity;
import main.java.entity.DroneTypeEntity;
import main.java.services.ApiToken.ApiTokenService;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test Class for LocalSearchService
 */
class LocalSearchServiceTest {
    ILocalSearchService localSearchService;
    ILocalDroneTypeDao localDroneTypeDao;

    @BeforeAll
    static void initialSetup() {
        //Initialize the parent frame for the dialog
        JFrame frame = new JFrame();
        ApiTokenService.setParent(frame);
    }

    @BeforeEach
    void setUp() {
        ILocalDroneDao localDroneDao = new LocalDroneDao();
        localDroneTypeDao = new LocalDroneTypeDao();

        String apiToken = ApiTokenService.getApiToken();

        IDroneApiService droneApiService = new DroneApiService(apiToken);
        localSearchService = LocalSearchService.createInstance(localDroneDao, localDroneTypeDao, droneApiService);

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
        List<DroneEntity> drones1 = localSearchService.findDronesByKeyword("hub");
        for (DroneEntity drone : drones1) {
            System.out.println(drone);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
    }

    @Test
    void searchDroneTypes() {
        List<DroneTypeEntity> droneTypes = localSearchService.findDroneTypesByKeyword("evo");

        assertNotNull(droneTypes, "The drone types list should not be null");
        assertFalse(droneTypes.isEmpty(), "The drone types list should not be empty");

        for (DroneTypeEntity droneType : droneTypes) {
            System.out.println(droneType);
        }

        // Additional test case for a specific keyword
        List<DroneTypeEntity> specificDroneTypes = localSearchService.findDroneTypesByKeyword("hub");
        for (DroneTypeEntity droneType : specificDroneTypes) {
            System.out.println(droneType);
        }
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