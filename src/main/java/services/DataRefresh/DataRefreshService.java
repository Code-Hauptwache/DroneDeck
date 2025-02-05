package main.java.services.DataRefresh;

import main.java.controllers.DroneController;
import main.java.ui.pages.DroneCatalog;
import main.java.ui.pages.DroneDashboard;
import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.DroneDynamicsResponse;
import main.java.ui.dtos.DroneDto;
import java.util.List;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service responsible for automatically refreshing drone data at regular intervals.
 */
public class DataRefreshService {
    private static final Logger logger = Logger.getLogger(DataRefreshService.class.getName());
    private static final int REFRESH_INTERVAL_SECONDS = 60;
    
    private static DataRefreshService instance;
    private final DroneController droneController;
    private final ScheduledExecutorService scheduler;
    private final ILocalDroneDao localDroneDao;

    private DataRefreshService() {
        this.droneController = new DroneController();
        this.localDroneDao = new LocalDroneDao();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        startPeriodicRefresh();
    }

    public static synchronized DataRefreshService getInstance() {
        if (instance == null) {
            instance = new DataRefreshService();
        }
        return instance;
    }

    private void startPeriodicRefresh() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                refreshData();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error refreshing drone data", e);
                // Schedule a retry after a short delay
                scheduler.schedule(this::refreshData, 5, TimeUnit.SECONDS);
            }
        }, 0, REFRESH_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    private void refreshData() {
        logger.info("Starting data refresh at " + new java.util.Date());
        try {
            // 1. Fetch basic drone data
            final int droneCount = localDroneDao.getDroneDataCount();
            logger.info("Fetching data for " + droneCount + " drones");
            List<DroneDto> drones = droneController.getDroneThreads(droneCount, 0);
            logger.info("Successfully updated basic drone data");

            // 2. Pre-fetch dynamics data for all drones
            for (DroneDto drone : drones) {
                try {
                    DroneDynamicsResponse dynamicsResponse = droneController.getDroneApiService()
                            .getDroneDynamicsResponseByDroneId(drone.getId(), 1, 0);
                    droneController.getDroneApiService()
                            .getDroneDynamicsByDroneId(drone.getId(), 1, dynamicsResponse.getCount() - 1);
                    logger.info("Updated dynamics data for drone " + drone.getId());
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Failed to update dynamics for drone " + drone.getId(), e);
                }
            }

            // 3. Update UI on EDT
            SwingUtilities.invokeLater(() -> {
                DroneDashboard dashboard = DroneDashboard.getInstance();
                DroneCatalog catalog = DroneCatalog.getInstance();
                
                dashboard.updateDrones("");
                catalog.updateDroneTypes("");
                logger.info("UI updated with refreshed data");
            });

            logger.info("Data refresh completed successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during data refresh", e);
            throw e; // Let the scheduler handle retry
        }
    }

    public IDroneApiService getDroneApiService() {
        return droneController.getDroneApiService();
    }

    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
