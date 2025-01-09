package main.java.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Drone Entity
 */
public class DroneEntity implements Serializable {

    // To fix the serialVersionUID
    @Serial
    private static final long serialVersionUID = 1879467100886065125L;

    /**
     * The ID of the Drone
     */
    private final int id;

    /**
     * Linked Dronetype Entity
     */
    private DroneTypeEntity droneType;

    /// Creation Date of the Drone
    private final Date created;

    /// Serial number of the Drone
    private final String serialNumber;

    /// The carriage weight of the drone
    private final int carriage_weight;

    /// The carriage type of the drone
    private final String carriage_type;

    public DroneEntity(int id, Date created, String serialNumber, int carriage_weight, String carriage_type) {
        this.id = id;
        this.created = created;
        this.serialNumber = serialNumber;
        this.carriage_weight = carriage_weight;
        this.carriage_type = carriage_type;
    }

    /**
     * Drone type Setter for Lazy Initialization
     */
    public void setDroneType(DroneTypeEntity droneTypeEntity) {
        this.droneType = droneTypeEntity;
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

    return (serialNumber != null && pattern.matcher(this.serialNumber).find())
            || (droneType != null && ((droneType.typename != null && pattern.matcher(this.droneType.typename).find())
            || (droneType.manufacturer != null && pattern.matcher(this.droneType.manufacturer).find())));
}

    /**
     * ToString Override method for Test
     * @return All DroneEntity's field value String
     */
    @Override
    public String toString() {
        return "DroneEntity{" +
                "id=" + id +
                ", drone_type=" + droneType +
                ", created=" + created +
                ", serial number='" + serialNumber + '\'' +
                ", carriage_weight=" + carriage_weight +
                ", carriage_type='" + carriage_type + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public DroneTypeEntity getDroneType() {
        return droneType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getCarriage_weight() {
        return carriage_weight;
    }

    public String getCarriage_type() {
        return carriage_type;
    }
}
