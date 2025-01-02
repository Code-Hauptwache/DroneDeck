package main.java.services.ReverseGeocode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReverseGeocodeServiceTest {

    @Test
    void testGetCityAndCountry() {
        IReverseGeocodeService service = new ReverseGeocodeService();
        String result = service.getCityAndCountry(12.34, 56.78);
        assertNotNull(result, "The result should not be null");
        assertTrue(result.contains(","), "The result should contain a comma separating city and country");
    }
}