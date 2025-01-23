package main.java.services.ApiToken;

import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.Drone;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for storing and retrieving API tokens securely.
 * This Service SHOULD NOT be used directly.
 */
public class ApiTokenStoreService {

    private static final Logger logger = Logger.getLogger(ApiTokenStoreService.class.getName());

    private static final String API_TOKEN_FILE = "api_token.bin";

    private static String ApiToken = "";

    /**
     * Checks if a saved API Token is available.
     * This DOES NOT check the validity of the Token.
     * This DOES NOT load the Token. The Token must be loaded separately.
     *
     * @return True if a saved Token is available
     */
    public static Boolean IsSavedTokenAvailable() {
        //Check if API_TOKEN_FILE Exists
        File file = new File(API_TOKEN_FILE);
        return file.exists();
    }

    /**
     * Checks if an API Token is available.
     * This DOES NOT check the validity of the Token.
     *
     * @return True if Token is available
     */
    public static Boolean IsTokenAvailable() {
        return !ApiToken.isEmpty();
    }

    /**
     * Checks if the current API Token is valid.
     *
     * @return True if the Token is valid
     */
    public static Boolean IsTokenValid() {
        IDroneApiService droneApiService = new DroneApiService(ApiToken);

        try {
            List<Drone> drones = droneApiService.getDrones();

            return drones != null && !drones.isEmpty();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to validate API token", ex);
            return false;
        }
    }

    /**
     * Retrieves the API token from the storage file.
     *
     * @param password The password used to decrypt the token
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException If the stored object is not found
     */
    public static void loadApiToken(String password) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(API_TOKEN_FILE))) {

            // Read and validate the object
            Object obj = ois.readObject();
            if (!(obj instanceof ApiTokenStoreDto apiTokenStoreDto)) {
                throw new IllegalArgumentException("Invalid object type: Expected ApiTokenStoreDto");
            }

            // Process the token
            SecretKey key = getKeyFromPassword(password, apiTokenStoreDto.Salt);
            ApiToken = decrypt(apiTokenStoreDto.Token, key, new IvParameterSpec(apiTokenStoreDto.IV));

        } catch (IOException | ClassNotFoundException ex) {
            // Rethrow specific exceptions for better clarity
            logger.log(Level.SEVERE, "Failed to retrieve API token", ex);
            throw ex;
        } catch (Exception ex) {
            // Wrap and rethrow generic exceptions with context
            logger.log(Level.SEVERE, "Failed to retrieve API token", ex);
            throw new RuntimeException("Failed to retrieve API token", ex);
        }
    }

    /**
     * Saves the API token to the storage file.
     *
     * @param password The password used to encrypt the token
     * @throws IOException If an I/O error occurs
     */
    public static void saveApiToken(String password) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(API_TOKEN_FILE))) {

            // Generate necessary cryptographic components
            String salt = generateSalt();
            SecretKey key = getKeyFromPassword(password, salt);
            IvParameterSpec iv = generateIv();

            // Prepare DTO for serialization
            ApiTokenStoreDto apiTokenStoreDto = new ApiTokenStoreDto();
            apiTokenStoreDto.Token = encrypt(ApiToken, key, iv);
            apiTokenStoreDto.IV = iv.getIV();
            apiTokenStoreDto.Salt = salt;

            // Write the DTO to the file
            oos.writeObject(apiTokenStoreDto);

        } catch (Exception ex) {
            // Catch all relevant exceptions and wrap them in a RuntimeException with context
            logger.log(Level.SEVERE, "Failed to save API token", ex);
            throw new RuntimeException("Failed to save API token", ex);
        }
    }

    /**
     * Encrypts the input string using the provided key and IV.
     *
     * @param input The input string to be encrypted
     * @param key The secret key used for encryption
     * @param iv The initialization vector
     * @return The encrypted string, encoded in Base64
     * @throws NoSuchPaddingException If the padding scheme is not available
     * @throws NoSuchAlgorithmException If the algorithm is not available
     * @throws InvalidAlgorithmParameterException If the algorithm parameters are invalid
     * @throws InvalidKeyException If the key is invalid
     * @throws BadPaddingException If the padding is incorrect
     * @throws IllegalBlockSizeException If the block size is incorrect
     */
    private static String encrypt(String input, SecretKey key,
                                 IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);

    }

    /**
     * Decrypts the encrypted string using the provided key and IV.
     *
     * @param cipherText The encrypted string, encoded in Base64
     * @param key The secret key used for decryption
     * @param iv The initialization vector
     * @return The decrypted string
     * @throws NoSuchPaddingException If the padding scheme is not available
     * @throws NoSuchAlgorithmException If the algorithm is not available
     * @throws InvalidAlgorithmParameterException If the algorithm parameters are invalid
     * @throws InvalidKeyException If the key is invalid
     * @throws BadPaddingException If the padding is incorrect
     * @throws IllegalBlockSizeException If the block size is incorrect
     */
    private static String decrypt(String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    /**
     * Generates a secret key from the given password and salt.
     *
     * @param password The password
     * @param salt The salt
     * @return The generated secret key
     * @throws NoSuchAlgorithmException If the algorithm is not available
     * @throws InvalidKeySpecException If the key specification is invalid
     */
    private static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");

    }

    /**
     * Generates a random initialization vector (IV).
     *
     * @return The generated IV
     */
    private static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * Generates a random salt.
     *
     * @return The generated salt, encoded in Base64
     */
    public static String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt); // Generate random bytes
        return Base64.getEncoder().encodeToString(salt); // Return the salt as a Base64-encoded string
    }

    public static String getApiToken() {
        return ApiToken;
    }

    public static void setApiToken(String apiToken) {
        ApiToken = apiToken;
    }
}
