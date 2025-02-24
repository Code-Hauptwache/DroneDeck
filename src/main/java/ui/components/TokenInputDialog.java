package main.java.ui.components;

import main.java.services.ApiToken.ApiTokenStoreService;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dialog to prompt the user to enter the password to decrypt the token.
 * This dialog will automatically save the token if the user wants to.
 */
public class TokenInputDialog extends JDialog {

    private static final Logger logger = Logger.getLogger(TokenInputDialog.class.getName());

    /**
     * Constructor for the TokenInputDialog
     *
     * @param parent The parent JFrame
     */
    public TokenInputDialog(JFrame parent) {
        super(parent, "Enter Token", ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(6, 1, 5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(new JLabel("Enter Token:"));
        JTextField tokenField = new JTextField(20);
        contentPanel.add(tokenField);

        JCheckBox saveCheckbox = new JCheckBox("Save this token");
        contentPanel.add(saveCheckbox);

        contentPanel.add(new JLabel("Enter Password (optional):"));
        JPasswordField passwordField = new JPasswordField(20);
        contentPanel.add(passwordField);

        add(contentPanel, BorderLayout.CENTER);

        add(getButtonPanel(tokenField, saveCheckbox, passwordField), BorderLayout.SOUTH);

        setLocationRelativeTo(parent); // Center the dialog relative to the parent
        setVisible(true); // Show the dialog
    }

    private JPanel getButtonPanel(JTextField tokenField, JCheckBox saveCheckbox, JPasswordField passwordField) {
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(_ -> handleOkButton(tokenField, passwordField, saveCheckbox));
        passwordField.addActionListener(_ -> handleOkButton(tokenField, passwordField, saveCheckbox));

        cancelButton.addActionListener(_ -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void handleOkButton(JTextField tokenField, JPasswordField passwordField, JCheckBox saveCheckbox) {
        String token = tokenField.getText();
        String password = new String(passwordField.getPassword());
        boolean saveToken = saveCheckbox.isSelected();

        // Save the token to the ApiTokenStoreService
        ApiTokenStoreService.setApiToken(token);

        // Check if the token is valid
        if (!ApiTokenStoreService.IsTokenValid()) {
            ApiTokenStoreService.setApiToken(""); // Clear the token
            JOptionPane.showMessageDialog(this, "Invalid Token", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save the Token if the user wants to
        if (saveToken) {
            try {
                ApiTokenStoreService.saveApiToken(password);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error saving token", ex);
            }
        }

        dispose(); // Close the dialog
    }
}
