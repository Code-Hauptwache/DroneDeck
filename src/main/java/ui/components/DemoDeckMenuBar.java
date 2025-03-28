package main.java.ui.components;

import main.java.services.DroneApi.ApiModeConfig;
import main.java.services.DataRefresh.DataRefreshService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Menu bar for the DroneDeck application with options
 * including toggling between live and demo mode.
 */
public class DemoDeckMenuBar extends JMenuBar {
    private static final Logger logger = Logger.getLogger(DemoDeckMenuBar.class.getName());
    private final JCheckBoxMenuItem demoModeItem;
    
    /**
     * Creates a new DemoDeckMenuBar.
     * 
     * @param frame The parent JFrame this menu bar belongs to
     */
    public DemoDeckMenuBar(JFrame frame) {
        // Create File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        // Exit item
        JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitItem.addActionListener(e -> {
            // Shut down the data refresh service
            DataRefreshService.getInstance().shutdown();
            System.exit(0);
        });
        fileMenu.add(exitItem);
        
        // Create Mode menu
        JMenu modeMenu = new JMenu("Mode");
        modeMenu.setMnemonic(KeyEvent.VK_M);
        
        // Demo mode toggle option
        demoModeItem = new JCheckBoxMenuItem("Demo Mode (Offline)");
        demoModeItem.setMnemonic(KeyEvent.VK_D);
        demoModeItem.setState(ApiModeConfig.isDemoMode());
        demoModeItem.addActionListener(this::toggleDemoMode);
        modeMenu.add(demoModeItem);
        
        // Create Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        
        JMenuItem aboutItem = new JMenuItem("About DroneDeck", KeyEvent.VK_A);
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "DroneDeck v1.0\n" +
                    "Demo mode allows running the application without a connection to the drone API server.\n\n" +
                    "© 2024 Frankfurt UAS - OOP Java Course",
                    "About DroneDeck",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(aboutItem);
        
        // Add all menus to the menu bar
        add(fileMenu);
        add(modeMenu);
        add(helpMenu);
    }
    
    /**
     * Handle toggling demo mode on and off.
     * Note: This only changes the configuration flag. Application restart
     * would be required for the changes to fully take effect.
     */
    private void toggleDemoMode(ActionEvent e) {
        boolean newDemoMode = demoModeItem.getState();
        ApiModeConfig.setDemoMode(newDemoMode);
        
        logger.log(Level.INFO, "Demo mode set to: " + newDemoMode);
        
        String message = newDemoMode ? 
                "Demo mode enabled. Application uses mock data instead of the API.\n" +
                "Please note: Some changes will only take effect after restarting the application." :
                "Demo mode disabled. Application will use the live API.\n" +
                "Please note: Some changes will only take effect after restarting the application.";
        
        JOptionPane.showMessageDialog(
                null,
                message,
                "Demo Mode " + (newDemoMode ? "Enabled" : "Disabled"),
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}