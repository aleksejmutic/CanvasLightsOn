package canvas;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class PathFinder {
    private static final int OFFSET = 10;

    public static ArrayList<Point> findPath(Point start, Point end, Canvas canvas) {
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> cameFrom = new HashMap<>();
        Set<Point> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(end)) {
                // Reconstruct the path
                return reconstructPath(cameFrom, start, end);
            }

            for (Point neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor) && !doesSegmentIntersectObstacles(current, neighbor, canvas)) {
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // If no path found, return a direct path (may intersect obstacles)
        ArrayList<Point> directPath = new ArrayList<>();
        directPath.add(start);
        directPath.add(end);
        return directPath;
    }

    private static ArrayList<Point> getNeighbors(Point point) {
        int stepSize = OFFSET;
        ArrayList<Point> neighbors = new ArrayList<>();
        neighbors.add(new Point(point.x + stepSize, point.y));     // Right
        neighbors.add(new Point(point.x - stepSize, point.y));     // Left
        neighbors.add(new Point(point.x, point.y + stepSize));     // Down
        neighbors.add(new Point(point.x, point.y - stepSize));     // Up
        return neighbors;
    }

    private static boolean doesSegmentIntersectObstacles(Point p1, Point p2, Canvas canvas) {
        Line2D line = new Line2D.Double(p1, p2);
        for (JunctionBox box : canvas.getJunctionBoxes()) {
            Rectangle2D boxBounds = new Rectangle2D.Double(box.getX(), box.getY(), box.getWidth(), box.getHeight());
            Rectangle2D inflatedBounds = new Rectangle2D.Double(
                boxBounds.getX() - OFFSET,
                boxBounds.getY() - OFFSET,
                boxBounds.getWidth() + 2 * OFFSET,
                boxBounds.getHeight() + 2 * OFFSET
            );
            if (inflatedBounds.contains(p1) || inflatedBounds.contains(p2) || line.intersects(inflatedBounds)) {
                return true;
            }
        }
        return false;
    }

    private static ArrayList<Point> reconstructPath(Map<Point, Point> cameFrom, Point start, Point end) {
        ArrayList<Point> path = new ArrayList<>();
        Point current = end;
        while (!current.equals(start)) {
            path.add(0, current);
            current = cameFrom.get(current);
        }
        path.add(0, start);
        return path;
    }
}
