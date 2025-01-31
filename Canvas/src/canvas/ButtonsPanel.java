package canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel {
    private JButton drawJunctionBoxButton;
    private JButton drawWireButton;

    public ButtonsPanel(ActionListener drawJunctionBoxListener, ActionListener drawWireListener) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        setBackground(Color.BLUE);

        drawJunctionBoxButton = new JButton("Draw Junction Box");
        drawWireButton = new JButton("Draw Wire");

        drawJunctionBoxButton.addActionListener(drawJunctionBoxListener);
        drawWireButton.addActionListener(drawWireListener);

        add(drawJunctionBoxButton);
        add(drawWireButton);
    }

    public void updateJunctionBoxButtonText(String text) {
        drawJunctionBoxButton.setText(text);
    }

    public void updateWireButtonText(String text) {
        drawWireButton.setText(text);
    }
}
