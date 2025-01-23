package main.java.services.ApiToken;

import main.java.ui.components.PasswordInputDialog;
import main.java.ui.components.TokenInputDialog;

import javax.swing.*;


/**
 * Service to get the API Token.
 * If the Token is not available, the user will be prompted to enter the Token.
 */
public class ApiTokenService {

    private static JFrame Parent;

    private static final Object lock = new Object();

    public static void setParent(JFrame parent) {
        Parent = parent;
    }

    /**
     * Get the API Token.
     * If the Token is not available, the user will be prompted to enter the Token.
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

        //Synchronize to prevent multiple dialogs from opening
        synchronized (lock) {

            //Check if a previous thread has already set the token
            if (ApiTokenStoreService.IsTokenAvailable()) {
                return ApiTokenStoreService.getApiToken();
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
            //in which case the application should close
            //This is to prevent the application from running without the API Token
            //(Another way this can happen is if the user closes the application while the dialog is open)
            Parent.dispose();
            System.exit(0);

            return null;
        }
    }
}
