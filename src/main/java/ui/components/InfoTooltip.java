package main.java.ui.components;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class InfoTooltip extends JComponent {

    public InfoTooltip(String tooltipText) {
        FontIcon infoIcon = FontIcon.of(FontAwesomeSolid.INFO_CIRCLE, 16, UIManager.getColor("Label.foreground"));
        JLabel iconLabel = new JLabel(infoIcon);
        iconLabel.setToolTipText(tooltipText);
        setLayout(new BorderLayout());
        add(iconLabel, BorderLayout.CENTER);
    }
}