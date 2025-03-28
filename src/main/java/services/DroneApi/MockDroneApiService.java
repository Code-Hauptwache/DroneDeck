package main.java.services.DroneApi;

import main.java.exceptions.DroneApiException;
import main.java.services.DroneApi.dtos.Drone;
import main.java.services.DroneApi.dtos.DroneDynamics;
import main.java.services.DroneApi.dtos.DroneDynamicsResponse;
import main.java.services.DroneApi.dtos.DroneType;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Mock implementation of the DroneApiService for demo purposes.
 * This class provides static mock data without requiring the actual API.
 */
public class MockDroneApiService implements IDroneApiService {
    private static final Logger logger = Logger.getLogger(MockDroneApiService.class.getName());
    
    private String apiKey = "mock-api-key";
    
    // Static collections for our mock data
    private final List<DroneType> mockDroneTypes;
    private final List<Drone> mockDrones;
    private final List<DroneDynamics> mockDroneDynamics;
    
    // Cache maps for faster lookups
    private final Map<Integer, DroneType> droneTypeMap = new HashMap<>();
    private final Map<Integer, Drone> droneMap = new HashMap<>();
    private final Map<Integer, List<DroneDynamics>> droneDynamicsMap = new HashMap<>();
    
    public MockDroneApiService() {
        logger.info("🔄 Initializing Mock Drone API Service for demo mode");
        
        // Initialize our mock data
        mockDroneTypes = createMockDroneTypes();
        mockDrones = createMockDrones();
        mockDroneDynamics = createMockDroneDynamics();
        
        // Build lookup maps
        mockDroneTypes.forEach(type -> droneTypeMap.put(type.id, type));
        mockDrones.forEach(drone -> droneMap.put(drone.id, drone));
        
        // Group drone dynamics by drone ID
        mockDroneDynamics.forEach(dynamics -> {
            int droneId = dynamics.getId();
            if (!droneDynamicsMap.containsKey(droneId)) {
                droneDynamicsMap.put(droneId, new ArrayList<>());
            }
            droneDynamicsMap.get(droneId).add(dynamics);
        });
        
        logger.info("✅ Mock data initialized successfully");
    }
    
    @Override
    public String getApiKey() {
        return apiKey;
    }
    
    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    // DRONE DYNAMICS METHODS
    
