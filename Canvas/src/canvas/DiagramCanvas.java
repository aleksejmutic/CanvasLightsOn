package canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DiagramCanvas extends JPanel {
    private ArrayList<JunctionBox> junctionBoxes;
    private ArrayList<Wire> wires;
    private ArrayList<SelectDecorator> selectionDecorators; // Specifically handle SelectionDecorators
    private WireMouseListener wireMouseListener;
    private JunctionBoxContextMenu contextMenu;
    private DiagramCanvasContextMenu diagramContextMenu;

    // Zoom state and scale levels
    private float currentZoom = 1.0f;
    private final float[] zoomLevels = {0.25f, 0.33f, 0.50f, 0.60f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f, 4.0f};

    private boolean gridVisible = true; // Track if the grid is visible

    public DiagramCanvas() {
        setPreferredSize(new Dimension(800, 500)); // Initial preferred size
        setBackground(Color.WHITE);
        junctionBoxes = new ArrayList<>();
        wires = new ArrayList<>();
        selectionDecorators = new ArrayList<>(); // Initialize selectionDecorators list
        setFocusable(true);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showContextMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showContextMenu(e);
                }
            }
        });
    }

    public void addJunctionBox(JunctionBox junctionBox) {
        junctionBoxes.add(junctionBox);
        repaint();
    }

    public void addWire(Wire wire) {
        wires.add(wire);
        repaint();
    }

    public void addDecorator(SelectDecorator decorator) {
        selectionDecorators.add(decorator); // Add the decorator to the list
        repaint();
    }

    public void removeDecorator(SelectDecorator decorator) {
        selectionDecorators.remove(decorator); // Remove the decorator from the list
        repaint();
    }

    public void setWireMouseListener(WireMouseListener listener) {
        this.wireMouseListener = listener;
    }

    public void setDrawingCursor() {
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    public void resetCursor() {
        setCursor(Cursor.getDefaultCursor());
    }

    public ArrayList<JunctionBox> getJunctionBoxes() {
        return junctionBoxes;
    }

    public ArrayList<Wire> getWires() {
        return wires;
    }

    // Zoom methods
    public void zoomIn() {
        // Increase zoom level
        int index = findCurrentZoomIndex();
        if (index < zoomLevels.length - 1) {
            currentZoom = zoomLevels[index + 1];
            updatePreferredSize();
            repaint();
        }
    }

    public void zoomOut() {
        // Decrease zoom level
        int index = findCurrentZoomIndex();
        if (index > 0) {
            currentZoom = zoomLevels[index - 1];
            updatePreferredSize();
            repaint();
        }
    }

    // Find the current zoom level index
    private int findCurrentZoomIndex() {
        for (int i = 0; i < zoomLevels.length; i++) {
            if (currentZoom == zoomLevels[i]) {
                return i;
            }
        }
        return 5; // Default to 100% (index 5)
    }

    // Update the preferred size based on current zoom level
    private void updatePreferredSize() {
        // Update the preferred size based on the current zoom level
        int width = (int) (800 * currentZoom); // Base width (800) scaled by zoom factor
        int height = (int) (500 * currentZoom); // Base height (500) scaled by zoom factor
        setPreferredSize(new Dimension(width, height)); // Set new preferred size

        // Revalidate the layout to notify the parent container about the size change
        revalidate();
    }

    public void toggleGrid() {
        gridVisible = !gridVisible; // Toggle the grid visibility
        repaint(); // Repaint to update the canvas
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Scale the graphics based on currentZoom
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(currentZoom, currentZoom);

        // If the grid is visible, draw it
        if (gridVisible) {
            drawGrid(g2d);
        }

        // Draw JunctionBoxes
        for (JunctionBox junctionBox : junctionBoxes) {
            junctionBox.draw(g);
        }

        // Draw Wires
        for (Wire wire : wires) {
            wire.draw(g);
        }

        // Draw the wire currently being drawn
        if (wireMouseListener != null && wireMouseListener.isDrawingWire()) {
            Wire currentWire = wireMouseListener.getCurrentWire();
            if (currentWire != null) {
                currentWire.draw(g);
            }
        }

        // Draw SelectionDecorators
        for (SelectDecorator decorator : selectionDecorators) {
            decorator.draw(g); // Directly call draw on SelectionDecorator
        }
    }

    // Method to draw the grid
    private void drawGrid(Graphics2D g2d) {
        int gridSize = 20;
        int dotSize = 3;  // Increase the dot size here
        g2d.setColor(Color.GRAY);
        for (int x = 0; x < getWidth(); x += gridSize) {
            for (int y = 0; y < getHeight(); y += gridSize) {
                g2d.fillRect(x - dotSize / 2, y - dotSize / 2, dotSize, dotSize); // Draw larger dots
            }
        }
    }
    
    // Show the context menu
    private void showContextMenu(MouseEvent e) {
        // Check if right-click is inside any JunctionBox
        for (JunctionBox junctionBox : junctionBoxes) {
            if (junctionBox.getBounds().contains(e.getPoint())) {
                // If right-click is inside a JunctionBox, show the context menu for the JunctionBox
                contextMenu = new JunctionBoxContextMenu(junctionBox); // Assuming contextMenu is a class that handles JunctionBox actions
                contextMenu.show(e.getComponent(), e.getX(), e.getY());
                return;  // Exit method after showing context menu for JunctionBox
            }
        }
        
        // If right-click is not inside any JunctionBox, show the general canvas context menu
        diagramContextMenu = new DiagramCanvasContextMenu(this); // Assuming this handles the general actions for the canvas
        diagramContextMenu.show(e.getComponent(), e.getX(), e.getY());
    }


}
