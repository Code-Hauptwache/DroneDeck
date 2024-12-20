package main.java.api.dtos;

import java.util.ArrayList;

/**
 * DTO (Data Transfer Object) for Api Response
 * You probably shouldn't use this in your own code
 */
public class DroneDynamicsResponse {

    /**
     * Number of results
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

    public ArrayList<DroneDynamics> results = new ArrayList<DroneDynamics>();

}
