package main.java.services.ApiToken;

import main.java.ui.TokenDialogs.PasswordInputDialog;
import main.java.ui.TokenDialogs.TokenInputDialog;

import javax.swing.*;


/**
 * Service to get the API Token.
 * If the Token is not available, the user will be prompted to enter the Token.
 */
public class ApiTokenService {

    private static JFrame Parent;

    public static void setParent(JFrame parent) {
        Parent = parent;
    }

    /**
     * Get the API Token.
     * If the Token is not available, the user will be prompted to enter the Token.
     *
     * This method SHOULD NOT be called before setting the Parent JFrame.
     *
     * @return The API Token
     */
    public static String getApiToken() {

        if (Parent == null) {
            throw new IllegalStateException("Parent JFrame is not set");
        }

        //Check if Token is available
        if (ApiTokenStoreService.IsTokenAvailable()) {
            return ApiTokenStoreService.getApiToken();
        }

        //Check for Environment Variable "API_TOKEN"
        String envToken = System.getenv("API_TOKEN");

        if (envToken != null && !envToken.isEmpty()) {
            ApiTokenStoreService.setApiToken(envToken);

            if (ApiTokenStoreService.IsTokenValid()) {
                return envToken;
            }

            //Display a warning if the dev token is invalid
            JOptionPane.showMessageDialog(Parent, "Invalid API Token from Environment Variable", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        if (ApiTokenStoreService.IsSavedTokenAvailable()) {
            PasswordInputDialog dialog = new PasswordInputDialog(Parent);

            if (ApiTokenStoreService.IsTokenAvailable()) {
                return ApiTokenStoreService.getApiToken();
            }
        }

        TokenInputDialog dialog = new TokenInputDialog(Parent);

        if (ApiTokenStoreService.IsTokenAvailable()) {
            return ApiTokenStoreService.getApiToken();
        }

        //Should never reach here
        //This only happens if the user closes the dialog without entering a token
        //The Application should not continue without a token and should close
        //TODO: Handle this properly
        throw new IllegalStateException("No API Token available");
    }
}
