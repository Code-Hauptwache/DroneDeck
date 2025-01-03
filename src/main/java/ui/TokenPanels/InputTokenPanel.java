package main.java.ui.TokenPanels;

import main.java.services.ApiTokenStore.ApiTokenStoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class InputTokenPanel extends JPanel {

    private final int PANEL_PADDING = 10;
    private JTextField tokenField;
    private JCheckBox saveTokenCheckbox;
    private JPasswordField passwordField;
    private JButton submitButton;
    private ActionListener submitAction;

    public InputTokenPanel(ActionListener submitAction) {
        super(new GridBagLayout()); // Use GridBagLayout for precise alignment

        this.submitAction = submitAction;

        setBorder(BorderFactory.createEmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; // Start at column 0
        gbc.gridy = 0; // Start at row 0
        gbc.weightx = 0; // No horizontal stretch
        gbc.anchor = GridBagConstraints.WEST; // Align to the left

        // Add token label
        JLabel tokenLabel = new JLabel("Enter Token:");
        add(tokenLabel, gbc);

        // Add token input field
        tokenField = new JTextField(20); // Fixed size
        gbc.gridy = 1; // Move to row 1
        gbc.weightx = 1; // Allow horizontal resizing for input field
        gbc.anchor = GridBagConstraints.CENTER; // Center-align input field
        add(tokenField, gbc);

        // Add checkbox for saving the token
        saveTokenCheckbox = new JCheckBox("Save Token");
        gbc.gridy = 3; // Move to next row
        gbc.weightx = 1; // Allow horizontal resizing for checkbox
        gbc.anchor = GridBagConstraints.WEST;
        add(saveTokenCheckbox, gbc);

        // Add password label (initially hidden)
        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordField = new JPasswordField(20); // Fixed size
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);

        gbc.gridy = 4; // Move to next row
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST; // Align to the left
        add(passwordLabel, gbc);

        gbc.gridy = 5; // Move to next row
        gbc.weightx = 1; // Allow horizontal resizing for password field
        gbc.anchor = GridBagConstraints.CENTER; // Center-align input field
        add(passwordField, gbc);

        // Toggle visibility of password field based on checkbox
        saveTokenCheckbox.addActionListener(e -> {
            boolean isSelected = saveTokenCheckbox.isSelected();
            passwordField.setVisible(isSelected);
            passwordLabel.setVisible(isSelected);
            revalidate();
            repaint();
        });

        // Add submit button
        submitButton = new JButton("Submit");
        gbc.gridy = 6; // Move to next row
        gbc.anchor = GridBagConstraints.CENTER; // Center-align button
        add(submitButton, gbc);

        submitButton.addActionListener(e -> {

            //Get the token and password
            String token = tokenField.getText();
            String password = new String(passwordField.getPassword());

            ApiTokenStoreService.setApiToken(token);

            //Save token to token store
            if (saveTokenCheckbox.isSelected()) {
                try {
                    ApiTokenStoreService.saveApiToken(password);
                } catch (Exception ex) {
                    //TODO: Handle exception properly and improve error message
                    JOptionPane.showMessageDialog(this, "Error saving token: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            //Call the submit action
            submitAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Token Submitted"));
        });
    }
}
