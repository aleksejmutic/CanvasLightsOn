package canvas;

import java.awt.*;
import java.awt.event.*;

public class JunctionBoxMouseListener extends MouseAdapter {
    private DiagramCanvas canvas;
    private boolean isDrawing = false;
    private JunctionBox selectedJunctionBox = null;
    private SelectDecorator selectionDecorator = null;

    public JunctionBoxMouseListener(DiagramCanvas canvas) {
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
        if (e.getButton() == MouseEvent.BUTTON1) {
            Point clickPoint = e.getPoint();

            // Check if a JunctionBox was clicked
            JunctionBox clickedBox = getClickedJunctionBox(clickPoint);

            if (clickedBox != null) {
                toggleSelection(clickedBox);
            } else if (isDrawing) {
                drawNewJunctionBox(clickPoint);
            }
        }
    }

    private void drawNewJunctionBox(Point clickPoint) {
        int boxWidth = 100;
        int boxHeight = 50;

        if (isOverlappingExistingJunctionBox(clickPoint.x, clickPoint.y, boxWidth, boxHeight)) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            JunctionBox junctionBox = new JunctionBox(clickPoint, clickPoint, boxWidth, boxHeight);
            canvas.addJunctionBox(junctionBox);
            canvas.repaint();
        }
    }

    
    //Draws the decorated object, after it has been clicked on
    private void toggleSelection(JunctionBox clickedBox) {
        if (selectedJunctionBox == clickedBox) {
            canvas.removeDecorator(selectionDecorator);
            selectedJunctionBox = null;
            selectionDecorator = null;
        } else {
            selectedJunctionBox = clickedBox;
            selectionDecorator = new SelectDecorator(
                clickedBox.getPoint(),
                clickedBox.getEndPoint(),
                clickedBox.getWidth(),
                clickedBox.getHeight(),
                clickedBox
            );
            canvas.addDecorator(selectionDecorator);
        }
        canvas.repaint();
    }

    private JunctionBox getClickedJunctionBox(Point point) {
        for (JunctionBox box : canvas.getJunctionBoxes()) {
            Rectangle bounds = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
            if (bounds.contains(point)) {
                return box;
            }
        }
        return null;
    }

    private boolean isOverlappingExistingJunctionBox(int x, int y, int width, int height) {
        Rectangle newBoxBounds = new Rectangle(x, y, width, height);
        for (JunctionBox box : canvas.getJunctionBoxes()) {
            Rectangle existingBoxBounds = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
            if (newBoxBounds.intersects(existingBoxBounds)) {
                return true;
            }
        }
        return false;
    }
}
