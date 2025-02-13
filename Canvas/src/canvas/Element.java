package canvas;

import java.awt.Point;

public abstract class Element {
    public String name;
    public Point point;
    public Point endPoint;
    public int width;
    public int height;
    public boolean isSelected;
    public String type;

    public Element() {
        // Default constructor
    }

    public Element(Point point, Point endPoint, int width, int height) {
        this.point = point;
        this.endPoint = endPoint;
        this.width = width;
        this.height = height;
    }

    public Point getPoint() {
        return point;
    }

    /** Sets the top-left point of the element. */
    public void setPoint(Point newPoint) {
        this.point = newPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    /** Sets the end point of the element. */
    public void setEndPoint(Point newEndPoint) {
        this.endPoint = newEndPoint;
    }

    public int getWidth() {
        return width;
    }

    /** Sets the element's width. */
    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    public int getHeight() {
        return height;
    }

    /** Sets the element's height. */
    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    /** Sets the element's selection state. */
    public void setIsSelected(boolean newIsSelected) {
        this.isSelected = newIsSelected;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
