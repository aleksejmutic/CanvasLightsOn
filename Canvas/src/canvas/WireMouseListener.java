package canvas;

import java.awt.*;
import java.awt.event.*;

public class WireMouseListener extends MouseAdapter implements MouseMotionListener {
	private DiagramCanvas canvas;
	private boolean isDrawingMode = false;
	private boolean isDrawingWire = false;
	private Wire currentWire = null;
	private JunctionPoint firstJunction = null;

	public WireMouseListener(DiagramCanvas canvas) {
		this.canvas = canvas;
	}

	public void setDrawingMode(boolean drawingMode) {
		this.isDrawingMode = drawingMode;
		if (!isDrawingMode) {
			resetState();
			canvas.resetCursor();
		} else {
			canvas.setDrawingCursor();
		}
	}

	public boolean isDrawingMode() {
		return isDrawingMode;
	}

	public boolean isDrawingWire() {
		return isDrawingWire;
	}

	public Wire getCurrentWire() {
		return currentWire;
	}

	private void resetState() {
		isDrawingWire = false;
		currentWire = null;
		firstJunction = null;
		canvas.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!isDrawingMode || e.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		//dio koda kada se kreira point kada se Click uradi lijevim misem!!!
		Point clickPoint = e.getPoint();
		JunctionPoint clickedJunctionPoint = getClickedJunctionPoint(clickPoint);

		if (!isDrawingWire) {
			if (clickedJunctionPoint != null) {
				isDrawingWire = true;
				firstJunction = clickedJunctionPoint;
				currentWire = new Wire(firstJunction, canvas); // Pass canvas reference
				currentWire.addPoint(new Point(firstJunction.getX(), firstJunction.getY()));
			}
		} else {
			JunctionBox clickedJunctionBox = clickedJunctionPoint != null ? clickedJunctionPoint.getParentBox() : null;

			if (clickedJunctionBox != null && clickedJunctionPoint == null) {
				resetState();
			} else if (clickedJunctionPoint != null) {
				JunctionBox endingJunctionBox = clickedJunctionPoint.getParentBox();
				if (endingJunctionBox != firstJunction.getParentBox()) {
					currentWire.setEndJunction(clickedJunctionPoint);
					//dio koda koji ja mislim jedini treba mozda mijenjati, dobro vidjeti sta se desava!!!
					canvas.addWire(currentWire);
					resetState();
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			} else {
				//dio koda gdje se dodaje novi point koji definise korisnik, on ga i crta, JAKO BITNO!!!
				currentWire.addUserDefinedPoint(clickPoint);
			}
			canvas.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (isDrawingWire && currentWire != null) {
			Point currentPoint = e.getPoint();
			currentWire.updateLastPoint(currentPoint);
			canvas.repaint();
		}
	}

	// Samo vraca junctionPoint koji se nalazi na JunctionBox-u, popraviti!!!
	private JunctionPoint getClickedJunctionPoint(Point point) {
		for (JunctionBox junctionBox : canvas.getJunctionBoxes()) {
			for (JunctionPoint junctionPoint : junctionBox.getJunctionPoints()) {
				if (junctionPoint.contains(point)) {
					return junctionPoint;
				}
			}
		}
		return null;
	}
}
