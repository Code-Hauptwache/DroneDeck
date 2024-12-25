package main.java.service;

import main.java.api.DroneApiService;
import main.java.api.IDroneApiService;
import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneType;
import main.java.dao.LocalDroneDao;
import main.java.dao.LocalDroneDaoImpl;
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
        List<Drone> drones = localSearchService.getAllDrones();
        List<DroneType> droneTypes = localSearchService.getAllDroneTypes();

        for (Drone drone : drones) {
            System.out.println("drone.serialnumber = " + drone.serialnumber);
        }

        for (DroneType droneType : droneTypes) {
            System.out.println("droneType.typename = " + droneType.typename);
        }

    }

    @Test
    void searchDrones() {
        LocalDroneDao localDroneDao = new LocalDroneDaoImpl();
        IDroneApiService droneApiService = new DroneApiService(System.getenv("DRONE_API_KEY"));
        LocalSearchService localSearchService = new LocalSearchServiceImpl(localDroneDao, droneApiService);

        localSearchService.initLocalData();

        List<Drone> drones = localSearchService.findDroneByKeyword("70");

        for (Drone drone : drones) {
            System.out.println("drone.droneType = " + drone.dronetype);
        }
    }
}
