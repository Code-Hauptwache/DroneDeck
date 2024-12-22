package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

public class SearchBar extends JComponent {
    public SearchBar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // TODO: Implement the Search Bar

        // This is a placeholder in orange for the search bar
        JLabel searchLabel = new JLabel("Search Bar (TODO)");
        searchLabel.setForeground(Color.ORANGE);
        add(searchLabel);

    }
}
