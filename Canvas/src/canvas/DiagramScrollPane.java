package canvas;

import javax.swing.*;
import java.awt.*;

public class DiagramScrollPane extends JScrollPane {
    private DiagramCanvas canvas;

    public DiagramScrollPane() {
        // Create the Canvas instance
        canvas = new DiagramCanvas();
        
        // Set the Canvas as the view of the JScrollPane
        setViewportView(canvas);

        // Optional: Set preferred size for the entire diagram pane
        setPreferredSize(new Dimension(800, 500));

        // Set scroll bar policies
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Customizing the scrollbars
        customizeScrollBars();
    }

    private void customizeScrollBars() {
        // Increase the size of the horizontal scrollbar
        JScrollBar horizontalBar = getHorizontalScrollBar();
        horizontalBar.setPreferredSize(new Dimension(20, 20)); // Increased width

        // Increase the size of the vertical scrollbar
        JScrollBar verticalBar = getVerticalScrollBar();
        verticalBar.setPreferredSize(new Dimension(20, 20)); // Increased height
    }

    // You can still access Canvas methods directly from here if needed
    public DiagramCanvas getDiagramCanvas() {
        return canvas;
    }

    public void setDiagramCanvas(DiagramCanvas canvas) {
        this.canvas = canvas;
    }
}
