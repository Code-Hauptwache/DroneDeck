package main.java.api;

import main.java.api.dtos.*;
import com.google.gson.Gson;
import main.java.api.exceptions.DroneApiException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


public class DroneApiService implements IDroneApiService {

    private String ApiKey;

    //HttpClient. This shouldn't be changed after Construction
    private final HttpClient HttpClient;

    //some constants for easy editing of "magic" urls
    private static final String Url = "http://dronesim.facets-labs.com/api";

    private static final String DroneDynamicsUrl = "/dronedynamics/";

    private static final String DronesUrl = "/drones/";

    private static final String DroneTypesUrl = "/dronetypes/";

    private static final String DroneDynamicsByDroneId = "/dynamics/";

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
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamics(int limit, int offset) throws DroneApiException {

        if (offset < 0) {
            throw new DroneApiException("Offset can't be less than 0");
        }

        if (limit <= 0) {
            throw new DroneApiException("Limit must be greater than 0");
        }

        if (limit > 10_000) {
            throw new DroneApiException("Limit is too big");
        }

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(Url + DroneDynamicsUrl + "?limit=" + limit + "&offset=" + offset))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            return gson.fromJson(response.body(), DroneDynamicsResponse.class).results;

        } catch (Exception e) {
            throw new DroneApiException("Error getting drone dynamics: " + e.getMessage());
        }
    }

    /**
     * Get 100 DroneDynamics
     * @param offset the offset (from 0)
     * @return 100 DroneDynamics
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamics(int offset) throws DroneApiException {
        return getDroneDynamics(100, offset);
    }

    /**
     * Get 100 DroneDynamics
     * @return 100 DroneDynamics
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamics() throws DroneApiException {
        return getDroneDynamics(100, 0);
    }

    /**
     * Gets a DroneDynamics by id
     * @param id the id to get the DroneDynamics of
     * @return the drone dynamics
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public DroneDynamics getDroneDynamicsById(int id) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(Url + DroneDynamicsUrl + id))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            return gson.fromJson(response.body(), DroneDynamics.class);

        } catch (Exception e) {
            throw new DroneApiException("Error getting drone dynamics: " + e.getMessage());
        }
    }

    /**
     * Get a list of Drones
     * @param limit the number of Drones to get. Must be between 10_000 and 1
     * @param offset the offset (from 0)
     * @return list of Drones
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<Drone> getDrones(int limit, int offset) throws DroneApiException {

        if (offset < 0) {
            throw new DroneApiException("Offset can't be less than 0");
        }

        if (limit <= 0) {
            throw new DroneApiException("Limit can't be less than 0");
        }

        if (limit > 10_000) {
            throw new DroneApiException("Limit is too big");
        }

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(Url + DronesUrl + "?limit=" + limit + "&offset=" + offset))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            return gson.fromJson(response.body(), DronesResponse.class).results;

        } catch (Exception e) {
            throw new DroneApiException("Error getting drones: " + e.getMessage());
        }
    }

    /**
     * Get a list of 100 Drones
     * @param offset the offset (from 0)
     * @return a list of 100 Drones
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<Drone> getDrones(int offset) throws DroneApiException {
        return this.getDrones(100, offset);
    }

    /**
     * Get a list of 100 Drones
     * @return a list of 100 Drones
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<Drone> getDrones() throws DroneApiException {
        return this.getDrones(100, 0);
    }

    /**
     * Gets a drone by its id
     * @param id the id of the drone to get
     * @return the drone
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public Drone getDronesById(int id) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(Url + DronesUrl + id))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            return gson.fromJson(response.body(), Drone.class);

        } catch (Exception e) {
            throw new DroneApiException("Error getting drones: " + e.getMessage());
        }
    }

    /**
     * Get a list of DroneTypes
     * @param limit the number of Types to get. Must be between 10_000 and 1
     * @param offset the offset (from 0)
     * @return a list of DroneTypes
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<DroneType> getDroneTypes(int limit, int offset) throws DroneApiException {

        if (offset < 0) {
            throw new DroneApiException("Offset can't be less than 0");
        }

        if (limit <= 0) {
            throw new DroneApiException("Limit can't be less than 0");
        }

        if (limit > 10_000) {
            throw new DroneApiException("Limit is too big");
        }

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(Url + DroneTypesUrl + "?limit=" + limit + "&offset=" + offset))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            return gson.fromJson(response.body(), DroneTypesResponse.class).results;

        } catch (Exception e) {
            throw new DroneApiException("Error getting drone types: " + e.getMessage());
        }
    }

    /**
     * Get a list of 100 DroneTypes
     * @param offset the offset from 0
     * @return A list with 100 DroneTypes
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<DroneType> getDroneTypes(int offset) throws DroneApiException {
        return this.getDroneTypes(100, offset);
    }

    /**
     * Get a list of 100 DroneTypes
     * @return A list with 100 DroneTypes
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<DroneType> getDroneTypes() throws DroneApiException {
        return this.getDroneTypes(100, 0);
    }

    /**
     * Get a DroneType by its id
     * @param id the id of the DroneType to get
     * @return the DroneType
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public DroneType getDroneTypeById(int id) throws DroneApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(Url + DroneTypesUrl + id))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            return gson.fromJson(response.body(), DroneType.class);

        } catch (Exception e) {
            throw new DroneApiException("Error getting drone types: " + e.getMessage());
        }
    }

    /**
     * Gets a drones DroneDynamics
     * @param id the id of the drone of which to retrieve the DroneDynamics
     * @param limit the number of DroneDynamics to get. Must be between 10_000 and 1
     * @param offset the offset from 0
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int limit, int offset) throws DroneApiException {

        if (offset < 0) {
            throw new DroneApiException("Offset can't be less than 0");
        }

        if (limit <= 0) {
            throw new DroneApiException("Limit can't be less than 0");
        }

        if (limit > 10_000) {
            throw new DroneApiException("Limit is too big");
        }

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(Url + "/" + id + DroneDynamicsByDroneId + "?limit=" + limit + "&offset=" + offset))
                    .GET()
                    .header("Authorization", "Token " + this.ApiKey)
                    .build();

            HttpResponse<String> response = this.HttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            return gson.fromJson(response.body(), DroneDynamicsResponse.class).results;

        } catch (Exception e) {
            throw new DroneApiException("Error getting drone types: " + e.getMessage());
        }
    }

    /**
     * Get 100 DroneDynamics by Drone id
     * @param id the id of the Drone
     * @param offset the offset from 0
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id, int offset) throws DroneApiException {
        return this.getDroneDynamicsByDroneId(id, 100, offset);
    }

    /**
     * Get 100 DroneDynamics by Drone id
     * @param id the id of the Drone
     * @return the DroneDynamics of said Drone
     * @throws DroneApiException Api Exceptions
     */
    @Override
    public ArrayList<DroneDynamics> getDroneDynamicsByDroneId(int id) throws DroneApiException {
        return this.getDroneDynamicsByDroneId(id, 100, 0);
    }
}
