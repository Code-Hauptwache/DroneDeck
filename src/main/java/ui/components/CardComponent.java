package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

public class CardComponent extends JPanel {
    private String typeName;
    private String brand;
    private String content;

    public CardComponent(String typeName, String brand, String content) {
        this.typeName = typeName;
        this.brand = brand;
        this.content = content;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel typeNameLabel = new JLabel(typeName);
        typeNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(typeNameLabel, BorderLayout.NORTH);

        JLabel brandLabel = new JLabel(brand);
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(brandLabel, BorderLayout.CENTER);

        JSeparator separator = new JSeparator();
        add(separator, BorderLayout.SOUTH);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        add(contentArea, BorderLayout.SOUTH);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
