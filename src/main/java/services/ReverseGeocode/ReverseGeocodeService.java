package main.java.services.ReverseGeocode;

import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Locale;
import java.util.Scanner;

/**
 * A service that provides reverse geocoding functionality.
 */
public class ReverseGeocodeService implements IReverseGeocodeService {
    // Primary API
    private static final String API_URL = "https://api-bdc.net/data/reverse-geocode";
    private static final String I_4_MJ_UX = "YmRjX2ExOTg3ZmQ4MDFlZjQ3YmQ4Mzc2YzE4NDc1NzI4MjUx";

    // Fallback API
    private static final String API_URL_FALLBACK = "https://us1.locationiq.com/v1/reverse";
    private static final String Y_RL_YJ_I = "cGsuZjAzZjIxMDIwMWYzMzExMDE1OTkwNjUzOWQ2YTRlYjI=";

    // Rate limiters for the APIs
    private final RateLimiter bdcRateLimiter = RateLimiter.create(15.0);
    private final RateLimiter locationIqRateLimiter = RateLimiter.create(1.0);

    /**
     * Get the city and country of a location based on its latitude and longitude.
     *
     * @param latitude  The latitude of the location.
     * @param longitude The longitude of the location.
     * @return The city and country of the location, or null if it could not be determined.
     */
    @Override
    public String getCityAndCountry(double latitude, double longitude) {
        // 1) Try the primary API (BDC)
        System.out.println("Attempting to get city and country from BDC API...");
        String cityCountry = getCityAndCountryFromBDC(latitude, longitude);
        if (cityCountry != null && !cityCountry.isEmpty()) {
            System.out.println("Successfully retrieved city and country from BDC API.");
            return cityCountry;
        }

        // 2) Fallback to LocationIQ if primary fails or has incomplete data
        System.out.println("BDC API failed or returned incomplete data. Attempting to get city and country from LocationIQ API...");
        cityCountry = getCityAndCountryFromLocationIQ(latitude, longitude);
        if (cityCountry != null && !cityCountry.isEmpty()) {
            System.out.println("Successfully retrieved city and country from LocationIQ API.");
        } else {
            System.out.println("Failed to retrieve city and country from both BDC and LocationIQ APIs.");
        }
        return cityCountry; // can be null if fallback also fails
    }

    private String getCityAndCountryFromBDC(double latitude, double longitude) {
        // Acquire a permit before making the request
        bdcRateLimiter.acquire();

        String urlString = String.format(
                Locale.US,
                "%s?latitude=%f&longitude=%f&localityLanguage=en&key=%s",
                API_URL, latitude, longitude, hsv_name1c2NhdVLOGOk(I_4_MJ_UX)
        );

        try {
            JsonObject json = sendGetRequest(urlString);
            if (json == null) {
                // Failed to fetch or parse response
                return null;
            }
            if (json.has("city") && json.has("countryName")) {
                return json.get("city").getAsString() + ", " + json.get("countryName").getAsString();
            } else {
                // No city/country found
                return null;
            }
        } catch (IOException e) {
            // TODO: Log the exception as needed
            e.printStackTrace();
            return null;
        }
    }

    private String getCityAndCountryFromLocationIQ(double latitude, double longitude) {
        // Acquire a permit before making the request
        locationIqRateLimiter.acquire();

        // Now safe to make the request
        String urlString = String.format(
                Locale.US,
                "%s?key=%s&lat=%f&lon=%f&format=json",
                API_URL_FALLBACK, hsv_name1c2NhdVLOGOk(Y_RL_YJ_I), latitude, longitude
        );

        try {
            JsonObject json = sendGetRequest(urlString);
            if (json == null || !json.has("address")) {
                return null;
            }

            return parseLocationIQResponse(json.getAsJsonObject("address"));
        } catch (IOException e) {
            // TODO: Log the exception as needed
            e.printStackTrace();
            return null;
        }
    }

    private String parseLocationIQResponse(JsonObject addressJson) {
        // Attempt to find something we can treat as "city"
        String city = null;
        if (addressJson.has("city")) {
            city = addressJson.get("city").getAsString();
        } else if (addressJson.has("town")) {
            city = addressJson.get("town").getAsString();
        } else if (addressJson.has("village")) {
            city = addressJson.get("village").getAsString();
        } else if (addressJson.has("county")) {
            // Fallback if city/town/village is absent
            city = addressJson.get("county").getAsString();
        }

        if (city == null) {
            return null;
        }

        if (!addressJson.has("country")) {
            return null;
        }
        String country = addressJson.get("country").getAsString();
        return city + ", " + country;
    }

    private static String hsv_name1c2NhdVLOGOk(String b2JmdXNjYXRlZEt_leQ) {
        return new String(Base64.getDecoder().decode(b2JmdXNjYXRlZEt_leQ));
    }

    private JsonObject sendGetRequest(String urlString) throws IOException {
        HttpURLConnection conn = null;
        Scanner scanner = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null; // Non-OK response means fallback or fail
            }

            StringBuilder inline = new StringBuilder();
            scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }

            return JsonParser.parseString(inline.toString()).getAsJsonObject();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}