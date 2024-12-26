package main.java.service;

import main.java.api.IDroneApiService;
import main.java.api.dtos.Drone;
import main.java.api.dtos.DroneType;
import main.java.api.exceptions.DroneApiException;
import main.java.dao.LocalDroneDao;
import main.java.entity.DroneEntity;

import java.util.List;
import java.util.stream.Collectors;

public interface LocalSearchService {

    void initLocalData();

    List<DroneEntity> getAllDrones();

    List<DroneEntity> findDroneByKeyword(String keyword);
}
