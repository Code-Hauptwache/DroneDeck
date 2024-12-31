package main.java.services.ReverseGeocode;

/**
 * A service that provides reverse geocoding functionality.
 */
public interface IReverseGeocodeService {
    String getCityAndCountry(double latitude, double longitude);
}
