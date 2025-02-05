package main.java.services.DroneApi.dtos;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
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

    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm a", Locale.ENGLISH);

    /**
     * Get the ID of the drone from the "drone" url property.
     * @return the ID (0 on Error)
     */
    public int getId() {
        try {
            return Integer.parseInt(drone.replaceAll("\\D+", ""));
        } catch (Exception e) {
            logger.warning("Failed to parse DroneDynamic Id");
            return 0;
        }
    }

    private LocalDateTime parseIsoDateTime(Date date) {
        if (date == null) {
            return null;
        }

        try {
            return LocalDateTime.parse(date.toString(), DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            logger.log(Level.FINE, "Failed to parse ISO date/time format, trying custom format: " + date);
        }

        try {
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(date.toString(), customFormatter);
            return zonedDateTime.toLocalDateTime();
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Could not parse date/time in any supported format: " + date);
        }

        throw new IllegalArgumentException("Invalid date time format: " + date);
    }

    /**
     * Get the Last Seen Date in a human-readable format
     * @return the Last Seen Date
     */
    public String getLastSeen() {
        LocalDateTime lastSeenDateTime = parseIsoDateTime(last_seen);
        if (lastSeenDateTime == null) {
            return "";
        }
        return lastSeenDateTime.format(DISPLAY_FORMAT);
    }

    /**
     * Get the Data Timestamp in a human-readable format
     * @return the Data Timestamp
     */
    public String getDataTimestamp() {
        LocalDateTime dataTimestampDateTime = parseIsoDateTime(timestamp);
        if (dataTimestampDateTime == null) {
            return "";
        }
        return dataTimestampDateTime.format(DISPLAY_FORMAT);
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
