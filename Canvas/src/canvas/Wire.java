/***********************************************************************
 * Module:  Wire.java
 * Author:  maril (updated)
 * Purpose: Defines the Wire as a leaf Conductor with drawing and routing logic.
 ***********************************************************************/
package canvas;

import java.awt.*;
import java.util.ArrayList;

public class Wire extends Conductor {
    // Polyline points that define this wire.
    private ArrayList<Point> points;
    // User-defined intermediate points.
    private ArrayList<Point> userDefinedPoints;
    // Starting and ending junctions.
    private JunctionPoint startJunction;
    private JunctionPoint endJunction;
    // A reference to the canvas for obstacle checking (assumes canvas provides getJunctionBoxes()).
    private DiagramCanvas canvas;
    
    private static int id = 0;
  
    
    private String name = "Wire ";

    /**
     * Constructs a new Wire given a starting JunctionPoint and a Canvas.
     * (This constructor is used during interactive drawing.)
     */
    public Wire(JunctionPoint startJunction, DiagramCanvas canvas) {
        // Initialize the inherited Element fields with the starting point.
        super(new Point(startJunction.getX(), startJunction.getY()),
              new Point(startJunction.getX(), startJunction.getY()),
              0, 0);
        id++;
        this.name += this.id;
        System.out.println(toString());
        this.startJunction = startJunction;
        this.canvas = canvas;
        this.points = new ArrayList<>();
        this.userDefinedPoints = new ArrayList<>();
        // Begin with the starting point.
        points.add(new Point(startJunction.getX(), startJunction.getY()));
        updateBoundingBox();
    }

    /**
     * (For backward compatibility) Constructs a Wire given explicit parameters.
     */
    public Wire(Point point, Point endPoint, int width, int height) {
        super(point, endPoint, width, height);
        id++;
        System.out.println(toString());
        this.points = new ArrayList<>();
        this.userDefinedPoints = new ArrayList<>();
        points.add(point);
        updateBoundingBox();
    }

    // --------------------------
    // Methods for interactive editing
    // --------------------------
    
    public void addPoint(Point point) {
        points.add(point);
        recalculatePath();
    }
    
    
    //desava se replacement, vidjeti da li to uopste treba da se desava!!!
    public void updateLastPoint(Point point) {
        if (!points.isEmpty()) {
            points.set(points.size() - 1, point);
            recalculatePath();
        }
    }
    
    public void addUserDefinedPoint(Point point) {
        // Fix the current segment, add the user-defined point,
        // and insert a placeholder for the next segment.
        updateLastPoint(point);
        userDefinedPoints.add(point);
        points.add(point);
        recalculatePath();
    }
    
    public void setEndJunction(JunctionPoint endJunction) {
        this.endJunction = endJunction;
        updateLastPoint(new Point(endJunction.getX(), endJunction.getY()));
        recalculatePath();
    }
    
    /**
     * Recalculates the polyline that defines this wire by connecting:
     * startJunction -> each userDefinedPoint -> endJunction (if set)
     * (Routing around obstacles is attempted.)
     */
    public void recalculatePath() {
        points.clear();
        Point startPoint = new Point(startJunction.getX(), startJunction.getY());
        Point previousPoint = startPoint;
        points.add(startPoint);
        
        for (Point userPoint : userDefinedPoints) {
            ArrayList<Point> segment = routeAroundObstacles(previousPoint, userPoint);
            // Remove duplicate starting point.
            if (!segment.isEmpty() && segment.get(0).equals(previousPoint))
                segment.remove(0);
            points.addAll(segment);
            previousPoint = userPoint;
        }
        
        if (endJunction != null) {
            Point endPoint = new Point(endJunction.getX(), endJunction.getY());
            ArrayList<Point> segment = routeAroundObstacles(previousPoint, endPoint);
            if (!segment.isEmpty() && segment.get(0).equals(previousPoint))
                segment.remove(0);
            points.addAll(segment);
        } else {
            // Placeholder (e.g., when the mouse is moving).
            points.add(new Point(previousPoint.x, previousPoint.y));
        }
        updateBoundingBox();
    }
    
