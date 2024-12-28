package main.java.services.DroneApi.DTOs;

import java.util.ArrayList;

/**
 * DTO (Data Transfer Object) for Api Response
 * You probably shouldn't use this in your own code
 */
public class DronesResponse {

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
    public ArrayList<Drone> results = new ArrayList<>();

}
