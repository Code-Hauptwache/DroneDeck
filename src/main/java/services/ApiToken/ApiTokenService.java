package main.java.services.ApiToken;

import main.java.services.DroneApi.ApiModeConfig;
import main.java.ui.components.PasswordInputDialog;
import main.java.ui.components.TokenInputDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;


/**
 * Service to get the API Token.
 * If the Token is not available, the user will be prompted to enter the Token.
 */
public class ApiTokenService {
    private static final Logger logger = Logger.getLogger(ApiTokenService.class.getName());
    private static JFrame Parent;
    private static final Object lock = new Object();
    private static final String DEMO_TOKEN = "demo-mode-mock-token-1234567890";

    public static void setParent(JFrame parent) {
        Parent = parent;
    }

    /**
     * Get the API Token.
     * If the Token is not available, the user will be prompted to enter the Token.
     * In demo mode, a mock password dialog is shown for UX flow, but any password works.
     * This method SHOULD NOT be called before setting the Parent JFrame.
     *
     * @return The API Token
     */
    public static String getApiToken() {
        // Check if we're in demo mode
        if (ApiModeConfig.isDemoMode()) {
            logger.info("Demo mode: Showing mock password dialog");
            
            // In demo mode, we still want to show a password dialog for the UX flow,
            // but we'll accept any password and return a mock token
            if (Parent != null) {
                showMockPasswordDialog();
            } else {
                logger.warning("Parent frame not set, skipping mock password dialog");
            }
            
            return DEMO_TOKEN;
        }

        if (Parent == null) {
            throw new IllegalStateException("Parent JFrame is not set");
        }

        //Check if Token is available
        if (ApiTokenStoreService.IsTokenAvailable()) {
            return ApiTokenStoreService.getApiToken();
        }

        //Check for Environment Variable "API_TOKEN"
        String envToken = System.getenv("API_TOKEN");
        
        // Also check for DRONE_API_KEY as mentioned in the README
        if (envToken == null || envToken.isEmpty()) {
            envToken = System.getenv("DRONE_API_KEY");
        }

        if (envToken != null && !envToken.isEmpty()) {
            ApiTokenStoreService.setApiToken(envToken);

            if (ApiTokenStoreService.IsTokenValid()) {
                return envToken;
            }

            //Display a warning if the env token is invalid
            JOptionPane.showMessageDialog(Parent, "Invalid API Token from Environment Variable", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        //Synchronize to prevent multiple dialogs from opening
        synchronized (lock) {
            //Check if a previous thread has already set the token
            if (ApiTokenStoreService.IsTokenAvailable()) {
                return ApiTokenStoreService.getApiToken();
            }

            if (ApiTokenStoreService.IsSavedTokenAvailable()) {
                new PasswordInputDialog(Parent);

                if (ApiTokenStoreService.IsTokenAvailable()) {
                    return ApiTokenStoreService.getApiToken();
                }
            }

            new TokenInputDialog(Parent);

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
    
    /**
     * Shows a mock password dialog in demo mode.
     * This dialog is purely for demonstration purposes and accepts any password.
     */
    private static void showMockPasswordDialog() {
        logger.info("Showing mock password dialog in demo mode");
        
        JDialog dialog = new JDialog(Parent, "Enter Password (Demo Mode)", ModalityType.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setSize(400, 170);
        dialog.setLayout(new BorderLayout());
        
        // Create content panel with GridLayout just like the original
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1, 5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // First row: label
        contentPanel.add(new JLabel("Enter any Password:"));
        
        // Second row: password field
        JPasswordField passwordField = new JPasswordField(20);
        contentPanel.add(passwordField);
        
        // Add content panel to dialog
        dialog.add(contentPanel, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        
        // Add action listeners
        okButton.addActionListener(e -> dialog.dispose());
        passwordField.addActionListener(e -> dialog.dispose());
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        // Add button panel to dialog
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Set location and make visible
        dialog.setLocationRelativeTo(Parent);
        dialog.setVisible(true);
    }
}
