package main.java.ui.TokenDialogs;

import main.java.services.ApiToken.ApiTokenStoreService;

import javax.swing.*;
import java.awt.*;

public class PasswordInputDialog extends JDialog {

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
                //TODO: Handle exception properly
                //This exception is thrown when the token file is not found
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
