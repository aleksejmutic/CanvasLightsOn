package canvas;

import java.awt.*;
import java.awt.event.*;

public class JunctionBoxMouseListener extends MouseAdapter {
    private Canvas canvas;
    private boolean isDrawing = false;

    // Image path within the src folder
    private static final String IMAGE_PATH = "/images/flash.png"; // Adjust to your image's path

    // Variables to store click coordinates
    private int clickX;
    private int clickY;

    public JunctionBoxMouseListener(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setDrawing(boolean drawing) {
        this.isDrawing = drawing;
    }

    public boolean isDrawing() {
        return isDrawing;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isDrawing && e.getButton() == MouseEvent.BUTTON1) {
            // Capture click coordinates
            clickX = e.getX();
            clickY = e.getY();

            int boxWidth = 100; // Width of the JunctionBox
            int boxHeight = 50; // Height of the JunctionBox

            // Check for overlap
            if (isOverlappingExistingJunctionBox(clickX, clickY, boxWidth, boxHeight)) {
                Toolkit.getDefaultToolkit().beep();
            } else {
                // Create the JunctionBox with the resource image path
                JunctionBox junctionBox = new JunctionBox(clickX, clickY, boxWidth, boxHeight, IMAGE_PATH);
                canvas.addJunctionBox(junctionBox);

                // Send the coordinates to the model (pseudo-code, replace with actual model call)
                // model.addJunctionBox(clickX, clickY);
            }
            // Keep drawing mode active
        }
    }

    // Method to check for overlap with existing JunctionBoxes
    private boolean isOverlappingExistingJunctionBox(int x, int y, int width, int height) {
        Rectangle newBoxBounds = new Rectangle(x, y, width, height);
        for (JunctionBox junctionBox : canvas.getJunctionBoxes()) {
            Rectangle existingBoxBounds = new Rectangle(
                junctionBox.getX(), junctionBox.getY(),
                junctionBox.getWidth(), junctionBox.getHeight()
            );
            if (newBoxBounds.intersects(existingBoxBounds)) {
                return true;
            }
        }
        return false;
    }

    // Getter methods for click coordinates
    public int getClickX() {
        return clickX;
    }

    public int getClickY() {
        return clickY;
    }
}