    @Override
    public ArrayList<DroneDynamics> getDroneDynamics(int limit, int offset) throws DroneApiException {
        logger.info("Mock: Getting drone dynamics with limit " + limit + " and offset " + offset);
        return new ArrayList<>(mockDroneDynamics.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList()));
    }
    
    @Override
    public ArrayList<DroneDynamics> getDroneDynamics(int offset) throws DroneApiException {
        return getDroneDynamics(100, offset);
    }
    
    @Override
    public ArrayList<DroneDynamics> getDroneDynamics() throws DroneApiException {
        return getDroneDynamics(100, 0);
    }
    
    @Override
    public DroneDynamics getDroneDynamicsById(int id) throws DroneApiException {
        logger.info("Mock: Getting drone dynamics by ID " + id);
        return mockDroneDynamics.stream()
                .filter(dynamics -> dynamics.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DroneApiException("Drone dynamics not found with ID: " + id));
    }
    
    // DRONES METHODS
    
    @Override
    public ArrayList<Drone> getDrones(int limit, int offset) throws DroneApiException {
        logger.info("Mock: Getting drones with limit " + limit + " and offset " + offset);
        return new ArrayList<>(mockDrones.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList()));
    }
    
    @Override
    public ArrayList<Drone> getDrones(int offset) throws DroneApiException {
        return getDrones(100, offset);
    }
    
    @Override
    public ArrayList<Drone> getDrones() throws DroneApiException {
        return getDrones(100, 0);
    }
    
    @Override
    public Drone getDronesById(int id) throws DroneApiException {
        logger.info("Mock: Getting drone by ID " + id);
        return mockDrones.stream()
                .filter(drone -> drone.id == id)
                .findFirst()
                .orElseThrow(() -> new DroneApiException("Drone not found with ID: " + id));
    }
    
    // DRONE TYPES METHODS
    
    @Override
    public ArrayList<DroneType> getDroneTypes(int limit, int offset) throws DroneApiException {
        logger.info("Mock: Getting drone types with limit " + limit + " and offset " + offset);
        return new ArrayList<>(mockDroneTypes.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList()));
    }
    
    @Override
    public ArrayList<DroneType> getDroneTypes(int offset) throws DroneApiException {
        return getDroneTypes(100, offset);
    }
    
    @Override
    public ArrayList<DroneType> getDroneTypes() throws DroneApiException {
        return getDroneTypes(100, 0);
    }
    
    @Override
    public DroneType getDroneTypeById(int id) throws DroneApiException {
        logger.info("Mock: Getting drone type by ID " + id);
        return mockDroneTypes.stream()
                .filter(type -> type.id == id)
                .findFirst()
                .orElseThrow(() -> new DroneApiException("Drone type not found with ID: " + id));
    }
    
    // DRONE DYNAMICS BY DRONE ID METHODS
    
    @Override
    public DroneDynamicsResponse getDroneDynamicsResponseByDroneId(int id, int limit, int offset) throws DroneApiException {
        logger.info("Mock: Getting drone dynamics response by drone ID " + id);
        
        // Get the dynamics for this drone
        List<DroneDynamics> dynamics = droneDynamicsMap.getOrDefault(id, new ArrayList<>());
        
        // Create a proper response with correct count
        DroneDynamicsResponse response = new DroneDynamicsResponse();
        
        // When we have no dynamics, we need to fake it with a count of at least 1
        // This ensures getDroneDynamicsByDroneId will be called with proper parameters
        if (dynamics.isEmpty()) {
            // Create a default dynamic entry for this drone
            DroneDynamics defaultDynamic = createDefaultDynamics(id);
            
            // Add it to our map for future reference
            List<DroneDynamics> newList = new ArrayList<>();
            newList.add(defaultDynamic);
            droneDynamicsMap.put(id, newList);
            
            // Set count to 1 and add the dynamic to results
            response.count = 1;
            response.results.add(defaultDynamic);
        } else {
            // We have real dynamics data
            response.count = dynamics.size();
            
            // If requested, add some results to the response
            if (limit > 0) {
                int effectiveOffset = Math.min(offset, dynamics.size() - 1);
                effectiveOffset = Math.max(0, effectiveOffset);
                
                int end = Math.min(effectiveOffset + limit, dynamics.size());
                for (int i = effectiveOffset; i < end; i++) {
                    response.results.add(dynamics.get(i));
                }
            }
        }
        
        response.next = "";
        response.previous = "";
        
        logger.info("Mock: Returning dynamics response with count: " + response.count);
        return response;
    }
    
    @Override
    public ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int limit, int offset) throws DroneApiException {
        logger.info("Mock: Getting drone dynamics by drone ID " + id + " with limit " + limit + " and offset " + offset);
        List<DroneDynamics> dynamicsList = droneDynamicsMap.getOrDefault(id, new ArrayList<>());
        
        // Ensure we always have at least one dynamic entry per drone
        if (dynamicsList.isEmpty()) {
            DroneDynamics defaultDynamic = createDefaultDynamics(id);
            dynamicsList = Collections.singletonList(defaultDynamic);
            logger.warning("No dynamics found for drone " + id + ", creating default");
        }
        
        // Handle negative or out-of-bounds offset
        if (offset < 0 || offset >= dynamicsList.size()) {
            logger.info("Offset " + offset + " is out of bounds, returning latest dynamics");
            // Return the latest entry (last in the list)
            return new ArrayList<>(Collections.singletonList(dynamicsList.getLast()));
        }
        
        return new ArrayList<>(dynamicsList.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList()));
    }
    
    // Helper method to create a default dynamic entry with good battery and status
    private DroneDynamics createDefaultDynamics(int droneId) {
        DroneDynamics dynamics = new DroneDynamics();
        dynamics.drone = "http://dronesim.facets-labs.com/api/drones/" + droneId + "/";
        dynamics.timestamp = new Date();
        dynamics.last_seen = new Date();
        dynamics.status = "ON"; // Set to ONLINE by default
        dynamics.battery_status = 85; // Good battery level
        dynamics.speed = 45;
        
        // Frankfurt coordinates
        dynamics.latitude = 50.110924 + (droneId * 0.01);
        dynamics.longitude = 8.682127 + (droneId * 0.01);
        
        dynamics.align_roll = 0.0f;
        dynamics.align_pitch = 0.0f;
        
        return dynamics;
    }
    
    @Override
    public ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int offset) throws DroneApiException {
        return getDroneDynamicsByDroneId(id, 100, offset);
    }
    
    @Override
    public ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id) throws DroneApiException {
        return getDroneDynamicsByDroneId(id, 100, 0);
    }
    
    @Override
    public DroneDynamics getDroneDynamicsByEntryIndex(int id, int index) throws DroneApiException {
        logger.info("Mock: Getting drone dynamics by entry index " + index + " for drone ID " + id);
        
        // Directly find the matching mock drone by ID from our simple mock list
        Optional<DroneDynamics> matchingDrone = mockDroneDynamics.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
        
        if (matchingDrone.isPresent()) {
            logger.info("Found direct mock drone for ID: " + id);
            return matchingDrone.get();
        }
        
        // Fallback to previous method if needed
        List<DroneDynamics> dynamicsList = droneDynamicsMap.getOrDefault(id, new ArrayList<>());
        if (!dynamicsList.isEmpty()) {
            if (index < 0 || index >= dynamicsList.size()) {
                return dynamicsList.get(0); // Return first entry if index is out of bounds
            }
            return dynamicsList.get(index);
        }
        
        // Last resort - create a new dynamic entry on the fly
        logger.warning("No dynamics found for drone ID: " + id + ", creating fallback");
        DroneDynamics fallback = createDefaultDynamics(id);
        
        // For variety, set different statuses and battery levels based on ID
        if (id % 3 == 0) {
            fallback.status = "ON";
            fallback.battery_status = 90;
        } else if (id % 3 == 1) {
            fallback.status = "IS";
            fallback.battery_status = 40;
        } else {
            fallback.status = "OF";
            fallback.battery_status = 5;
        }
        
        return fallback;
    }
    
    // DATA GENERATION METHODS
    
    private List<DroneType> createMockDroneTypes() {
        List<DroneType> types = new ArrayList<>();
        
        // Type 1: Standard delivery drone
        DroneType type1 = new DroneType();
        type1.id = 1;
        type1.manufacturer = "DJI";
        type1.typename = "Mavic Air 2";
        type1.weight = 570;
        type1.max_speed = 68;
        type1.battery_capacity = 3500;
        type1.control_range = 10000;
        type1.max_carriage = 500;
        types.add(type1);
        
        // Type 2: Heavy lift drone
        DroneType type2 = new DroneType();
        type2.id = 2;
        type2.manufacturer = "FreeFly";
        type2.typename = "Alta X";
        type2.weight = 6800;
        type2.max_speed = 55;
        type2.battery_capacity = 5200;
        type2.control_range = 15000;
        type2.max_carriage = 16000;
        types.add(type2);
        
        // Type 3: Survey drone
        DroneType type3 = new DroneType();
        type3.id = 3;
        type3.manufacturer = "Parrot";
        type3.typename = "Anafi USA";
        type3.weight = 515;
        type3.max_speed = 55;
        type3.battery_capacity = 3350;
        type3.control_range = 4000;
        type3.max_carriage = 400;
        types.add(type3);
        
        // Type 4: Racing drone
        DroneType type4 = new DroneType();
        type4.id = 4;
        type4.manufacturer = "ImmersionRC";
        type4.typename = "Vortex 250 Pro";
        type4.weight = 335;
        type4.max_speed = 120;
        type4.battery_capacity = 1800;
        type4.control_range = 2000;
        type4.max_carriage = 100;
        types.add(type4);
        
        return types;
    }
    
    private List<Drone> createMockDrones() {
        List<Drone> drones = new ArrayList<>();
        
        // Create 6 drones with different configurations (instead of 10)
        for (int i = 1; i <= 6; i++) {
            Drone drone = new Drone();
            drone.id = i;
            
            // Assign drone types in a distributed manner
            int typeId = ((i - 1) % 4) + 1;
            drone.dronetype = "http://dronesim.facets-labs.com/api/dronetypes/" + typeId + "/";
            
            drone.serialnumber = "SN-" + String.format("%06d", i * 100);
            drone.created = new Date(System.currentTimeMillis() - (i * 86400000L)); // Different creation dates
            
            // Vary carriage types and weights
            switch ((i - 1) % 3) {
                case 0:
                    drone.carriage_type = "SEN";
                    drone.carriage_weight = 250;
                    break;
                case 1:
                    drone.carriage_type = "ACT";
                    drone.carriage_weight = 350;
                    break;
                case 2:
                    drone.carriage_type = "NONE";
                    drone.carriage_weight = 0;
                    break;
            }
            
            drones.add(drone);
        }
        
        return drones;
    }
    
    private List<DroneDynamics> createMockDroneDynamics() {
        List<DroneDynamics> dynamicsList = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible randomness
        
        logger.info("Creating detailed mock drone dynamics data with flight paths for travel distance calculation");
        
        // ======= DRONE 1: ONLINE with high battery (Mavic Air 2, 3500 mAh) =======
        // Create a significant flight path for travel distance calculations
        double baseLat1 = 50.110924;
        double baseLong1 = 8.682127;
        
        // Generate 20 points along a figure-8 pattern to ensure significant movement
        // First point is oldest, last point is newest
        for (int i = 0; i < 20; i++) {
            DroneDynamics d1History = new DroneDynamics();
            d1History.drone = "http://dronesim.facets-labs.com/api/drones/1/";
            d1History.status = "ON";
            
            // Battery decreases as the drone flies (from 100% down to 95%)
            d1History.battery_status = 3500 - (i * 9);
            
            // Set timestamps with 5-minute intervals (oldest first)
            long minutesAgo = (20 - i) * 5; // 100, 95, 90, ... minutes ago
            d1History.timestamp = new Date(System.currentTimeMillis() - (minutesAgo * 60000L));
            d1History.last_seen = d1History.timestamp;
            
            // Speed varies slightly during flight (40-50 km/h)
            d1History.speed = 40 + random.nextInt(11);
            
            // Create a figure-8 pattern for significant travel distance
            // Each 0.001 in lat/long is roughly 100m, so we're creating a path
            // that spans several kilometers to ensure non-zero travel distance
            double t = i * Math.PI / 10; // Parameter for figure-8 curve
            double latOffset = 0.01 * Math.sin(t);        // ~1km north-south
            double longOffset = 0.015 * Math.sin(2 * t);  // ~1.5km east-west
            
            d1History.latitude = baseLat1 + latOffset;
            d1History.longitude = baseLong1 + longOffset;
            
            dynamicsList.add(d1History);
        }

        // ======= DRONE 2: ONLINE with medium battery (Alta X, 5200 mAh) =======
        double baseLat2 = 50.120924;
        double baseLong2 = 8.692127;
        
        // Create a rectangular flight path (15 points)
        for (int i = 0; i < 15; i++) {
            DroneDynamics d2History = new DroneDynamics();
            d2History.drone = "http://dronesim.facets-labs.com/api/drones/2/";
            d2History.status = "ON";
            
            // Battery at 85% and dropping slightly
            d2History.battery_status = 4420 - (i * 12);
            
            // Timestamps with 6-minute intervals (oldest first)
            long minutesAgo = (15 - i) * 6; // 90, 84, 78, ... minutes ago
            d2History.timestamp = new Date(System.currentTimeMillis() - (minutesAgo * 60000L));
            d2History.last_seen = d2History.timestamp;
            
            // Speed around 45-55 km/h
            d2History.speed = 45 + random.nextInt(11);
            
            // Create a rectangular flight path
            // This will ensure substantial travel distance
            double phase = (double)i / 15;
            double latOffset = 0;
            double longOffset = 0;
            
            if (phase < 0.25) {
                // Moving east
                latOffset = 0;
                longOffset = 0.02 * (phase * 4);
            } else if (phase < 0.5) {
                // Moving north
                latOffset = 0.02 * ((phase - 0.25) * 4);
                longOffset = 0.02;
            } else if (phase < 0.75) {
                // Moving west
                latOffset = 0.02;
                longOffset = 0.02 - 0.02 * ((phase - 0.5) * 4);
            } else {
                // Moving south, back to start
                latOffset = 0.02 - 0.02 * ((phase - 0.75) * 4);
                longOffset = 0;
            }
            
            d2History.latitude = baseLat2 + latOffset;
            d2History.longitude = baseLong2 + longOffset;
            
            dynamicsList.add(d2History);
        }

        // ======= DRONE 3: ONLINE with low battery (Anafi USA, 3350 mAh) =======
        double baseLat3 = 50.130924;
        double baseLong3 = 8.702127;
        
        // Create a one-way flight path (12 points)
        for (int i = 0; i < 12; i++) {
            DroneDynamics d3History = new DroneDynamics();
            d3History.drone = "http://dronesim.facets-labs.com/api/drones/3/";
            d3History.status = "ON";
            
            // Battery at 40% and dropping
            d3History.battery_status = 1340 - (i * 15);
            
            // Timestamps with 4-minute intervals (oldest first)
            long minutesAgo = (12 - i) * 4; // 48, 44, 40, ... minutes ago
            d3History.timestamp = new Date(System.currentTimeMillis() - (minutesAgo * 60000L));
            d3History.last_seen = d3History.timestamp;
            
            // Speed around 35-40 km/h, slowing as battery drops
            d3History.speed = 40 - (i / 4);
            
            // Linear flight path from southwest to northeast
            d3History.latitude = baseLat3 + (0.015 * i / 11);
            d3History.longitude = baseLong3 + (0.02 * i / 11);
            
            dynamicsList.add(d3History);
        }

        // ======= DRONE 4: ISSUE status with medium battery (Vortex 250 Pro, 1800 mAh) =======
        double baseLat4 = 50.140924;
        double baseLong4 = 8.712127;
        
        // Create zigzag pattern showing erratic behavior (10 points)
        for (int i = 0; i < 10; i++) {
            DroneDynamics d4History = new DroneDynamics();
            d4History.drone = "http://dronesim.facets-labs.com/api/drones/4/";
            
            // Status changes from ONLINE to ISSUE
            if (i < 5) {
                d4History.status = "ON";
            } else {
                d4History.status = "IS"; // Issue after 5th point
            }
            
            // Battery at 38% and dropping
            d4History.battery_status = 684 - (i * 8);
            
            // Timestamps with 5-minute intervals (oldest first)
            long minutesAgo = (10 - i) * 5; // 50, 45, 40, ... minutes ago
            d4History.timestamp = new Date(System.currentTimeMillis() - (minutesAgo * 60000L));
            d4History.last_seen = d4History.timestamp;
            
            // Speed drops significantly after issue appears
            if (i < 5) {
                d4History.speed = 30 + random.nextInt(6);
            } else {
                d4History.speed = 15 + random.nextInt(6);
            }
            
            // Zigzag pattern, more erratic after issue appears
            double latOffset;
            double longOffset;
            
            if (i < 5) {
                // Before issue - normal zigzag
                latOffset = 0.002 * i;
                longOffset = 0.003 * Math.sin(i);
            } else {
                // After issue - more erratic movements
                latOffset = 0.002 * i + 0.001 * Math.sin(i * 3);
                longOffset = 0.003 * Math.sin(i) + 0.001 * Math.cos(i * 2);
            }
            
            d4History.latitude = baseLat4 + latOffset;
            d4History.longitude = baseLong4 + longOffset;
            
            dynamicsList.add(d4History);
        }

        // ======= DRONE 5: ONLINE with very low battery (Mavic Air 2, 3500 mAh) =======
        double baseLat5 = 50.150924;
        double baseLong5 = 8.722127;
        
        // Create short flight path showing final moments of battery (8 points)
        for (int i = 0; i < 8; i++) {
            DroneDynamics d5History = new DroneDynamics();
            d5History.drone = "http://dronesim.facets-labs.com/api/drones/5/";
            d5History.status = "ON";
            
            // Battery critically low (4%) and dropping
            d5History.battery_status = 175 - (i * 5);
            if (d5History.battery_status < 0) d5History.battery_status = 0;
            
            // Timestamps with 2-minute intervals (oldest first)
            long minutesAgo = (8 - i) * 3; // 24, 21, 18, ... minutes ago
            d5History.timestamp = new Date(System.currentTimeMillis() - (minutesAgo * 60000L));
            d5History.last_seen = d5History.timestamp;
            
            // Speed decreasing as battery runs out
            d5History.speed = 20 - (i * 2);
            if (d5History.speed < 0) d5History.speed = 0;
            
            // Small, tentative movements as battery runs out
            // Drone is trying to land safely
            d5History.latitude = baseLat5 + 0.005 - (0.005 * i / 7.0);
            d5History.longitude = baseLong5 + 0.003 - (0.003 * i / 7.0);
            
            dynamicsList.add(d5History);
        }

        // ======= DRONE 6: OFFLINE with dead battery (Alta X, 5200 mAh) =======
        double baseLat6 = 50.160924;
        double baseLong6 = 8.732127;
        
        // Create flight path showing gradual shutdown (15 points)
        for (int i = 0; i < 15; i++) {
            DroneDynamics d6History = new DroneDynamics();
            d6History.drone = "http://dronesim.facets-labs.com/api/drones/6/";
            
            // Status changes as battery depletes
            if (i < 7) {
                d6History.status = "ON"; // First half - online
            } else if (i < 12) {
                d6History.status = "IS"; // Middle - issues
            } else {
                d6History.status = "OF"; // End - offline
            }
            
            // Battery depleting to zero
            if (i < 7) {
                d6History.battery_status = 520 - (i * 30); // 10% down to 5%
            } else if (i < 12) {
                d6History.battery_status = 310 - ((i - 7) * 60); // 5% down to 1%
            } else {
                d6History.battery_status = 0; // Dead battery
            }
            
            // Timestamps with 4-minute intervals (oldest first)
            long minutesAgo = (15 - i) * 4; // 60, 56, 52, ... minutes ago
            d6History.timestamp = new Date(System.currentTimeMillis() - (minutesAgo * 60000L));
            
            // Last seen time becomes increasingly delayed as the drone goes offline
            if (i < 12) {
                d6History.last_seen = d6History.timestamp;
            } else {
                // Increasingly large gap between timestamp and last_seen
                long lastSeenDelay = (i - 11) * 60000; // 1-3 minutes delay
                d6History.last_seen = new Date(d6History.timestamp.getTime() - lastSeenDelay);
            }
            
            // Speed declining to zero
            if (i < 7) {
                d6History.speed = 25 - (i * 2); // Normal decline
            } else if (i < 12) {
                d6History.speed = 11 - ((i - 7) * 2); // Faster decline
            } else {
                d6History.speed = 0; // Stopped
            }
            
            // Path showing drone attempting to return home but failing
            double latOffset;
            double longOffset;
            
            if (i < 7) {
                // Moving away from home in an arc
                double angle = (double)i / 7 * Math.PI;
                latOffset = 0.015 * Math.sin(angle);
                longOffset = 0.015 * Math.cos(angle);
            } else if (i < 12) {
                // Trying to return home but following erratic path
                double angle = (double)(i - 7) / 5 * Math.PI;
                double radius = 0.015 * (1 - ((double)(i - 7) / 5));
                latOffset = radius * Math.sin(angle);
                longOffset = radius * Math.cos(angle);
            } else {
                // Stopped, failed to return all the way home
                latOffset = 0.003;
                longOffset = 0.003;
            }
            
            d6History.latitude = baseLat6 + latOffset;
            d6History.longitude = baseLong6 + longOffset;
            
            dynamicsList.add(d6History);
        }
        
        // Log info about our dataset
        logger.info("Created " + dynamicsList.size() + " mock drone dynamics entries with detailed flight paths");
        
        return dynamicsList;
    }
}