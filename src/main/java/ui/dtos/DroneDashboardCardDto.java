package main.java.ui.dtos;

import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.ReverseGeocode.ReverseGeocodeService;

/**
 * The DroneDashboardCardDto class is a DTO that contains information about a drone.
 * It is used to transfer information about a drone to the DroneDashboardCard component.
 */
public class DroneDashboardCardDto {
    private final String typename;
    private final String manufacture;
    private final String status;
    private final int batteryStatus;
    private final int batteryCapacity;
    private final double speed;
    private final double longitude;
    private final double latitude;
    private final String serialNumber;
    private final double travelDistance;
    private final String location;

    /**
     * Creates a new DroneDashboardCardDto with the given information.
     *
     * @param typename        The type name of the drone.
     * @param manufacture     The manufacturer of the drone.
     * @param status          The status of the drone.
     * @param batteryStatus   The current battery status of the drone.
     * @param batteryCapacity The battery capacity of the drone.
     * @param speed           The speed of the drone.
     * @param longitude       The longitude of the drone.
     * @param latitude        The latitude of the drone.
     * @param serialNumber    The serial number of the drone.
     * @param travelDistance  The travel distance of the drone.
     * @param location        The current location of the drone.
     */
    public DroneDashboardCardDto(String typename, String manufacture, String status, int batteryStatus, int batteryCapacity, double speed, double longitude, double latitude, String serialNumber, double travelDistance, String location) {
        this.typename = typename;
        this.manufacture = manufacture;
        this.status = status;
        this.batteryStatus = batteryStatus;
        this.batteryCapacity = batteryCapacity;
        this.speed = speed;
        this.longitude = longitude;
        this.latitude = latitude;
        this.serialNumber = serialNumber;
        this.travelDistance = travelDistance;
        this.location = location;
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

    public double getBatteryPercentage() {
        if (batteryCapacity == 0) {
            return 0;
        }
        return (double) batteryStatus / batteryCapacity * 100;
    }

    public double getTravelDistance() {
        return travelDistance;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "DroneDashboardCardDto{" +
                "typename='" + typename + '\'' +
                ", manufacture='" + manufacture + '\'' +
                ", status='" + status + '\'' +
                ", batteryStatus=" + batteryStatus +
                ", batteryCapacity=" + batteryCapacity +
                ", speed=" + speed +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", serialNumber='" + serialNumber + '\'' +
                ", travelDistance=" + travelDistance +
                ", location='" + location + '\'' +
                '}';
    }
}