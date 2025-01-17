package main.java.ui.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PieChartPanel extends JComponent {
    private final List<Slice> slices;

    public PieChartPanel(List<Slice> slices) {
        this.slices = slices;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (slices == null || slices.isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int minDimension = Math.min(width, height);
        int x = (width - minDimension) / 2;
        int y = (height - minDimension) / 2;

        double total = slices.stream().mapToDouble(Slice::getValue).sum();
        double curValue = 0.0;
        int startAngle;

        for (Slice slice : slices) {
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slice.getValue() * 360 / total);

            g2d.setColor(slice.getColor());
            g2d.fillArc(x, y, minDimension, minDimension, startAngle, arcAngle);
            curValue += slice.getValue();
        }
    }

    public static class Slice {
        private final double value;
        private final Color color;

        public Slice(double value, Color color) {
            this.value = value;
            this.color = color;
        }

        public double getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }
    }
}