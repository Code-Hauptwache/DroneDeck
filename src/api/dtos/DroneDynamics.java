package api.dtos;

import java.time.LocalDateTime;
import java.util.Date;

public class DroneDynamics {

    public String drone;

    public Date timestamp = new Date();

    public int speed = 0;

    public float align_roll = 0;

    public float align_pitch = 0;

    public double longitude = 0;

    public double latitude = 0;

    public int battery_status = 0;

    public Date last_seen = new Date();

    public String status = "";

    /**
     * Get the Id of the drone from the "drone" url property.
     * @return the Id (0 on Error)
     */
    public int GetId() {
        try {
            return Integer.parseInt(drone.substring(drone.length() - 3, drone.length() - 1));
        } catch (Exception e) {
            //TODO Log Exception to Debugging logger
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
                ", id=" + GetId() +
                ", status='" + status + '\'' +
                '}';
    }
}
