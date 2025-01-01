package main.java.ui.dtos;

import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.ReverseGeocode.ReverseGeocodeService;

import java.util.List;

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
    private List<Coordinate> historicalFlightDataList;

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

    public void updateHistoricalFlightDataList(List<Coordinate> historicalFlightDataList) {
        this.historicalFlightDataList = historicalFlightDataList;
    }

    public Object getTravelDistance() {
        return null; // TODO: Implement this
    }

    public String getLocation() {
        IReverseGeocodeService reverseGeocodeService = new ReverseGeocodeService();
        return reverseGeocodeService.getCityAndCountry(latitude, longitude);
    }
}