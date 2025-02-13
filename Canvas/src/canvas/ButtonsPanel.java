package canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel {
    private JButton drawJunctionBoxButton;
    private JButton drawWireButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton toggleGridButton; // New button for toggling grid
    private DiagramCanvas canvas;

    public ButtonsPanel(ActionListener drawJunctionBoxListener, ActionListener drawWireListener, DiagramCanvas canvas) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        setBackground(Color.BLUE);
        
        this.canvas = canvas;
        
        drawJunctionBoxButton = new JButton("Draw Junction Box");
        drawWireButton = new JButton("Draw Wire");
        zoomInButton = new JButton("Zoom In");
        zoomOutButton = new JButton("Zoom Out");
        toggleGridButton = new JButton("Toggle Grid"); // Button for toggling grid

        drawJunctionBoxButton.addActionListener(drawJunctionBoxListener);
        drawWireButton.addActionListener(drawWireListener);
        
        // Set ActionListeners for buttons
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.zoomIn(); // Trigger zoom in on canvas
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.zoomOut(); // Trigger zoom out on canvas
            }
        });

        // Toggle grid visibility when clicked
        toggleGridButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.toggleGrid(); // Toggle the grid visibility
            }
        });

        add(drawJunctionBoxButton);
        add(drawWireButton);
        add(zoomInButton);
        add(zoomOutButton);
        add(toggleGridButton); // Add the toggle grid button
    }

    public void updateJunctionBoxButtonText(String text) {
        drawJunctionBoxButton.setText(text);
    }

    public void updateWireButtonText(String text) {
        drawWireButton.setText(text);
    }
}
