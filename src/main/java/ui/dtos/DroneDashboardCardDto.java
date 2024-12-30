package main.java.ui.dtos;

import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.ReverseGeocode.ReverseGeocodeService;

/**
 * The DroneDashboardCardDto class is a DTO that contains information about a drone.
 * It is used to transfer information about a drone to the DroneDashboardCard component.
 */
public class DroneDashboardCardDto {
    private String typename;
    private String manufacture;
    private String status;
    private int batteryStatus;
    private int batteryCapacity;
    private double speed;
    private double longitude;
    private double latitude;
    private String serialNumber;

    /**
     * Creates a new DroneDashboardCardDto with the given information.
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
     */
    public DroneDashboardCardDto(String typename, String manufacture, String status, int batteryStatus, int batteryCapacity, double speed, double longitude, double latitude, String serialNumber) {
        this.typename = typename;
        this.manufacture = manufacture;
        this.status = status;
        this.batteryStatus = batteryStatus;
        this.batteryCapacity = batteryCapacity;
        this.speed = speed;
        this.longitude = longitude;
        this.latitude = latitude;
        this.serialNumber = serialNumber;
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

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public double getSpeed() {
        return speed;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
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

    public Object getTravelDistance() {
        return null; // TODO: Implement this
    }

    public String getLocation() {
        IReverseGeocodeService reverseGeocodeService = new ReverseGeocodeService();
        return reverseGeocodeService.getCityAndCountry(latitude, longitude);
    }
}