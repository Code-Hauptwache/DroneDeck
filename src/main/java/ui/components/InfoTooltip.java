package main.java.ui.components;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

/**
 * The InfoTooltip class is a custom
 * JComponent that represents an information tooltip.
 */
public class InfoTooltip extends JComponent {
    private final JLabel iconLabel;

    /**
     * Creates a new InfoTooltip with the given tooltip text.
     *
     * @param tooltipText The text to display in the tooltip.
     */
    public InfoTooltip(String tooltipText) {
        // Create the icon
        FontIcon infoIcon = FontIcon.of(FontAwesomeSolid.INFO_CIRCLE, 16,
                UIManager.getColor("Label.foreground"));

        // Create the label, set tooltip
        iconLabel = new JLabel(infoIcon);
        iconLabel.setToolTipText(tooltipText);

        // Add the label to this component
        setLayout(new BorderLayout());
        add(iconLabel, BorderLayout.CENTER);
    }

    // Ensure the component always stays the same size as the icon
    @Override
    public Dimension getPreferredSize() {
        return iconLabel.getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return iconLabel.getMinimumSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return iconLabel.getPreferredSize();
    }
}
