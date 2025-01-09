package main.java.ui.dtos;

import main.java.ui.enums.CarriageType;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * DTO for drone-related information.
 * Contains basic fields and light data conversion (e.g. formatting date/time).
 */
public class DroneDto {
    private final int id;
    private final String typeName;
    private final String manufacturer;
    private final String status;
    private final double batteryStatus;
    private final double batteryCapacity;
    private final double speed;
    private final double longitude;
    private final double latitude;
    private final String serialNumber;
    private final double carriageWeight;
    private final CarriageType carriageType;
    private final double maxCarriageWeight;
    private final LocalDateTime lastSeenDateTime;
    private final LocalDateTime dataTimestampDateTime;
    private final double weight;
    private final double maxSpeed;
    private final double controlRange;
    private double travelDistance;
    private String location;
    private boolean isTravelDistanceSet = false;


    // Format for display purposes:
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm a", Locale.ENGLISH);

    /**
     * Main constructor. Note that we parse the ISO date/time strings up front.
     */
    public DroneDto(
            int id,
            String typeName,
            String manufacturer,
            String status,
            int batteryStatus,
            int batteryCapacity,
            double speed,
            double longitude,
            double latitude,
            String serialNumber,
            double carriageWeight,
            String carriageTypeCode,       // The raw string code (SEN, ACT, etc.)
            double maxCarriageWeight,
            String lastSeen,               // ISO date/time string
            double weight,
            double maxSpeed,
            double controlRange,
            String dataTimestamp           // ISO date/time string
    ) {
        this.id = id;
        this.typeName = typeName;
        this.manufacturer = manufacturer;
        this.status = status;
        this.batteryStatus = batteryStatus;
        this.batteryCapacity = batteryCapacity;
        this.speed = speed;
        this.longitude = longitude;
        this.latitude = latitude;
        this.serialNumber = serialNumber;
        this.carriageWeight = carriageWeight;
        // Convert string code to enum
        this.carriageType = CarriageType.fromCode(carriageTypeCode);
        this.maxCarriageWeight = maxCarriageWeight;
        // Parse ISO date/time once
        this.lastSeenDateTime = parseIsoDateTime(lastSeen);
        this.dataTimestampDateTime = parseIsoDateTime(dataTimestamp);
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        this.controlRange = controlRange;
    }

    // Helper method to parse ISO date/time
    private LocalDateTime parseIsoDateTime(String isoDateTime) {
        if (isoDateTime == null) {
            return null;
        }

        try {
            return LocalDateTime.parse(isoDateTime, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException _) {
        }

        try {
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoDateTime, customFormatter);
            return zonedDateTime.toLocalDateTime();
        } catch (DateTimeParseException _) {
        }

        throw new IllegalArgumentException("Invalid date time format: " + isoDateTime);
    }

    // -------------------------
    // Getters (with formatting)
    // -------------------------

    public int getId() { return id; }

    public String getTypeName() {
        return typeName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getStatus() {
        return status;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public double getSpeed() {
        return speed;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public double getCarriageWeight() {
        return carriageWeight;
    }

    public String getCarriageType() {
        // Return the user-friendly name from the enum
        return carriageType.getDisplayName();
    }

    public double getMaxCarriageWeight() {
        return maxCarriageWeight;
    }

    public String getLastSeen() {
        if (lastSeenDateTime == null) {
            return "";
        }
        return lastSeenDateTime.format(DISPLAY_FORMAT);
    }

    public double getWeight() {
        return weight;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getControlRange() {
        return controlRange;
    }

    public String getDataTimestamp() {
        if (dataTimestampDateTime == null) {
            return "";
        }
        return dataTimestampDateTime.format(DISPLAY_FORMAT);
    }

    // Modify the setTravelDistance method
    public void setTravelDistance(double travelDistance) {
        this.travelDistance = travelDistance;
        this.isTravelDistanceSet = true;
    }

    // Add a method to check if travel distance is set
    public boolean isTravelDistanceSet() {
        return isTravelDistanceSet;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the location of the drone as a string (city, country).
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns battery percentage in the range [0..100], or 0 if capacity is 0.
     */
    public double getBatteryPercentage() {
        if (batteryCapacity <= 0) {
            return 0;
        }
        // Ensure we don't exceed 100% in case batteryStatus > batteryCapacity
        return Math.min(100.0, (double) batteryStatus / batteryCapacity * 100);
    }

    /**
     * Placeholder method. Remove if unused or implement properly.
     */
    public double getTravelDistance() {
        return travelDistance;
    }

    /**
     * Placeholder method. Remove if unused or implement properly.
     */
    public Object getAverageSpeed() {
        // TODO: Implement or remove
        return null;
    }
}
