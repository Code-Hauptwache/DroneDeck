package main.java.services.DroneApi.dtos;

import java.util.Date;
import java.util.logging.Logger;

/**
 * DroneDynamic Data Transfer Object
 * DroneDynamics are Datapoint's wich are created periodically and
 * display the "current" information about the Drone.
 */
public class DroneDynamics {

    private static final Logger logger = Logger.getLogger(DroneDynamics.class.getName());

    /**
     * Url pointing to the drone this DroneDynamic is owned by
     */
    public String drone;

    ///Timestamp of the Creating of this DroneDynamic
    public Date timestamp = new Date();

    /// Speed of the Drone
    public int speed = 0;

    /// Role of the Drone
    public float align_roll = 0;

    /// Pitch of the Drone
    public float align_pitch = 0;

    /// Longitude of the Drone
    public double longitude = 0;

    /// Latitude of the Drone
    public double latitude = 0;

    /// Battery Status of the Drone
    public int battery_status = 0;

    /// When the Drone was Last Seen
    public Date last_seen = new Date();

    /// The Status of the Drone
    public String status = "";

    /**
     * Get the Id of the drone from the "drone" url property.
     * @return the Id (0 on Error)
     */
    public int getId() {
        try {
            return Integer.parseInt(drone.replaceAll("\\D+", ""));
        } catch (Exception e) {
            logger.warning("Failed to parse DroneDynamic Id");
            return 0;
        }
    }

    /**
     * Just Prints a pretty representation
     * @return Textual representation of the Data
     */
    @Override
    public String toString() {
        return "DroneDynamics{" +
                "drone='" + drone + '\'' +
                ", timestamp=" + timestamp +
                ", speed=" + speed +
                ", align_roll=" + align_roll +
                ", align_pitch=" + align_pitch +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", battery_status=" + battery_status +
                ", last_seen=" + last_seen +
                ", id=" + getId() +
                ", status='" + status + '\'' +
                '}';
    }
}
