package main.java.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import main.java.dao.LocalDroneDao;
import main.java.dao.LocalDroneTypeDao;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.LocalSearch.LocalSearchService;
import main.java.services.LocalSearch.ILocalSearchService;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

public class DroneDeck {

    /**
     * The main entry point of the application.
     * It sets up the FlatLaf look and feel, loads a Google Font,
     * creates the main frame, and makes it visible.
     *
     * @param args the command line arguments
     *             (not used in this application)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DroneDeck::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Set up ToolTipManager
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setReshowDelay(0);

        // Set up FlatLaf look and feel
        FlatLaf.registerCustomDefaultsSource("main.java.ui.themes");

        final OsThemeDetector detector = OsThemeDetector.getDetector();
        final boolean isDarkThemeUsed = detector.isDark();
        if (isDarkThemeUsed) {
            //The OS uses a dark theme
            FlatDarkLaf.setup();
        } else {
            //The OS uses a light theme
            FlatLightLaf.setup();
        }

        // Load Google Font
        try (InputStream is = DroneDeck.class.getResourceAsStream("/Lato-Bold.ttf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is)).deriveFont(16f);
            UIManager.put("defaultFont", font);
        }
        catch (Exception e) {
            // TODO: Handle exception properly

            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        // Initialize LocalSearchService
        LocalDroneDao localDroneDao = new LocalDroneDao();
        LocalDroneTypeDao localDroneTypeDao = new LocalDroneTypeDao();
        DroneApiService droneApiService = new DroneApiService(System.getenv("DRONE_API_KEY"));
        ILocalSearchService localSearchService = LocalSearchService.createInstance(localDroneDao, localDroneTypeDao, droneApiService);

        // Update local drone data
        localSearchService.initLocalData();

        // Create the frame
        JFrame frame = new JFrame("DroneDeck");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the logo image
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(DroneDeck.class.getResource("/DroneDeck_Logo.png")));
        Image scaledLogo = logoIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        frame.setIconImage(scaledLogo);

        // Create and add the main panel
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel, BorderLayout.CENTER);

        // Call pack() so that components are laid out properly
        frame.pack();

        // Enforce the minimum size (the user cannot shrink the window below this)
        frame.setMinimumSize(new Dimension(1100, 850));

        // Center the frame on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
