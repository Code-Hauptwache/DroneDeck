package main.java.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Drone Data Transfer Object
 */
public class DroneEntity implements Serializable {

    /**
     * The Id of the Drone
     */
    private int id = 0;

    /**
     * This shouldn't be used in the Applikation directly.
     * This is an Url to the DroneType assigned to the Drone.
     */
    private DroneTypeEntity dronetype;

    /// Creation Date of the Drone
    private Date created = new Date();

    /// Serial number of the Drone
    private String serialnumber = "";

    /// The carriage weight of the drone
    private int carriage_weight = 0;

    /// The carriage type of the drone
    private String carriage_type = "";

    public DroneEntity(int id, Date created, String serialnumber, int carriage_weight, String carriage_type) {
        this.id = id;
        this.created = created;
        this.serialnumber = serialnumber;
        this.carriage_weight = carriage_weight;
        this.carriage_type = carriage_type;
    }

    public void setDronetype(DroneTypeEntity dronetype) {
        this.dronetype = dronetype;
    }

    public boolean checkIfKeywordMatches(String keyword) {
        return Integer.toString(this.id).contains(keyword)
                || this.serialnumber.contains(keyword)
                || this.dronetype.typename.contains(keyword);
    }

    @Override
    public String toString() {
        return "DroneEntity{" +
                "id=" + id +
                ", dronetype=" + dronetype +
                ", created=" + created +
                ", serialnumber='" + serialnumber + '\'' +
                ", carriage_weight=" + carriage_weight +
                ", carriage_type='" + carriage_type + '\'' +
                '}';
    }
}
