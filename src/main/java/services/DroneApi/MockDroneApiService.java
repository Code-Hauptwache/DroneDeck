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
        dynamics.status = "ONLINE"; // Set to ONLINE by default
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
            fallback.status = "ONLINE";
            fallback.battery_status = 90;
        } else if (id % 3 == 1) {
            fallback.status = "ISSUE";
            fallback.battery_status = 40;
        } else {
            fallback.status = "OFFLINE";
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
        
        // Create 10 drones with different configurations
        for (int i = 1; i <= 10; i++) {
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
        
        // Create a very simple mix exactly as requested:
        // - Some high battery and ONLINE
        // - Some medium battery and ONLINE
        // - Some low battery but still ONLINE
        // - Some 0% battery and OFFLINE
        
        // Skip all history - just create ONE current entry per drone
        
        // We'll make exactly 10 drones with the following properties:
        // Drone 1 is Type 1 (Mavic Air 2) with 3500 mAh capacity
        DroneDynamics d1 = new DroneDynamics();
        d1.drone = "http://dronesim.facets-labs.com/api/drones/1/";
        d1.status = "ON";
        d1.battery_status = 3325; // 95% of 3500 mAh
        d1.timestamp = new Date();
        d1.last_seen = new Date();
        d1.speed = 45;
        d1.latitude = 50.110924;
        d1.longitude = 8.682127;
        dynamicsList.add(d1);
        
        // Drone 2 is Type 2 (Alta X) with 5200 mAh capacity
        DroneDynamics d2 = new DroneDynamics();
        d2.drone = "http://dronesim.facets-labs.com/api/drones/2/";
        d2.status = "ON";
        d2.battery_status = 4420; // 85% of 5200 mAh
        d2.timestamp = new Date();
        d2.last_seen = new Date();
        d2.speed = 50;
        d2.latitude = 50.120924;
        d2.longitude = 8.692127;
        dynamicsList.add(d2);
        
        // Drone 3 is Type 3 (Anafi USA) with 3350 mAh capacity
        DroneDynamics d3 = new DroneDynamics();
        d3.drone = "http://dronesim.facets-labs.com/api/drones/3/";
        d3.status = "ON";
        d3.battery_status = 1340; // 40% of 3350 mAh
        d3.timestamp = new Date();
        d3.last_seen = new Date();
        d3.speed = 35;
        d3.latitude = 50.130924;
        d3.longitude = 8.702127;
        dynamicsList.add(d3);
        
        // Drone 4 is Type 4 (Vortex 250 Pro) with 1800 mAh capacity
        DroneDynamics d4 = new DroneDynamics();
        d4.drone = "http://dronesim.facets-labs.com/api/drones/4/";
        d4.status = "ON";
        d4.battery_status = 684; // 38% of 1800 mAh
        d4.timestamp = new Date();
        d4.last_seen = new Date();
        d4.speed = 30;
        d4.latitude = 50.140924;
        d4.longitude = 8.712127;
        dynamicsList.add(d4);
        
        // Drone 5 is Type 1 (Mavic Air 2) with 3500 mAh capacity
        DroneDynamics d5 = new DroneDynamics();
        d5.drone = "http://dronesim.facets-labs.com/api/drones/5/";
        d5.status = "ON";
        d5.battery_status = 140; // 4% of 3500 mAh - very low but still online
        d5.timestamp = new Date();
        d5.last_seen = new Date();
        d5.speed = 20;
        d5.latitude = 50.150924;
        d5.longitude = 8.722127;
        dynamicsList.add(d5);
        
        // Drone 6 is Type 2 (Alta X) with 5200 mAh capacity
        DroneDynamics d6 = new DroneDynamics();
        d6.drone = "http://dronesim.facets-labs.com/api/drones/6/";
        d6.status = "ON";
        d6.battery_status = 156; // 3% of 5200 mAh - very low but still online
        d6.timestamp = new Date();
        d6.last_seen = new Date();
        d6.speed = 15;
        d6.latitude = 50.160924;
        d6.longitude = 8.732127;
        dynamicsList.add(d6);
        
        // Drone 7 is Type 3 (Anafi USA) with 3350 mAh capacity
        DroneDynamics d7 = new DroneDynamics();
        d7.drone = "http://dronesim.facets-labs.com/api/drones/7/";
        d7.status = "IS";
        d7.battery_status = 1005; // 30% of 3350 mAh - medium with issue
        d7.timestamp = new Date();
        d7.last_seen = new Date();
        d7.speed = 25;
        d7.latitude = 50.170924;
        d7.longitude = 8.742127;
        dynamicsList.add(d7);
        
        // Drone 8 is Type 4 (Vortex 250 Pro) with 1800 mAh capacity
        DroneDynamics d8 = new DroneDynamics();
        d8.drone = "http://dronesim.facets-labs.com/api/drones/8/";
        d8.status = "OF";
        d8.battery_status = 0; // Dead battery, offline (0 mAh left)
        d8.timestamp = new Date();
        d8.last_seen = new Date(System.currentTimeMillis() - 3600000); // 1 hour ago
        d8.speed = 0;
        d8.latitude = 50.180924;
        d8.longitude = 8.752127;
        dynamicsList.add(d8);
        
        // Drone 9 is Type 1 (Mavic Air 2) with 3500 mAh capacity
        DroneDynamics d9 = new DroneDynamics();
        d9.drone = "http://dronesim.facets-labs.com/api/drones/9/";
        d9.status = "OF";
        d9.battery_status = 0; // Dead battery, offline (0 mAh left)
        d9.timestamp = new Date();
        d9.last_seen = new Date(System.currentTimeMillis() - 7200000); // 2 hours ago
        d9.speed = 0;
        d9.latitude = 50.190924;
        d9.longitude = 8.762127;
        dynamicsList.add(d9);
        
        // Drone 10 is Type 2 (Alta X) with 5200 mAh capacity
        DroneDynamics d10 = new DroneDynamics();
        d10.drone = "http://dronesim.facets-labs.com/api/drones/10/";
        d10.status = "IS";
        d10.battery_status = 780; // 15% of 5200 mAh - low with issue
        d10.timestamp = new Date();
        d10.last_seen = new Date();
        d10.speed = 10;
        d10.latitude = 50.200924;
        d10.longitude = 8.772127;
        dynamicsList.add(d10);
        
        logger.info("Created exactly 10 mock drone dynamics entries with varied statuses and battery levels");
        
        return dynamicsList;
    }
}