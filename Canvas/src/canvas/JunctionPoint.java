package canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class JunctionPoint extends Element {
    private static final int DIAMETER = 10;  // The size of the junction point
    private JunctionBox parentBox;          // The JunctionBox that owns this point
    private int id;                         // Unique identifier

    // Unique ID generation
    private static int nextId = 0;
    private static synchronized int generateUniqueId() {
        return nextId++;
    }

    /**
     * Constructs a JunctionPoint using the provided center coordinates.
     * 
     * Because Element expects its point to be the top‐left corner of the element,
     * we compute that as (centerX - DIAMETER/2, centerY - DIAMETER/2) and set the width/height accordingly.
     *
     * @param centerX the x-coordinate of the junction point’s center
     * @param centerY the y-coordinate of the junction point’s center
     */
    public JunctionPoint(int centerX, int centerY) {
        // Calculate the top-left corner so that the center is at (centerX, centerY).
        super(new Point(centerX - DIAMETER/2, centerY - DIAMETER/2),
              new Point(centerX - DIAMETER/2 + DIAMETER, centerY - DIAMETER/2 + DIAMETER),
              DIAMETER, DIAMETER);
        this.id = generateUniqueId();
    }

    /**
     * Sets the parent JunctionBox for this junction point.
     */
    public void setParentBox(JunctionBox box) {
        this.parentBox = box;
    }

    /**
     * Returns the parent JunctionBox.
     */
    public JunctionBox getParentBox() {
        return parentBox;
    }

    /**
     * Returns true if the provided point lies within the junction point’s circular area.
     *
     * @param p the point to test
     * @return true if p is within DIAMETER/2 of the center; false otherwise.
     */
    public boolean contains(Point p) {
        // Compute the center of the junction point.
        int centerX = this.point.x + DIAMETER / 2;
        int centerY = this.point.y + DIAMETER / 2;
        return p.distance(centerX, centerY) <= DIAMETER / 2;
    }

    /**
     * Draws the JunctionPoint as a filled oval.
     *
     * @param g the Graphics context
     */
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        // Draw the oval using the inherited top-left (point) and fixed DIAMETER.
        g.fillOval(point.x, point.y, DIAMETER, DIAMETER);
    }

    /**
     * Returns the x-coordinate of the junction point’s center.
     */
    public int getX() {
        return point.x + DIAMETER / 2;
    }

    /**
     * Returns the y-coordinate of the junction point’s center.
     */
    public int getY() {
        return point.y + DIAMETER / 2;
    }

    /**
     * Returns the unique identifier.
     */
    public int getId() {
        return id;
    }
}
