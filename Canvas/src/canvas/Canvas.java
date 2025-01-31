package canvas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JPanel {
    private ArrayList<JunctionBox> junctionBoxes;
    private ArrayList<Wire> wires;
    private WireMouseListener wireMouseListener;

    public Canvas() {
        setPreferredSize(new Dimension(800, 500));
        setBackground(Color.WHITE);
        junctionBoxes = new ArrayList<>();
        wires = new ArrayList<>();
        setFocusable(true);
    }

    public void addJunctionBox(JunctionBox junctionBox) {
        junctionBoxes.add(junctionBox);
        repaint();
    }

    public void addWire(Wire wire) {
        wires.add(wire);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (JunctionBox junctionBox : junctionBoxes) {
            junctionBox.draw(g);
        }

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
    }
}
