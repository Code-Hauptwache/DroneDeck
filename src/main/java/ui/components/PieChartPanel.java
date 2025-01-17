package main.java.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
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

        Graphics2D g2d = (Graphics2D) g.create();
        try {
            // Enable anti-aliasing for smoother arcs
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int width  = getWidth();
            int height = getHeight();

            // -------------------------
            // Reserve space at the bottom for the legend
            // -------------------------
            int legendHeight = 50; // You can adjust this as needed
            int chartHeight  = height - legendHeight;

            // Determine the max dimension for the pie chart
            int minDimension = Math.min(width, chartHeight);

            // Center the pie chart horizontally and place it at the top
            int chartX = (width - minDimension) / 2;
            int chartY = 0; // top

            // Calculate the total of all slice values
            double total = slices.stream()
                    .mapToDouble(Slice::getValue)
                    .sum();

            // We will keep track of the "startAngle" for each slice
            double currentValue = 0.0;
            // Some padding around the chart to avoid drawing to the very edge
            int chartPadding = 5;

            // Decide how thick your "gap" stroke should be
            // e.g. 10px wide stroke, which also provides a rounded boundary
            float gapStrokeWidth = 3f;

            // Draw each slice of the pie
            for (Slice slice : slices) {
                double startAngle = currentValue * 360 / total;
                double arcAngle   = slice.getValue() * 360 / total;

                // Construct the arc for this slice
                Arc2D.Double arc = new Arc2D.Double(
                        chartX + chartPadding,
                        chartY + chartPadding,
                        minDimension - 2.0 * chartPadding,
                        minDimension - 2.0 * chartPadding,
                        startAngle,
                        arcAngle,
                        Arc2D.PIE
                );

                // 1) Fill the arc with the slice color
                g2d.setColor(slice.getColor());
                g2d.fill(arc);

                // 2) Immediately draw a thick stroke in the background color
                //    to create a gap between adjacent slices
                g2d.setColor(getBackground());
                g2d.setStroke(
                        new BasicStroke(gapStrokeWidth,
                                BasicStroke.CAP_ROUND,
                                BasicStroke.JOIN_ROUND)
                );
                g2d.draw(arc);

                currentValue += slice.getValue();
            }

            // -------------------------
            // Draw the legend at the bottom, horizontally centered
            // -------------------------
            // Legend item sizes
            int squareSize   = 10;  // Size of the color indicator
            int itemSpacing  = 20;  // Horizontal space between legend items
            int labelPadding = 5;   // Space between the color square and label
            int valuePadding = 5;   // Space between the label and value text

            FontMetrics fm = g2d.getFontMetrics();
            int totalLegendWidth = 0;

            for (Slice slice : slices) {
                String labelText = slice.getLabel();
                String valueText = String.valueOf((int) slice.getValue());

                int labelWidth = fm.stringWidth(labelText);
                int valueWidth = fm.stringWidth(valueText);

                // Width = square + labelPadding + label + valuePadding + value
                int itemWidth = squareSize
                        + labelPadding
                        + labelWidth
                        + valuePadding
                        + valueWidth;
                totalLegendWidth += itemWidth;
            }
            // Add spacing between items
            totalLegendWidth += itemSpacing * (slices.size() - 1);

            // Compute where the legend starts to center it horizontally
            int legendX = (width - totalLegendWidth) / 2;
            // Place the legend Y in the middle of that bottom region
            int legendY = minDimension + (legendHeight - squareSize) / 2;

            // Render each legend item (color square + label + value)
            for (Slice slice : slices) {
                String labelText = slice.getLabel();
                String valueText = String.valueOf((int) slice.getValue());

                g2d.setColor(slice.getColor());
                g2d.fillRoundRect(legendX, legendY, squareSize, squareSize,
                        squareSize, squareSize);

                int labelWidth = fm.stringWidth(labelText);
                int valueWidth = fm.stringWidth(valueText);

                // Draw label in default foreground color
                g2d.setColor(getForeground());
                int labelX = legendX + squareSize + labelPadding;
                int labelY = legendY + (squareSize - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(labelText, labelX, labelY);

                // Draw value in disabled-foreground color
                g2d.setColor(UIManager.getColor("Label.disabledForeground"));
                int valueX = labelX + labelWidth + valuePadding;
                int valueY = labelY;
                g2d.drawString(valueText, valueX, valueY);

                // Move to the next legend item
                int totalItemWidth = squareSize
                        + labelPadding
                        + labelWidth
                        + valuePadding
                        + valueWidth;
                legendX += totalItemWidth + itemSpacing;
            }

        } finally {
            g2d.dispose();
        }
    }

    public static class Slice {
        private final double value;
        private final Color color;
        private final String label;

        public Slice(double value, Color color, String label) {
            this.value = value;
            this.color = color;
            this.label = label;
        }

        public double getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }

        public String getLabel() {
            return label;
        }
    }
}
