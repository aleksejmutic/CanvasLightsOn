package canvas;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
	private DiagramTabbedPane diagramTabbedPane = new DiagramTabbedPane();
	// private Canvas canvas;
	private JunctionBoxMouseListener junctionBoxMouseListener;
	private WireMouseListener wireMouseListener;
	private ButtonsPanel buttonsPanel;

	public MainFrame() {
		setTitle("Circuit Editor");
		setSize(800, 600);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// canvas = new Canvas();

		// diagramPane.setCanvas(canvas);

		junctionBoxMouseListener = new JunctionBoxMouseListener(diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas());
		wireMouseListener = new WireMouseListener(diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas());

		// Register listeners
		diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().setWireMouseListener(wireMouseListener);
		diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().addMouseListener(junctionBoxMouseListener);
		diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().addMouseListener(wireMouseListener);
		diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().addMouseMotionListener(wireMouseListener);

		buttonsPanel = new ButtonsPanel(e -> {
			boolean isCurrentlyDrawing = junctionBoxMouseListener.isDrawing();
			junctionBoxMouseListener.setDrawing(!isCurrentlyDrawing);
			wireMouseListener.setDrawingMode(false); // Ensure wire drawing mode is off

			if (junctionBoxMouseListener.isDrawing()) {
				diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().setDrawingCursor();
				buttonsPanel.updateJunctionBoxButtonText("Stop Drawing Junction Boxes");
				buttonsPanel.updateWireButtonText("Draw Wire"); // Reset other button
			} else {
				diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().resetCursor();
				buttonsPanel.updateJunctionBoxButtonText("Draw Junction Box");
			}
		}, e -> {
			boolean isCurrentlyDrawing = wireMouseListener.isDrawingMode();
			// Toggle wire drawing mode
			wireMouseListener.setDrawingMode(!isCurrentlyDrawing);
			junctionBoxMouseListener.setDrawing(false); // Ensure junction box drawing mode is off

			if (wireMouseListener.isDrawingMode()) {
				diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().setDrawingCursor();
				buttonsPanel.updateWireButtonText("Stop Drawing Wires");
				buttonsPanel.updateJunctionBoxButtonText("Draw Junction Box"); // Reset other button
			} else {
				diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().resetCursor();
				buttonsPanel.updateWireButtonText("Draw Wire");
			}
		}, diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas());

		add(buttonsPanel, BorderLayout.NORTH);
		add(diagramTabbedPane, BorderLayout.CENTER);

		setVisible(true);
	}
}
