package main.java.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import main.java.dao.LocalDroneDao;
import main.java.dao.LocalDroneTypeDao;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.LocalSearch.LocalSearchService;
import main.java.services.LocalSearch.ILocalSearchService;
import main.java.ui.components.StartupLoadingScreen;
import main.java.services.ApiToken.ApiTokenService;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The DroneDeck class is the main
 * entry point of the DroneDeck application.
 */
public class DroneDeck {

    private static final Logger logger = Logger.getLogger(DroneDeck.class.getName());

    /**
     * The main entry point of the application.
     *
     * @param args the command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        configureLogging();
        SwingUtilities.invokeLater(DroneDeck::createAndShowGUI);
    }

    /**
     * Creates and displays the GUI for the DroneDeck application.
     */
    private static void createAndShowGUI() {
        // Set up ToolTipManager
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setReshowDelay(0);

        // Load Google Font
        try (InputStream is = DroneDeck.class.getResourceAsStream("/Lato-Bold.ttf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is)).deriveFont(16f);
            UIManager.put("defaultFont", font);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load the font.", e);
            JOptionPane.showMessageDialog(null, "Failed to load the font. The application will use the default font.", "Font Load Error", JOptionPane.ERROR_MESSAGE);
        }

        setupFlatLaf();
        setupMainPanel();
    }

    private static void configureLogging() {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);

        // Configure the console handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new SimpleFormatter());
        rootLogger.addHandler(consoleHandler);

        // Remove the default console handler
        rootLogger.setUseParentHandlers(false);

        // Example log message
        logger.info("Application started");
    }

    private static void setupFlatLaf() {
        // Set up FlatLaf look and feel
        FlatLaf.registerCustomDefaultsSource("main.java.ui.themes");

        final OsThemeDetector detector = OsThemeDetector.getDetector();
        final boolean isDarkThemeUsed = detector.isDark();
        if (isDarkThemeUsed) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }
    }

    private static void setupMainPanel() {
        // Create the frame
        JFrame frame = new JFrame("DroneDeck");

        ApiTokenService.setParent(frame); //Initialize the ApiTokenService with the parent frame

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the logo image
        try {
            ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(DroneDeck.class.getResource("/DroneDeck_Logo.png")));
            Image scaledLogo = logoIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            frame.setIconImage(scaledLogo);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load the logo image.", e);
            JOptionPane.showMessageDialog(null, "Failed to load the logo image.", "Logo Load Error", JOptionPane.ERROR_MESSAGE);
        }

        // Create the loading panel
        StartupLoadingScreen loadingPanel = new StartupLoadingScreen();

        // Add the loading panel to the frame
        frame.add(loadingPanel, BorderLayout.CENTER);

        // Call pack() so that components are laid out properly
        frame.pack();

        // Enforce the minimum size (the user cannot shrink the window below this)
        frame.setMinimumSize(new Dimension(1135, 850));

        // Center the frame on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Load data and switch to the main panel asynchronously
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    // Initialize LocalSearchService
                    LocalDroneDao localDroneDao = new LocalDroneDao();
                    LocalDroneTypeDao localDroneTypeDao = new LocalDroneTypeDao();

                    String apiToken = ApiTokenService.getApiToken();

                    IDroneApiService droneApiService = new DroneApiService(apiToken);
                    ILocalSearchService localSearchService = LocalSearchService.createInstance(localDroneDao, localDroneTypeDao, droneApiService);

                    // Update local drone data
                    localSearchService.initLocalData();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to initialize data.", e);
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Failed to initialize data", "Initialization Error", JOptionPane.ERROR_MESSAGE));
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    // Remove the loading panel
                    frame.remove(loadingPanel);

                    // Create and add the main panel
                    MainPanel mainPanel = new MainPanel();
                    frame.add(mainPanel, BorderLayout.CENTER);

                    // Revalidate and repaint the frame to apply changes
                    frame.revalidate();
                    frame.repaint();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to switch to the main panel.", e);
                    JOptionPane.showMessageDialog(frame, "Failed to switch to the main panel", "Panel Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
}
