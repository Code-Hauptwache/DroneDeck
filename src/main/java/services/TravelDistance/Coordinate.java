package main.java.services.TravelDistance;

/**
 * Coordinate data with longitude and latitude
 */
public class Coordinate {

    private double longitude;
    private double latitude;

    public Coordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Getter method of longitude
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Getter method of latitude
     * @return longitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Static method for calculating distance of two coordinate
     * @param coordinate1
     * @param coordinate2
     * @return
     */
    public static double distance(Coordinate coordinate1, Coordinate coordinate2) {
        double lat1 = coordinate1.getLatitude();
        double lon1 = coordinate1.getLongitude();
        double lat2 = coordinate2.getLatitude();
        double lon2 = coordinate2.getLongitude();

        final int R = 6371; // Radius of the Earth in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distance in kilometers
    }
}