    /**
     * Updates the bounding box (i.e. the inherited point, width, height, and endPoint)
     * from the current set of points.
     */
    private void updateBoundingBox() {
        if (points.isEmpty())
            return;
        int minX = points.get(0).x, minY = points.get(0).y;
        int maxX = points.get(0).x, maxY = points.get(0).y;
        for (Point p : points) {
            if (p.x < minX)
                minX = p.x;
            if (p.y < minY)
                minY = p.y;
            if (p.x > maxX)
                maxX = p.x;
            if (p.y > maxY)
                maxY = p.y;
        }
        this.point = new Point(minX, minY);
        this.width = maxX - minX;
        this.height = maxY - minY;
        this.endPoint = points.get(points.size() - 1);
    }
    
    /**
     * Attempts to compute a route between two points that avoids obstacles.
     * Tries horizontal–then–vertical and vertical–then–horizontal paths,
     * then falls back to simple detours.
     */
    private ArrayList<Point> routeAroundObstacles(Point start, Point end) {
        ArrayList<Point> path = new ArrayList<>();
        path.add(start);
        Point currentPoint = start;
        
        // Try horizontal then vertical.
        Point nextPointH = new Point(end.x, start.y);
        if (!doesSegmentIntersectObstacles(currentPoint, nextPointH)) {
            path.add(nextPointH);
            if (!doesSegmentIntersectObstacles(nextPointH, end)) {
                path.add(end);
                return path;
            }
        }
        
        // Try vertical then horizontal.
        Point nextPointV = new Point(start.x, end.y);
        if (!doesSegmentIntersectObstacles(currentPoint, nextPointV)) {
            path.add(nextPointV);
            if (!doesSegmentIntersectObstacles(nextPointV, end)) {
                path.add(end);
                return path;
            }
        }
        
        // Try a simple detour.
        int detourOffset = 20;
        Point detour1 = new Point(currentPoint.x, currentPoint.y + detourOffset);
        Point detour2 = new Point(end.x, currentPoint.y + detourOffset);
        if (!doesSegmentIntersectObstacles(currentPoint, detour1) &&
            !doesSegmentIntersectObstacles(detour1, detour2) &&
            !doesSegmentIntersectObstacles(detour2, end)) {
            path.add(detour1);
            path.add(detour2);
            path.add(end);
            return path;
        }
        
        // Last resort: larger detour.
        detourOffset += 20;
        detour1 = new Point(currentPoint.x + detourOffset, currentPoint.y);
        detour2 = new Point(currentPoint.x + detourOffset, end.y);
        if (!doesSegmentIntersectObstacles(currentPoint, detour1) &&
            !doesSegmentIntersectObstacles(detour1, detour2) &&
            !doesSegmentIntersectObstacles(detour2, end)) {
            path.add(detour1);
            path.add(detour2);
            path.add(end);
            return path;
        }
        
        // Default: direct path.
        path.clear();
        path.add(start);
        path.add(end);
        return path;
    }
    
    /**
     * Checks whether a line segment between p1 and p2 intersects any obstacles.
     * Obstacles are derived from the bounding boxes of JunctionBoxes on the canvas.
     */
    private boolean doesSegmentIntersectObstacles(Point p1, Point p2) {
        for (JunctionBox box : canvas.getJunctionBoxes()) {
            // Skip the boxes associated with this wire’s start and end junctions.
            if (box == startJunction.getParentBox() ||
                (endJunction != null && box == endJunction.getParentBox()))
                continue;
            Rectangle rect = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
            if (lineIntersectsRectangle(p1, p2, rect))
                return true;
        }
        return false;
    }
    
    private boolean lineIntersectsRectangle(Point p1, Point p2, Rectangle rect) {
        return rect.intersectsLine(p1.x, p1.y, p2.x, p2.y);
    }
    
    /**
     * Draws this Wire by connecting each of its points.
     */
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        if (points.size() > 1) {
            Point prev = points.get(0);
            for (int i = 1; i < points.size(); i++) {
                Point curr = points.get(i);
                //prikazivanje tacaka!!!
                System.out.println(curr.x + " " + curr.y);
                g.drawLine(prev.x, prev.y, curr.x, curr.y);
                prev = curr;
            }
        }
    }
    
    @Override
    public String toString() {
		return name;
    	
    }
    
    // --- Getter Methods ---
    
    public JunctionPoint getStartJunction() {
        return startJunction;
    }
    
    public JunctionPoint getEndJunction() {
        return endJunction;
    }
    
    public ArrayList<Point> getUserDefinedPoints() {
        return userDefinedPoints;
    }
    
    public ArrayList<Point> getPoints() {
        return points;
    }
}
