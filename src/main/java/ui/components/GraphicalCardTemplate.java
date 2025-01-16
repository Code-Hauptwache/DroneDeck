package main.java.ui.components;

import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;

public class GraphicalCardTemplate extends JComponent {
public GraphicalCardTemplate(String title, Component content) {
    this(title, content, 1, 0);
}

public GraphicalCardTemplate(String title, Component content, int cardWeight, int cardGap) {
    setLayout(new BorderLayout(0, 10));        setLayout(new BorderLayout(0, 10));

        // Set a fixed width/height for the card
        int cardHeight = CardTemplate.cardHeight;
        int cardWidth = CardTemplate.cardWidth;
        Dimension fixedSize = new Dimension(
            cardWeight == 1 ? cardWidth : ((cardWidth * cardWeight) + ((cardGap * cardWeight) - cardGap)),
            cardHeight
        );
        setPreferredSize(fixedSize);
        setMinimumSize(fixedSize);
        setMaximumSize(fixedSize);

        // Use a FlatLineBorder for a modern border with rounded corners
        setBorder(new FlatLineBorder(
                new Insets(15, 15, 15, 15),
                UIManager.getColor("Component.borderColor"),
                1,
                40 // corner radius
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.putClientProperty("FlatLaf.styleClass", "h3");
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(content, BorderLayout.CENTER);
        contentPanel.add(content, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }
}
