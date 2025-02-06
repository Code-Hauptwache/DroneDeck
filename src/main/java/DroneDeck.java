package main.java;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import main.java.controllers.DroneController;
import main.java.dao.LocalDroneDao;
import main.java.dao.LocalDroneTypeDao;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.LocalSearch.LocalSearchService;
import main.java.services.LocalSearch.ILocalSearchService;
import main.java.services.DataRefresh.DataRefreshService;
import main.java.ui.components.StartupLoadingScreen;
import main.java.services.ApiToken.ApiTokenService;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.*;

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
        try {
            // Load logging configuration from resources
            InputStream configFile = DroneDeck.class.getResourceAsStream("/logging.properties");
            if (configFile != null) {
                LogManager.getLogManager().readConfiguration(configFile);
                logger.info("Logging configuration loaded successfully");
            } else {
                logger.warning("Could not find logging.properties, using default configuration");
                // Fallback to basic configuration
                Logger rootLogger = Logger.getLogger("");
                rootLogger.setLevel(Level.INFO);
                ConsoleHandler consoleHandler = new ConsoleHandler();
                consoleHandler.setLevel(Level.INFO);
                consoleHandler.setFormatter(new SimpleFormatter());
                rootLogger.addHandler(consoleHandler);
                rootLogger.setUseParentHandlers(false);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not load logging configuration", e);
        }
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

        // Create and set up a glass pane to block all interaction during loading
        JPanel glassPane = new JPanel();
        glassPane.setOpaque(false);
        // Consume all mouse and keyboard events
        glassPane.addMouseListener(new java.awt.event.MouseAdapter() {});
        glassPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {});
        glassPane.addKeyListener(new java.awt.event.KeyAdapter() {});
        glassPane.setFocusTraversalKeysEnabled(false);
        glassPane.setFocusable(true);
        frame.setGlassPane(glassPane);
        glassPane.setVisible(true);
        glassPane.requestFocusInWindow();

        // Create the loading panel with progress updates
        StartupLoadingScreen loadingPanel = new StartupLoadingScreen();
        frame.add(loadingPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setMinimumSize(new Dimension(1135, 850));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Load data and switch to the main panel asynchronously
        new SwingWorker<Void, Object[]>() {
            @Override
            protected void process(java.util.List<Object[]> chunks) {
                if (!chunks.isEmpty()) {
                    Object[] latest = chunks.getLast();
                    int progress = (Integer) latest[0];
                    String status = (String) latest[1];
                    loadingPanel.updateProgress(progress, status);
                }
            }
            @Override
            protected Void doInBackground() {
                try {
                    // Stop the pulse animation and start showing real progress
                    loadingPanel.stopPulseAnimation();
                    publish(new Object[]{0, "🚀 Starting application initialization..."});
                    logger.info("🚀 Starting application initialization...");

                    publish(new Object[]{10, "📦 Initializing data services..."});
                    logger.info("📦 Initializing data services...");
                    LocalDroneDao localDroneDao = new LocalDroneDao();
                    LocalDroneTypeDao localDroneTypeDao = new LocalDroneTypeDao();
                    Thread.sleep(500);

                    publish(new Object[]{20, "🔑 Retrieving API token..."});
                    logger.info("🔑 Retrieving API token...");
                    String apiToken = ApiTokenService.getApiToken();
                    Thread.sleep(500);

                    publish(new Object[]{30, "🔌 Setting up API service..."});
                    logger.info("🔌 Setting up API service...");
                    IDroneApiService droneApiService = new DroneApiService(apiToken);
                    DroneController droneController = new DroneController();
                    ILocalSearchService localSearchService = LocalSearchService.createInstance(localDroneDao, localDroneTypeDao, droneApiService);
                    Thread.sleep(500);

                    // Set up progress listener for LocalSearchService
                    localSearchService.setProgressListener((progress, status) -> {
                        // Map LocalSearchService's progress (0-100) to our overall progress (40-90)
                        int mappedProgress = 40 + (int)(progress * 0.5); // 0-100 -> 40-90
                        publish(new Object[]{mappedProgress, status});
                        logger.info(status);
                    });

                    // Start data initialization
                    localSearchService.initLocalData();

                    // Clean up listener
                    localSearchService.setProgressListener(null);

                    // Get initial dynamic data
                    int droneCount = localDroneDao.getDroneDataCount();
                    publish(new Object[]{90, String.format("🔄 Fetching latest dynamic data for %d drones...", droneCount)});
                    logger.info(String.format("🔄 Fetching latest dynamic data for %d drones...", droneCount));

                    if (droneCount > 0) {
                        droneController.getDroneThreads(droneCount, 0, (completed, total, status) -> {
                            // Map progress from 90-99%, ensuring we don't exceed 99%
                            int mappedProgress = 90 + Math.min(9, (int)((completed * 9.0) / total));
                            publish(new Object[]{mappedProgress, status});
                            logger.info(status);
                        });
                    }

                    // Ensure we show 100% at the end
                    Thread.sleep(100); // Brief pause to ensure last progress update is visible
                    publish(new Object[]{100, "✅ Application initialization complete"});
                    logger.info("✅ Application initialization complete");
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to initialize data.", e);
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Failed to initialize data", "Initialization Error", JOptionPane.ERROR_MESSAGE));
                }
                return null;
            }

            @Override
            protected void done() {
                loadingPanel.cleanup(); // Stop the pulse animation
                try {
                    // Remove the loading panel and disable glass pane
                    frame.remove(loadingPanel);
                    glassPane.setVisible(false);

                    // Create and add the main panel
                    main.java.ui.components.MainPanel mainPanel = new main.java.ui.components.MainPanel();
                    frame.add(mainPanel, BorderLayout.CENTER);

                    // Initialize the DataRefreshService
                    DataRefreshService.getInstance();

                    // Add window listener to shut down the refresh service
                    frame.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            DataRefreshService.getInstance().shutdown();
                        }
                    });

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
