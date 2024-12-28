package main.java.services.ApiTokenStore;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for ApiTokenStoreService
 */
class ApiTokenStoreServiceTest {
    @Test
    void IsTokenAvailable_returnsTrue_whenTokenFileExists() {
        // Setup
        // Create a dummy token file
        File file = new File("api_token.bin");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Act
        boolean result = ApiTokenStoreService.IsTokenAvailable();

        // Assert
        assertTrue(result);

        // Cleanup
        file.delete();
    }

    @Test
    void IsTokenAvailable_returnsFalse_whenTokenFileDoesNotExist() {
        // Act
        boolean result = ApiTokenStoreService.IsTokenAvailable();

        // Assert
        assertFalse(result);
    }

    @Test
    void getApiToken_returnsDecryptedToken_whenValidPasswordProvided() throws IOException, ClassNotFoundException {
        // Setup
        String password = "validPassword";
        String token = "testToken";
        ApiTokenStoreService.saveApiToken(token, password);

        // Act
        String result = ApiTokenStoreService.getApiToken(password);

        // Assert
        assertEquals(token, result);

        // Cleanup
        new File("api_token.bin").delete();
    }

    @Test
    void getApiToken_throwsException_whenInvalidPasswordProvided() {
        // Setup
        String password = "validPassword";
        String invalidPassword = "invalidPassword";
        String token = "testToken";
        try {
            ApiTokenStoreService.saveApiToken(token, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Act & Assert
        assertThrows(RuntimeException.class, () -> ApiTokenStoreService.getApiToken(invalidPassword));

        // Cleanup
        new File("api_token.bin").delete();
    }

    @Test
    void saveApiToken_savesTokenSuccessfully() throws IOException {
        // Setup
        String password = "validPassword";
        String token = "testToken";

        // Act
        ApiTokenStoreService.saveApiToken(token, password);

        // Assert
        assertTrue(new File("api_token.bin").exists());

        // Cleanup
        new File("api_token.bin").delete();
    }

    @Test
    void token_getsDecrypted_whenValidPasswordProvided() throws IOException, ClassNotFoundException {
        // Setup
        String password = "validPassword";
        String token = "testToken";

        // Act
        ApiTokenStoreService.saveApiToken(token, password);

        String decryptedToken = ApiTokenStoreService.getApiToken(password);

        // Assert
        assertEquals(token, decryptedToken);

        // Cleanup
        new File("api_token.bin").delete();
    }

}