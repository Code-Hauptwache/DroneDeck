package main.java.ui.components;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBar extends JPanel {
    private JButton catalogButton;
    private JButton dashboardButton;

    public NavigationBar() {
        // Set the layout manager
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Add Navigation Buttons
        catalogButton = new JButton("Catalog");
        dashboardButton = new JButton("Dashboard");

        // Add action listeners to buttons
        catalogButton.addActionListener(new NavigationButtonListener());
        dashboardButton.addActionListener(new NavigationButtonListener());

        // Add buttons to the panel
        add(catalogButton);
        add(dashboardButton);

        // Add a filler to push the theme switch button to the right
        add(Box.createHorizontalGlue());

        // Add the theme switch button
        ButtonThemeSwitch themeSwitch = new ButtonThemeSwitch();
        add(themeSwitch);
    }

    private class NavigationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == catalogButton) {
                // Switch to Catalog page
                System.out.println("Switching to Catalog page");
                catalogButton.setFont(catalogButton.getFont().deriveFont(Font.BOLD));
                dashboardButton.setFont(dashboardButton.getFont().deriveFont(Font.PLAIN));
            } else if (source == dashboardButton) {
                // Switch to Dashboard page
                System.out.println("Switching to Dashboard page");
                dashboardButton.setFont(dashboardButton.getFont().deriveFont(Font.BOLD));
                catalogButton.setFont(catalogButton.getFont().deriveFont(Font.PLAIN));
            }
        }
    }
}
