package main.java.service;

import main.java.api.DroneApiService;
import main.java.api.IDroneApiService;
import main.java.dao.LocalDroneDao;
import main.java.dao.LocalDroneDaoImpl;
import org.junit.jupiter.api.Test;

public class LocalSearchServiceTest {

    @Test
    void initTest() {
        LocalDroneDao localDroneDao = new LocalDroneDaoImpl();
        IDroneApiService droneApiService = new DroneApiService(System.getenv("DRONE_API_KEY"));
        LocalSearchService localSearchService = new LocalSearchService(localDroneDao, droneApiService);

        localSearchService.initLocalData();

    }
}
