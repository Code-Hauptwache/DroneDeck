package main.java.services.DroneApi.dtos;

import java.util.ArrayList;

/**
 * DTO (Data Transfer Object) for Api Response
 * You probably shouldn't use this in your own code
 */
public class DroneDynamicsResponse {

    /**
     * Number of total results available on the API
     */
    public int count = 0;

    /**
     * Uri for next results
     */
    public String next = "";

    /**
     * Uri for previous results
     */
    public String previous = "";

    /// The Results
    public ArrayList<DroneDynamics> results = new ArrayList<DroneDynamics>();

    /**
     * Get the number of total results available on the API
     * @return the number of total results available on the API
     */
    public int getCount() {
        return count;
    }
}
