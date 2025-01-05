package main.java.services.ApiToken;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for ApiTokenStoreService
 */
class ApiTokenStoreServiceTest {
    @Test
    void IsTokenAvailable_returnsTrue_whenSavedTokenFileExists() {
        // Setup
        // Create a dummy token file
        File file = new File("api_token.bin");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Act
        boolean result = ApiTokenStoreService.IsSavedTokenAvailable();

        // Assert
        assertTrue(result);

        // Cleanup
        file.delete();
    }

    @Test
    void IsTokenAvailable_returnsFalse_whenSavedTokenFileDoesNotExist() {
        // Act
        boolean result = ApiTokenStoreService.IsSavedTokenAvailable();

        // Assert
        assertFalse(result);
    }

    @Test
    void loadApiToken_returnsDecryptedToken_whenValidPasswordProvided() throws IOException, ClassNotFoundException {
        // Setup
        String password = "validPassword";
        String token = "testToken";

        ApiTokenStoreService.setApiToken(token);
        ApiTokenStoreService.saveApiToken(password);

        // Act
        ApiTokenStoreService.loadApiToken(password);
        String result = ApiTokenStoreService.getApiToken();

        // Assert
        assertEquals(token, result);

        // Cleanup
        new File("api_token.bin").delete();
    }

    @Test
    void loadApiToken_throwsException_whenInvalidPasswordProvided() {
        // Setup
        String password = "validPassword";
        String invalidPassword = "invalidPassword";
        String token = "testToken";

        ApiTokenStoreService.setApiToken(token);
        try {
            ApiTokenStoreService.saveApiToken(password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Act & Assert
        assertThrows(RuntimeException.class, () -> ApiTokenStoreService.loadApiToken(invalidPassword));

        // Cleanup
        new File("api_token.bin").delete();
    }

    @Test
    void saveApiToken_savesTokenSuccessfully() throws IOException {
        // Setup
        String password = "validPassword";
        String token = "testToken";

        // Act
        ApiTokenStoreService.setApiToken(token);
        ApiTokenStoreService.saveApiToken(password);

        // Assert
        assertTrue(new File("api_token.bin").exists());

        // Cleanup
        new File("api_token.bin").delete();
    }
}