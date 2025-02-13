/***********************************************************************
 * Module:  Decorator.java
 * Author:  maril
 * Purpose: Defines the Class Decorator
 ***********************************************************************/

package canvas;

import java.awt.Point;

public class Decorator extends Element {
	public Decorator(Point point, Point endPoint, int width, int height) {
		super(point, endPoint, width, height);
	}

	public Decorator() {
		super();
		this.type = "Decorator";
	}

	public java.util.Collection<Element> elementDecorator;


}