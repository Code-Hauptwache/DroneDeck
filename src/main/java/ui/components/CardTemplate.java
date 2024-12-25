package main.java.ui.components;

import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A CardTemplate is a JComponent that displays a card with a title, subtitle, and content.
 * The card has a border with rounded corners and a separator between the header and content.
 * It is used to create a consistent look and feel for card-like components in the application.
 */
public class CardTemplate extends JComponent {
    private static final List<CardTemplate> instances = new ArrayList<>();
    private final int cardHeight = 300;
    private final int cardWidth = 250;
    private final JLabel titleLabel;
    private final JLabel subtitleLabel;
    private final JSeparator separator;

    /**
     * Constructs a CardTemplate with the specified title, subtitle, and content.
     *
     * @param title    the title of the card
     * @param subtitle the subtitle of the card
     * @param content  the content component to display in the card
     */
    public CardTemplate(String title, String subtitle, Component content) {
        // Register this instance
        instances.add(this);

        // Use BorderLayout for main arrangement
        setLayout(new BorderLayout(0, 10));

        // Ensure we paint the background
        setOpaque(true);
        setBackground(UIManager.getColor("Panel.background"));

        // Set a fixed width/height here (adjust to your needs)
        Dimension fixedSize = new Dimension(cardWidth, cardHeight);
        setPreferredSize(fixedSize);
        setMinimumSize(fixedSize);
        setMaximumSize(fixedSize);

        // Use a FlatLineBorder for a modern border with rounded corners
        setBorder(new FlatLineBorder(
                new Insets(15, 15, 15, 15),
                UIManager.getColor("Component.borderColor"),
                1,
                30 // corner radius
        ));

        // --- Header Panel (Title + Subtitle) ---
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        // We want it to blend with the parent
        headerPanel.setOpaque(false);

        // Create and style the title label
        titleLabel = new JLabel(title);
        titleLabel.putClientProperty("FlatLaf.styleClass", "h3");

        // Create and style the subtitle label
        subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setForeground(UIManager.getColor("Label.disabledForeground"));

        // Add title and subtitle to the header panel
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Add the header panel to the top of the card
        add(headerPanel, BorderLayout.NORTH);

        // --- Center Panel (Separator + Content) ---
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setOpaque(false);

        // Add a separator below the header
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        centerPanel.add(separator, BorderLayout.NORTH);

        // Create a panel for the content and add the provided content component
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(content, BorderLayout.CENTER);

        // Add the content panel to the center panel
        centerPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the center panel to the center of the card
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Update the theme color of the card.
     * This method should be called whenever the theme changes.
     */
    public void updateThemeColor(){
        separator.setForeground(UIManager.getColor("Label.disabledForeground"));
        subtitleLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
    }

    /**
     * Update the theme color of all CardTemplate instances.
     * This method gets called whenever ButtonThemeSwitcher toggles the theme.
     */
    public static void updateAllInstances(){
        for (CardTemplate card : instances){
            card.updateThemeColor();
        }
    }

    public JLabel getSubtitleLabel() {
        return subtitleLabel;
    }

    public JSeparator getSeparator() {
        return separator;
    }
}