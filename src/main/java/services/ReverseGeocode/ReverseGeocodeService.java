package main.java.services.ReverseGeocode;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * A service that provides reverse geocoding functionality.
 */
public class ReverseGeocodeService implements IReverseGeocodeService {
    private static final String API_URL = "https://api-bdc.net/data/reverse-geocode-client";

    /**
     * Get the city and country of a location based on its latitude and longitude.
     *
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @return The city and country of the location.
     */
    @Override
    public String getCityAndCountry(double latitude, double longitude) {
        String urlString = String.format("%s?latitude=%f&longitude=%f&localityLanguage=en", API_URL, latitude, longitude, "en");
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();

                JsonObject json = JsonParser.parseString(inline.toString()).getAsJsonObject();
                String city = json.get("city").getAsString();
                String country = json.get("countryName").getAsString();

                return city + ", " + country;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}