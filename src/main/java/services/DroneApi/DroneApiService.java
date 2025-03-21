package main.java.services.DroneApi;

import com.google.gson.Gson;
import main.java.exceptions.DroneApiException;
import main.java.services.DroneApi.dtos.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DroneApiService implements IDroneApiService {

    private static final Logger logger = Logger.getLogger(DroneApiService.class.getName());

    private String ApiKey;
    private final HttpClient HttpClient; // HttpClient instance, shouldn't be changed after construction

    // Constants for API URLs
    private static final String URL = "http://dronesim.facets-labs.com/api";
    private static final String DRONE_DYNAMICS_URL = "/dronedynamics/";
    private static final String DRONES_URL = "/drones/";
    private static final String DRONE_TYPES_URL = "/dronetypes/";
    private static final String DRONE_DYNAMICS_BY_DRONE_ID = "/dynamics/";

    // Error messages
    private static final String ERROR_OFFSET_LESS_THAN_ZERO = "Offset can't be less than 0";
    private static final String ERROR_LIMIT_LESS_THAN_ONE = "Limit must be greater than 0";
    private static final String ERROR_LIMIT_TOO_BIG = "Limit is too big";

    /**
     * Empty Constructor, mainly used for testing
     */
    public DroneApiService() {
        this.ApiKey = "";
        this.HttpClient = buildHttpClient();
    }

    /**
     * Default Constructor
     * @param apiKey the ApiKey used for Authentication
     */
    public DroneApiService(String apiKey) {
        this.ApiKey = apiKey;
        this.HttpClient = buildHttpClient();
    }

    private HttpClient buildHttpClient() {
        return java.net.http.HttpClient.newBuilder().followRedirects(java.net.http.HttpClient.Redirect.ALWAYS).build();
    }

    /**
     * @return currently used ApiKey
     */
    @Override
    public String getApiKey() {
        return ApiKey;
    }

    /**
     * Set (new) ApiKey
     * @param apiKey the (new) ApiKey to set
     */
    @Override
    public void setApiKey(String apiKey) {
        this.ApiKey = apiKey;
    }

    /**
     * Get a list of DroneDynamics
     * @param limit the number of DroneDynamics to fetch. Must be between 10_000 and 1
     * @param offset the offset (from 0)
     * @return the list of DroneDynamics
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamics(int limit, int offset) throws DroneApiException {
        validateLimitAndOffset(limit, offset);
        return fetchDroneDynamics(URL + DRONE_DYNAMICS_URL + "?limit=" + limit + "&offset=" + offset);
    }

    /**
     * Get 100 DroneDynamics
     * @param offset the offset (from 0)
     * @return 100 DroneDynamics
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamics(int offset) throws DroneApiException {
        return getDroneDynamics(100, offset);
    }

    /**
     * Get 100 DroneDynamics
     * @return 100 DroneDynamics
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamics() throws DroneApiException {
        return getDroneDynamics(100, 0);
    }

    /**
     * Gets a DroneDynamics by id
     * @param id the id to get the DroneDynamics of
     * @return the drone dynamics
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public DroneDynamics getDroneDynamicsById(int id) throws DroneApiException {
        return fetchSingleDroneDynamics(URL + DRONE_DYNAMICS_URL + id);
    }

    /**
     * Get a list of Drones
     * @param limit the number of Drones to get. Must be between 10_000 and 1
     * @param offset the offset (from 0)
     * @return list of Drones
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<Drone> getDrones(int limit, int offset) throws DroneApiException {
        validateLimitAndOffset(limit, offset);
        return fetchDrones(URL + DRONES_URL + "?limit=" + limit + "&offset=" + offset);
    }

    /**
     * Get a list of 100 Drones
     * @param offset the offset (from 0)
     * @return a list of 100 Drones
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<Drone> getDrones(int offset) throws DroneApiException {
        return this.getDrones(100, offset);
    }

    /**
     * Get a list of 100 Drones
     * @return a list of 100 Drones
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<Drone> getDrones() throws DroneApiException {
        return this.getDrones(100, 0);
    }

    /**
     * Gets a drone by its id
     * @param id the id of the drone to get
     * @return the drone
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public Drone getDronesById(int id) throws DroneApiException {
        return fetchSingleDrone(URL + DRONES_URL + id);
    }

    /**
     * Get a list of DroneTypes
     * @param limit the number of Types to get. Must be between 10_000 and 1
     * @param offset the offset (from 0)
     * @return a list of DroneTypes
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<DroneType> getDroneTypes(int limit, int offset) throws DroneApiException {
        validateLimitAndOffset(limit, offset);
        return fetchDroneTypes(URL + DRONE_TYPES_URL + "?limit=" + limit + "&offset=" + offset);
    }

    /**
     * Get a list of 100 DroneTypes
     * @param offset the offset from 0
     * @return A list with 100 DroneTypes
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<DroneType> getDroneTypes(int offset) throws DroneApiException {
        return this.getDroneTypes(100, offset);
    }

    /**
     * Get a list of 100 DroneTypes
     * @return A list with 100 DroneTypes
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<DroneType> getDroneTypes() throws DroneApiException {
        return this.getDroneTypes(100, 0);
    }

    /**
     * Get a DroneType by its id
     * @param id the id of the DroneType to get
     * @return the DroneType
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public DroneType getDroneTypeById(int id) throws DroneApiException {
        return fetchSingleDroneType(URL + DRONE_TYPES_URL + id);
    }

    /**
     * Get 100 DroneDynamics by Drone id
     * @param id the id of the Drone
     * @param offset the offset from 0
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException if an error occurs while fetching the data
     */
    public DroneDynamicsResponse getDroneDynamicsResponseByDroneId(int id, int limit, int offset) throws DroneApiException {
        validateLimitAndOffset(limit, offset);
        return fetchDroneDynamicsResponse(URL + "/" + id + DRONE_DYNAMICS_BY_DRONE_ID + "?limit=" + limit + "&offset=" + offset);
    }

    /**
     * Gets a drone's DroneDynamics
     * @param id the id of the drone of which to retrieve the DroneDynamics
     * @param limit the number of DroneDynamics to get. Must be between 10_000 and 1
     * @param offset the offset from 0
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int limit, int offset) throws DroneApiException {
        validateLimitAndOffset(limit, offset);
        return fetchDroneDynamics(URL + "/" + id + DRONE_DYNAMICS_BY_DRONE_ID + "?limit=" + limit + "&offset=" + offset);
    }

    /**
     * Get 100 DroneDynamics by Drone id
     * @param id the id of the Drone
     * @param offset the offset from 0
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int offset) throws DroneApiException {
        return this.getDroneDynamicsByDroneId(id, 100, offset);
    }

    /**
     * Get 100 DroneDynamics by Drone id
     * @param id the id of the Drone
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException if an error occurs while fetching the data
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id) throws DroneApiException {
        return this.getDroneDynamicsByDroneId(id, 100, 0);
    }

    private void validateLimitAndOffset(int limit, int offset) throws DroneApiException {
        if (offset < 0) {
            throw new DroneApiException(ERROR_OFFSET_LESS_THAN_ZERO);
        }
        if (limit <= 0) {
            throw new DroneApiException(ERROR_LIMIT_LESS_THAN_ONE);
        }
        if (limit > 10_000) {
            throw new DroneApiException(ERROR_LIMIT_TOO_BIG);
        }
    }

    /**
     * Get a DroneDynamics by entry index
     * @param droneId the id of the drone
     * @param entryIndex the index of the entry
     * @return the DroneDynamics
     * @throws DroneApiException if an error occurs while fetching the data
     */
    public DroneDynamics getDroneDynamicsByEntryIndex(int droneId, int entryIndex) throws DroneApiException {
        DroneDynamicsResponse response = getDroneDynamicsResponseByDroneId(droneId, 1, entryIndex);
        return response.results.getFirst();
    }

    private ArrayList<DroneDynamics> fetchDroneDynamics(String url) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), DroneDynamicsResponse.class).results;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get drone dynamics", e);
            throw new DroneApiException("Error getting drone dynamics");
        }
    }

    private DroneDynamicsResponse fetchDroneDynamicsResponse(String url) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), DroneDynamicsResponse.class);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get drone dynamics", e);
            throw new DroneApiException("Error getting drone dynamics");
        }
    }

    private DroneDynamics fetchSingleDroneDynamics(String url) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), DroneDynamics.class);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get drone dynamics", e);
            throw new DroneApiException("Error getting drone dynamics");
        }
    }

    private ArrayList<Drone> fetchDrones(String url) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), DronesResponse.class).results;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get drones", e);
            throw new DroneApiException("Error getting drones");
        }
    }

    private Drone fetchSingleDrone(String url) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), Drone.class);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get drones", e);
            throw new DroneApiException("Error getting drones");
        }
    }

    private ArrayList<DroneType> fetchDroneTypes(String url) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), DroneTypesResponse.class).results;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get drone types", e);
            throw new DroneApiException("Error getting drone types");
        }
    }

    private DroneType fetchSingleDroneType(String url) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), DroneType.class);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get drone types", e);
            throw new DroneApiException("Error getting drone types");
        }
    }
}