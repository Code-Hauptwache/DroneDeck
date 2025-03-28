package main.java.services.DataRefresh;

import main.java.controllers.DroneController;
import main.java.ui.pages.DroneCatalog;
import main.java.ui.pages.DroneDashboard;
import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.services.DroneApi.ApiModeConfig;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.DroneDynamicsResponse;
import main.java.ui.dtos.DroneDto;
import main.java.services.LocalSearch.LocalSearchService;
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
    private static final int REFRESH_INTERVAL_SECONDS = 300;
    
    private static DataRefreshService instance;
    private final DroneController droneController;
    private final ScheduledExecutorService scheduler;
    private final ILocalDroneDao localDroneDao;
private DataRefreshService() {
    this.droneController = new DroneController();
    this.localDroneDao = new LocalDroneDao();
    this.scheduler = Executors.newSingleThreadScheduledExecutor();
    
    // In demo mode, we use a longer refresh interval since data is static
    if (ApiModeConfig.isDemoMode()) {
        logger.info("🧪 DataRefreshService initialized in DEMO MODE with mock data");
    }
    
    startPeriodicRefresh();
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
        }, REFRESH_INTERVAL_SECONDS, REFRESH_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    private void refreshData() {
        logger.info("\n🔄 Starting periodic data refresh cycle");
        
        // In demo mode, we don't need to fetch real data - our mock data is static
        if (ApiModeConfig.isDemoMode()) {
            logger.info("🧪 DEMO MODE: Using static mock data instead of refreshing from API");
            
            // Just update the UI with the existing mock data
            SwingUtilities.invokeLater(() -> {
                DroneDashboard dashboard = DroneDashboard.getInstance();
                DroneCatalog catalog = DroneCatalog.getInstance();
                
                dashboard.updateDrones("");
                catalog.updateDroneTypes("");
                logger.info("✨ UI components updated with mock data");
            });
            
            logger.info("✅ Demo mode refresh cycle completed successfully\n");
            return;
        }
        
        try {
            // For live mode, proceed with normal API refresh:
            
            // 1. Update local data storage with fresh API data
            logger.info("📥 Fetching fresh data from API and updating local storage...");
            LocalSearchService.getCurrentInstance().initLocalData();
            logger.info("💾 Local data storage updated successfully with latest drone and drone type data");

            // 2. Get drone DTOs for dynamics update
            final int droneCount = localDroneDao.getDroneDataCount();
            logger.info("🔍 Preparing to update dynamics for " + droneCount + " drones");
            List<DroneDto> drones = droneController.getDroneThreads(droneCount, 0);

            // 3. Pre-fetch dynamics data for all drones
            logger.info("📊 Starting dynamics data update");
            int completedCount = 0;
            int batchSize = 10;
            long startTime = System.currentTimeMillis();

            for (DroneDto drone : drones) {
                try {
                    DroneDynamicsResponse dynamicsResponse = droneController.getDroneApiService()
                            .getDroneDynamicsResponseByDroneId(drone.getId(), 1, 0);
                    droneController.getDroneApiService()
                            .getDroneDynamicsByDroneId(drone.getId(), 1, dynamicsResponse.getCount() - 1);
                    completedCount++;
                    
                    // Log progress in batches
                    if (completedCount % batchSize == 0 || completedCount == drones.size()) {
                        double progress = (completedCount * 100.0) / drones.size();
                        long currentTime = System.currentTimeMillis();
                        double timeElapsed = (currentTime - startTime) / 1000.0;
                        logger.info(String.format("⏳ Dynamics update progress: %.1f%% (%d/%d drones) - %.1f seconds elapsed",
                            progress, completedCount, drones.size(), timeElapsed));
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, "⚠️ Failed to update dynamics for drone " + drone.getId() + ": " + e.getMessage());
                }
            }
            
            long totalTime = (System.currentTimeMillis() - startTime) / 1000;
            logger.info(String.format("✅ Dynamics update completed in %d seconds", totalTime));

            // 4. Update UI on EDT
            SwingUtilities.invokeLater(() -> {
                DroneDashboard dashboard = DroneDashboard.getInstance();
                DroneCatalog catalog = DroneCatalog.getInstance();
                
                dashboard.updateDrones("");
                catalog.updateDroneTypes("");
                logger.info("✨ UI components updated with fresh data");
            });

            logger.info("✅ Live data refresh cycle completed successfully\n");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Error during data refresh", e);
            throw e; // Let the scheduler handle retry
        }
    }

    public void triggerRefreshData() {
        refreshData();
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
