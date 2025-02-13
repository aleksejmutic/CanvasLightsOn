package canvas;

import javax.swing.*;

import documentation.DocumentationTabbedPane;

import java.awt.*;

public class MainFrame extends JFrame {
    private DiagramTabbedPane diagramTabbedPane = new DiagramTabbedPane();
    private DocumentationTabbedPane documentationTabbedPane = new DocumentationTabbedPane();
    private JunctionBoxMouseListener junctionBoxMouseListener;
    private WireMouseListener wireMouseListener;
    private ButtonsPanel buttonsPanel;

    public MainFrame() {
        setTitle("Circuit Editor");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Listeners for canvas interactions
        junctionBoxMouseListener = new JunctionBoxMouseListener(diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas());
        wireMouseListener = new WireMouseListener(diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas());

        diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().setWireMouseListener(wireMouseListener);
        diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().addMouseListener(junctionBoxMouseListener);
        diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().addMouseListener(wireMouseListener);
        diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().addMouseMotionListener(wireMouseListener);

        // Buttons Panel with event handlers
        buttonsPanel = new ButtonsPanel(e -> {
            boolean isCurrentlyDrawing = junctionBoxMouseListener.isDrawing();
            junctionBoxMouseListener.setDrawing(!isCurrentlyDrawing);
            wireMouseListener.setDrawingMode(false);

            if (junctionBoxMouseListener.isDrawing()) {
                diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().setDrawingCursor();
                buttonsPanel.updateJunctionBoxButtonText("Stop Drawing Junction Boxes");
                buttonsPanel.updateWireButtonText("Draw Wire");
            } else {
                diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().resetCursor();
                buttonsPanel.updateJunctionBoxButtonText("Draw Junction Box");
            }
        }, e -> {
            boolean isCurrentlyDrawing = wireMouseListener.isDrawingMode();
            wireMouseListener.setDrawingMode(!isCurrentlyDrawing);
            junctionBoxMouseListener.setDrawing(false);

            if (wireMouseListener.isDrawingMode()) {
                diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().setDrawingCursor();
                buttonsPanel.updateWireButtonText("Stop Drawing Wires");
                buttonsPanel.updateJunctionBoxButtonText("Draw Junction Box");
            } else {
                diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas().resetCursor();
                buttonsPanel.updateWireButtonText("Draw Wire");
            }
        }, diagramTabbedPane.getDiagramScrollPane().getDiagramCanvas());

        // Use JSplitPane for horizontal layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, diagramTabbedPane, documentationTabbedPane);
        splitPane.setDividerLocation(600); // Adjust initial divider position

        // Add the components to the frame
        add(buttonsPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER); // Add split pane containing both tabbed panes

        setVisible(true);
    }
}
