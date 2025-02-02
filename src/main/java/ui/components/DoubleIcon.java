package main.java.ui.components;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public class DoubleIcon implements Icon {
    private final Icon icon;
    private final int gap; // space between the two icons

    public DoubleIcon(Icon icon, int gap) {
        this.icon = icon;
        this.gap = gap;
    }

    @Override
    public int getIconWidth() {
        // width is two times the icon width plus a gap
        return icon.getIconWidth() * 2 + gap;
    }

    @Override
    public int getIconHeight() {
        return icon.getIconHeight();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // draw the first icon
        icon.paintIcon(c, g, x, y);
        // draw the second icon with an offset (icon width + gap)
        icon.paintIcon(c, g, x + icon.getIconWidth() + gap, y);
    }
}

