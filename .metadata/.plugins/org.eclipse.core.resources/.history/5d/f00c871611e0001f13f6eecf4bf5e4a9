package canvas;

import java.awt.*;
import java.util.ArrayList;

public class Wire {
    private ArrayList<Point> points;               // Points used for drawing
    private JunctionPoint startJunction;           // Starting JunctionPoint
    private JunctionPoint endJunction;             // Ending JunctionPoint
    private ArrayList<Point> userDefinedPoints;    // Points added by the user during drawing
    private Canvas canvas;                         // Reference to the canvas for accessing JunctionBoxes

    public Wire(JunctionPoint startJunction, Canvas canvas) {
        this.startJunction = startJunction;
        this.canvas = canvas;
        points = new ArrayList<>();
        userDefinedPoints = new ArrayList<>();
        // Initialize with the starting point
        points.add(new Point(startJunction.getX(), startJunction.getY()));
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void updateLastPoint(Point point) {
        if (!points.isEmpty()) {
            points.set(points.size() - 1, point);
            recalculatePath();
        }
    }

    public void addUserDefinedPoint(Point point) {
        // Fix the last point to the current position before adding a new point
        updateLastPoint(point);
        userDefinedPoints.add(point);
        // Add a placeholder for the next point
        points.add(point);
    }

    public void setEndJunction(JunctionPoint endJunction) {
        this.endJunction = endJunction;
        updateLastPoint(new Point(endJunction.getX(), endJunction.getY()));
        recalculatePath();
    }

    public void recalculatePath() {
        points.clear();
        Point startPoint = new Point(startJunction.getX(), startJunction.getY());
        Point previousPoint = startPoint;
        points.add(startPoint);

        for (Point userPoint : userDefinedPoints) {
            // Route from previousPoint to userPoint
            ArrayList<Point> segment = routeAroundObstacles(previousPoint, userPoint);
            // Remove the starting point to avoid duplication
            if (!segment.isEmpty() && segment.get(0).equals(previousPoint)) {
                segment.remove(0);
            }
            points.addAll(segment);
            previousPoint = userPoint;
        }

        if (endJunction != null) {
            Point endPoint = new Point(endJunction.getX(), endJunction.getY());
            // Route from previousPoint to endPoint
            ArrayList<Point> segment = routeAroundObstacles(previousPoint, endPoint);
            // Remove the starting point to avoid duplication
            if (!segment.isEmpty() && segment.get(0).equals(previousPoint)) {
                segment.remove(0);
            }
            points.addAll(segment);
        } else {
            // Placeholder for current mouse position
            points.add(new Point(previousPoint.x, previousPoint.y));
        }
    }

    private ArrayList<Point> routeAroundObstacles(Point start, Point end) {
        ArrayList<Point> path = new ArrayList<>();
        path.add(start);
        Point currentPoint = start;

        // Attempt horizontal then vertical path
        Point nextPointH = new Point(end.x, start.y);
        if (!doesSegmentIntersectObstacles(currentPoint, nextPointH)) {
            path.add(nextPointH);
            if (!doesSegmentIntersectObstacles(nextPointH, end)) {
                path.add(end);
                return path;
            }
        }

        // Attempt vertical then horizontal path
        Point nextPointV = new Point(start.x, end.y);
        if (!doesSegmentIntersectObstacles(currentPoint, nextPointV)) {
            path.add(nextPointV);
            if (!doesSegmentIntersectObstacles(nextPointV, end)) {
                path.add(end);
                return path;
            }
        }

        // If both fail, create a detour
        int detourOffset = 20; // Arbitrary offset for detour
        // Determine detour direction based on obstacle positions

        // For simplicity, we'll shift along Y-axis first, then X-axis
        Point detour1 = new Point(currentPoint.x, currentPoint.y + detourOffset);
        Point detour2 = new Point(end.x, currentPoint.y + detourOffset);

        // Check for obstacles along the detour path
        if (!doesSegmentIntersectObstacles(currentPoint, detour1)
                && !doesSegmentIntersectObstacles(detour1, detour2)
                && !doesSegmentIntersectObstacles(detour2, end)) {
            path.add(detour1);
            path.add(detour2);
            path.add(end);
            return path;
        }

        // As a last resort, add a larger detour
        detourOffset += 20;
        detour1 = new Point(currentPoint.x + detourOffset, currentPoint.y);
        detour2 = new Point(currentPoint.x + detourOffset, end.y);

        if (!doesSegmentIntersectObstacles(currentPoint, detour1)
                && !doesSegmentIntersectObstacles(detour1, detour2)
                && !doesSegmentIntersectObstacles(detour2, end)) {
            path.add(detour1);
            path.add(detour2);
            path.add(end);
            return path;
        }

        // If no valid path found, default to direct line (may intersect obstacles)
        path.clear();
        path.add(start);
        path.add(end);
        return path;
    }

    private boolean doesSegmentIntersectObstacles(Point p1, Point p2) {
        for (JunctionBox box : canvas.getJunctionBoxes()) {
            // Ignore own starting and ending boxes
            if (box == startJunction.getParentBox() || (endJunction != null && box == endJunction.getParentBox())) {
                continue;
            }
            Rectangle boxBounds = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
            if (lineIntersectsRectangle(p1, p2, boxBounds)) {
                return true;
            }
        }
        return false;
    }

    private boolean lineIntersectsRectangle(Point p1, Point p2, Rectangle rect) {
        return rect.intersectsLine(p1.x, p1.y, p2.x, p2.y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        Point prevPoint = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Point currPoint = points.get(i);
            g.drawLine(prevPoint.x, prevPoint.y, currPoint.x, currPoint.y);
            prevPoint = currPoint;
        }
    }

    // Getter methods...
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
