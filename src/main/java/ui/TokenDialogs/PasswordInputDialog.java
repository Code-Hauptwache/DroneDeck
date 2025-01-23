package main.java.ui.TokenDialogs;

import main.java.services.ApiToken.ApiTokenStoreService;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dialog to get the password for the token.
 *
 * This dialog will automatically load the token from the file and check if the token is valid.
 */
public class PasswordInputDialog extends JDialog {

    private static final Logger logger = Logger.getLogger(PasswordInputDialog.class.getName());

    public PasswordInputDialog(JFrame parent) {
        super(parent, "Enter Token", ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setSize(400, 170);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1, 5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(new JLabel("Enter Password (optional):"));
        JPasswordField passwordField = new JPasswordField(20);
        contentPanel.add(passwordField);

        add(contentPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(event -> {
            String password = new String(passwordField.getPassword());

            //Save the token to the ApiTokenStoreService
            try {
                ApiTokenStoreService.loadApiToken(password);
            } catch (Exception ex) {
                //This exception is thrown when the token file is not found
                logger.log(Level.INFO, "Failed to load the token", ex);
            }

            //Check if the token is valid
            if(!ApiTokenStoreService.IsTokenValid()) {
                ApiTokenStoreService.setApiToken(""); // Clear the token
                JOptionPane.showMessageDialog(this, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dispose(); // Close the dialog
        });

        cancelButton.addActionListener(event -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(parent); // Center the dialog relative to the parent
        setVisible(true); // Show the dialog
    }

}
