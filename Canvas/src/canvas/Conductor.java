package canvas;

import java.awt.Point;

public abstract class Conductor extends Element {

	public Conductor(Point point, Point endPoint, int width, int height) {
        super(point, endPoint, width, height);
        
        this.type = type;
    }

    public Conductor() {
        super();
    }
}
