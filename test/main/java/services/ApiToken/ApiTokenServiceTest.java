package main.java.services.ApiToken;

import main.java.ui.DroneDeck;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class ApiTokenServiceTest {

    @Test
    void testGetApiToken() {

        try {

            //Create main window
            DroneDeck.main(new String[]{});

            boolean _ =new File("api_token.bin").delete();

            var token = ApiTokenService.getApiToken();

        } catch (Exception e) {
            fail("Exception occurred during test execution: " + e.getMessage());
        }

    }
}
