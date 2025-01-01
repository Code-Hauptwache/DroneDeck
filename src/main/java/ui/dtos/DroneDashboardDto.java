package main.java.ui.dtos;

import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.ReverseGeocode.ReverseGeocodeService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The DroneDashboardDto class is a DTO that contains information about a drone.
 * It is used to transfer information about a drone to the DroneDashboardCard component.
 */
public class DroneDashboardDto {
    private final String typename;
    private final String manufacture;
    private final String status;
    private final int batteryStatus;
    private final int batteryCapacity;
    private final double speed;
    private final double longitude;
    private final double latitude;
    private final String serialNumber;
    private final double carriageWeight;
    private final String carriageType;
    private final double maxCarriageWeight;
    private final String lastSeen;
    private final double weight;
    private final double maxSpeed;
    private final double controlRange;
    private final String dataTimestamp;

    /**
     * Creates a new DroneDashboardDto with the given information.
     *
     * @param typename        The type name of the drone.
     * @param manufacture      The manufacturer of the drone.
     * @param status           The status of the drone.
     * @param batteryStatus    The current battery status of the drone.
     * @param batteryCapacity  The battery capacity of the drone.
     * @param speed            The speed of the drone.
     * @param longitude        The longitude of the drone.
     * @param latitude         The latitude of the drone.
     * @param serialNumber     The serial number of the drone.
     * @param CarriageWeight   The carriage weight of the drone.
     * @param CarriageType     The carriage type of the drone.
     * @param MaxCarriageWeight The maximum carriage weight of the drone.
     * @param LastSeen         The last seen time of the drone.
     * @param Weight           The weight of the drone.
     * @param MaxSpeed         The maximum speed of the drone.
     * @param ControlRange     The control range of the drone.
     */
    public DroneDashboardDto(String typename, String manufacture, String status, int batteryStatus, int batteryCapacity, double speed, double longitude, double latitude, String serialNumber, double CarriageWeight, String CarriageType, double MaxCarriageWeight, String LastSeen, double Weight, double MaxSpeed, double ControlRange, String dataTimestamp) {
        this.typename = typename;
        this.manufacture = manufacture;
        this.status = status;
        this.batteryStatus = batteryStatus;
        this.batteryCapacity = batteryCapacity;
        this.speed = speed;
        this.longitude = longitude;
        this.latitude = latitude;
        this.serialNumber = serialNumber;
        this.carriageWeight = CarriageWeight;
        this.carriageType = CarriageType;
        this.maxCarriageWeight = MaxCarriageWeight;
        this.lastSeen = LastSeen;
        this.weight = Weight;
        this.maxSpeed = MaxSpeed;
        this.controlRange = ControlRange;
        this.dataTimestamp = dataTimestamp;
    }

    public String getTypename() {
        return typename;
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getStatus() {
        return status;
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
        return carriageType;
    }

    public double getMaxCarriageWeight() {
        return maxCarriageWeight;
    }

    public String getLastSeen() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm a", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(lastSeen, DateTimeFormatter.ISO_DATE_TIME);
        return dateTime.format(formatter);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm a", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(dataTimestamp, DateTimeFormatter.ISO_DATE_TIME);
        return dateTime.format(formatter);
    }

    public double getBatteryPercentage() {
        if (batteryCapacity == 0) {
            return 0;
        }
        return (double) batteryStatus / batteryCapacity * 100;
    }

    public Object getTravelDistance() {
        return null; // TODO: Implement this
    }

    public String getLocation() {
        IReverseGeocodeService reverseGeocodeService = new ReverseGeocodeService();
        return reverseGeocodeService.getCityAndCountry(latitude, longitude);
    }
}