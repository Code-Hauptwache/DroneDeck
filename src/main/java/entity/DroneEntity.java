package main.java.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Drone Entity
 */
public class DroneEntity implements Serializable {

    /**
     * The Id of the Drone
     */
    private int id = 0;

    /**
     * Linked Dronetype Entity
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

    /**
     * Dronetype Setter for Lazy Initialization
     * @param dronetype
     */
    public void setDronetype(DroneTypeEntity dronetype) {
        this.dronetype = dronetype;
    }

    /**
     * check if this drone entity matches with keyword
     * Especially for drone's id, serial number and type name.
     * @param keyword for searching
     * @return boolean value that this drone matches with condition
     */
    public boolean checkIfKeywordMatches(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return true; // If empty value comes in, all the drones return.
        }

        Pattern pattern = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE);

        return pattern.matcher(this.serialnumber).find()
                || pattern.matcher(this.dronetype.typename).find()
                || pattern.matcher(this.dronetype.manufacturer).find();
    }

    /**
     * ToString Override method for Test
     * @return All DroneEntity's field value String
     */
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
