package main.java.services.DroneApi.dtos;

import main.java.entity.DroneEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Drone Data Transfer Object
 */
public class Drone {

    /**
     * The Id of the Drone
     */
    public int id = 0;

    /**
     * This shouldn't be used in the Applikation directly.
     * This is an Url to the DroneType assigned to the Drone.
     */
    public String dronetype = "";

    /// Creation Date of the Drone
    public Date created = new Date();

    /// Serial number of the Drone
    public String serialnumber = "";

    /// The carriage weight of the drone
    public int carriage_weight = 0;

    /// The carriage type of the drone
    public String carriage_type = "";

    /**
     * This gets the Id of the DroneType assigned to this Drone.
     * This then can be used to get the DroneType from the DroneApiService
     * @return the DroneType Id
     */
    public int getDronetypeId() {
        try {
            return Integer.parseInt(dronetype.substring(dronetype.length() - 3, dronetype.length() - 1));
        } catch (Exception ex) {
            //TODO Logging
            return 0;
        }

    }

    /**
     * "Json" representation of this drone.
     * This isn't always a valid Json representation.
     * @return Humanly readable "Json"ish representation of the Drone data
     */
    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", dronetypeId=" + getDronetypeId() +
                ", dronetype='" + dronetype + '\'' +
                ", created=" + created +
                ", serialnumber='" + serialnumber + '\'' +
                ", carriage_weight=" + carriage_weight +
                ", carriage_type='" + carriage_type + '\'' +
                '}';
    }

    /**
     * This method makes Dto to Entity
     * @return new DroneEntity
     */
    public DroneEntity toEntity() {
        return new DroneEntity(id, created, serialnumber, carriage_weight, carriage_type);
    }
}
