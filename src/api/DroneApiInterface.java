package api;

import api.dtos.DroneApiException;
import api.dtos.DroneDynamics;
import api.dtos.DroneDynamicsResponse;
import com.google.gson.Gson;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


public class DroneApiInterface {

    private String ApiKey;

    //Dies sollte natürlich nach dem Erstellen nicht gearendert werden
    private final HttpClient HttpClient;

    //Ab hier Konstanten für die Verbindung mit der Rest Api.
    //Einfach, sodass es später einfacher ist diese eventuell auszutauschen.
    private static final String Url = "http://dronesim.facets-labs.com/api";

    private static final String DroneDynamicsUrl = "/dronedynamics/";

    /**
     * Empty Constructor, mainly used for testing
     */
    public DroneApiInterface() throws UncheckedIOException {
        this.ApiKey = "";
        this.HttpClient = java.net.http.HttpClient.newHttpClient();
    }

    /**
     * Default Constructor
     * @param apiKey the ApiKey used for Authentication
     */
    public DroneApiInterface(String apiKey) {
        this.ApiKey = apiKey;
        this.HttpClient = java.net.http.HttpClient.newHttpClient();
    }

    /**
     * @return currently used ApiKey
     */
    public String getApiKey() {
        return ApiKey;
    }

    /**
     * Set (new) ApiKey
     * @param apiKey the (new) ApiKey to set
     */
    public void setApiKey(String apiKey) {
        this.ApiKey = apiKey;
    }

    /**
     * Get a list of DroneDynamics
     * @param limit the number of DroneDynamics to fetch. Must be >= 1
     * @param offset the offset (from 0)
     * @return the list of DroneDynamics
     * @throws DroneApiException Strange Custom Exception XD
     */
    public ArrayList<DroneDynamics> getDroneDynamics(int limit, int offset) throws DroneApiException {

        if (limit <= 0) {
            limit = 1;
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
            throw new DroneApiException();
        }
    }

    /**
     * Get 100 DroneDynamics
     * @param offset the offset (from 0)
     * @return 100 DroneDynamics
     * @throws DroneApiException Magic Exception :D
     */
    public ArrayList<DroneDynamics> getDroneDynamics(int offset) throws DroneApiException {
        return getDroneDynamics(100, offset);
    }

    /**
     * Get 100 DroneDynamics
     * @return 100 DroneDynamics
     * @throws DroneApiException Magic Exception :)
     */
    public ArrayList<DroneDynamics> getDroneDynamics() throws DroneApiException {
        return getDroneDynamics(100, 0);
    }
}
