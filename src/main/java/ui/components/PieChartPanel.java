package main.java.ui.components;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
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
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int width  = getWidth();
            int height = getHeight();

            // Measure the legend first
            LegendLayout layout = measureLegend(g2d, width);
            int neededLegendHeight = layout.totalHeight;

            // The chart gets whatever space remains
            int chartHeight  = height - neededLegendHeight;
            int minDimension = Math.min(width, chartHeight);

            // Draw the pie chart in the top region
            drawPieChart(g2d, minDimension);

            // Render the legend in the leftover space
            drawLegendWithWrapping(g2d, layout, width, minDimension);

        } finally {
            g2d.dispose();
        }
    }

    private static class LegendLayout {
        List<List<LegendItem>> rows;
        int totalHeight;
    }

    private LegendLayout measureLegend(Graphics2D g2d, int panelWidth) {
        // Basic sizes
        int squareSize   = 10;
        int itemSpacing  = 20;
        int labelPadding = 5;
        int valuePadding = 5;

        FontMetrics fm = g2d.getFontMetrics();

        // Convert each slice into a "LegendItem"
        List<LegendItem> items = new ArrayList<>();
        for (Slice slice : slices) {
            String labelText = slice.label();
            String valueText = String.valueOf((int) slice.value());

            int labelWidth = fm.stringWidth(labelText);
            int valueWidth = fm.stringWidth(valueText);

            int itemWidth = squareSize
                    + labelPadding
                    + labelWidth
                    + valuePadding
                    + valueWidth;

            int itemHeight = Math.max(squareSize, fm.getHeight());

            items.add(new LegendItem(slice, labelWidth, valueWidth, itemWidth, itemHeight));
        }

        // Split items into rows
        List<List<LegendItem>> rows = getLists(panelWidth, items, itemSpacing);

        // Compute total legend height by summing the tallest item in each row
        int totalHeight = 0;
        for (List<LegendItem> row : rows) {
            int rowMaxHeight = row.stream()
                    .mapToInt(li -> li.itemHeight)
                    .max()
                    .orElse(fm.getHeight());
            totalHeight += rowMaxHeight + 5; // +5 for a small gap after each row
        }
        // Add a bit of top padding, say 10px
        totalHeight += 10;

        LegendLayout layout = new LegendLayout();
        layout.rows = rows;
        layout.totalHeight = totalHeight;
        return layout;
    }

    private static @NotNull List<List<LegendItem>> getLists(int panelWidth, List<LegendItem> items, int itemSpacing) {
        List<List<LegendItem>> rows = new ArrayList<>();
        List<LegendItem> currentRow = new ArrayList<>();
        int currentRowWidth = 0;

        for (LegendItem item : items) {
            int neededWidth = (currentRow.isEmpty() ? 0 : itemSpacing) + item.itemWidth;
            if (!currentRow.isEmpty() && currentRowWidth + neededWidth > panelWidth) {
                rows.add(currentRow);
                currentRow = new ArrayList<>();
                currentRowWidth = 0;
            }
            currentRow.add(item);
            currentRowWidth += neededWidth;
        }
        if (!currentRow.isEmpty()) {
            rows.add(currentRow);
        }
        return rows;
    }

    private void drawPieChart(Graphics2D g2d, int minDimension) {
        // Grab the panel’s current width & height
        int width  = getWidth();

        // Where do we place the chart?
        // Here, we place it horizontally centered and at the top:
        int chartX = (width - minDimension) / 2;
        int chartY = 0; // top

        // Calculate the total of all slice values
        double total = slices.stream()
                .mapToDouble(Slice::value)
                .sum();

        // We'll keep track of the "startAngle" for each slice
        double currentValue = 0.0;

        // Some padding around the chart to avoid drawing to the very edge
        int chartPadding = 5;

        // Decide how thick your "gap" stroke should be
        // (e.g. 3px wide stroke, which also provides a rounded boundary)
        float gapStrokeWidth = 3f;

        // Draw each slice of the pie
        for (Slice slice : slices) {
            // Calculate the angular span for this slice
            double startAngle = currentValue * 360 / total;
            double arcAngle   = slice.value() * 360 / total;

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

            // Fill the arc with the slice's color
            g2d.setColor(slice.color());
            g2d.fill(arc);

            // Immediately draw a thick stroke in the background color
            // to create a gap between adjacent slices
            g2d.setColor(getBackground());
            g2d.setStroke(
                    new BasicStroke(
                            gapStrokeWidth,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );
            g2d.draw(arc);

            // Advance the starting angle for the next slice
            currentValue += slice.value();
        }
    }

    private void drawLegendWithWrapping(Graphics2D g2d, LegendLayout layout, int panelWidth, int legendTop) {
        // We already know how to measure each row, so we just paint them:
        int squareSize   = 10;
        int itemSpacing  = 20;
        int labelPadding = 5;
        int valuePadding = 5;

        FontMetrics fm = g2d.getFontMetrics();

        int y = legendTop + 10; // some top padding
        for (List<LegendItem> row : layout.rows) {
            // Calculate this row's width so we can center it
            int rowWidth = 0;
            for (int i = 0; i < row.size(); i++) {
                if (i > 0) {
                    rowWidth += itemSpacing;
                }
                rowWidth += row.get(i).itemWidth;
            }

            int x = (panelWidth - rowWidth) / 2;  // center horizontally

            // Determine this row's max height
            int rowMaxHeight = row.stream()
                    .mapToInt(li -> li.itemHeight)
                    .max()
                    .orElse(fm.getHeight());

            // Paint each LegendItem
            for (int i = 0; i < row.size(); i++) {
                if (i > 0) {
                    x += itemSpacing;
                }

                LegendItem li = row.get(i);
                // Circle indicator
                int shapeY = y + (li.itemHeight - squareSize) / 2;
                g2d.setColor(li.slice.color());
                g2d.fillOval(x, shapeY, squareSize, squareSize);

                // Label
                g2d.setColor(getForeground());
                int labelX = x + squareSize + labelPadding;
                int textBaseline = y + (li.itemHeight - fm.getHeight())/2 + fm.getAscent();
                g2d.drawString(li.slice.label(), labelX, textBaseline);

                // Value
                g2d.setColor(UIManager.getColor("Label.disabledForeground"));
                int valueX = labelX + li.labelWidth + valuePadding;
                g2d.drawString(String.valueOf((int) li.slice.value()), valueX, textBaseline);

                x += li.itemWidth;
            }

            // Move down for the next row
            y += rowMaxHeight + 5;
        }
    }

    private record LegendItem(Slice slice, int labelWidth, int valueWidth, int itemWidth, int itemHeight) {
    }

    public record Slice(double value, Color color, String label) {
    }
}