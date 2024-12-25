package main.java.service;

import main.java.api.IDroneApiService;
import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneType;
import main.java.api.exceptions.DroneApiException;
import main.java.dao.LocalDroneDao;

import java.util.List;
import java.util.stream.Collectors;

public interface LocalSearchService {

    void initLocalData();

    List<Drone> getAllDrones();

    List<DroneType> getAllDroneTypes();

    List<Drone> findDroneByKeyword(String keyword);
}
