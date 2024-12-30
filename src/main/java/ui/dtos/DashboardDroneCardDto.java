package main.java.ui.dtos;

import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.ReverseGeocode.ReverseGeocodeService;

/**
 * The DashboardDroneCardDto class is a DTO that contains information about a drone.
 * It is used to transfer information about a drone to the DroneDashboardCard component.
 */
public class DashboardDroneCardDto {
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
     * Creates a new DashboardDroneCardDto with the given information.
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
    public DashboardDroneCardDto(String typename, String manufacture, String status, int batteryStatus, int batteryCapacity, double speed, double longitude, double latitude, String serialNumber) {
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

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(int batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getBatteryPercentage() {
        if (batteryCapacity == 0) {
            return 0;
        }
        return (double) batteryStatus / batteryCapacity * 100;
    }

    public Object getTravelDistance() {
        IReverseGeocodeService reverseGeocodeService = new ReverseGeocodeService();
        return reverseGeocodeService.getCityAndCountry(latitude, longitude);
    }

    public String getLocation() {
        return ""; // TODO: Get actual location
    }
}