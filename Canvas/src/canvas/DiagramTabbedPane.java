package canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiagramTabbedPane extends JTabbedPane {
    private DiagramScrollPane diagramScrollPane;

    public DiagramTabbedPane() {
        // Initialize the DiagramScrollPane (which is a JScrollPane containing the DiagramCanvas)
        diagramScrollPane = new DiagramScrollPane();

        // Add the DiagramScrollPane to the tabbed pane as a new tab
        this.addTab("Diagram", diagramScrollPane);

        // Set the custom tab with the close button next to the title
        setTabComponentAt(0, createTabPanel());
    }

    // Creates a custom tab with a close button next to the title
    private JPanel createTabPanel() {
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Use FlowLayout to align title and button horizontally
        tabPanel.setOpaque(false);  // Make the panel transparent

        // Create the title label
        JLabel label = new JLabel("Diagram");
        label.setHorizontalAlignment(SwingConstants.LEFT);

        // Create the close button
        JButton closeButton = new JButton("X");
        closeButton.setPreferredSize(new Dimension(15, 15));
        closeButton.setToolTipText("Close Tab");
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder());

        // Add action listener for close button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the tab
                int index = getSelectedIndex();
                if (index != -1) {
                    removeTabAt(index);
                }
            }
        });

        // Add the label and close button to the tabPanel
        tabPanel.add(label);
        tabPanel.add(closeButton);

        return tabPanel;
    }

    // Setter for DiagramScrollPane
    public void setDiagramPane(DiagramScrollPane diagramPane) {
        this.diagramScrollPane = diagramPane;
    }

    // Getter for accessing the DiagramScrollPane if needed
    public DiagramScrollPane getDiagramScrollPane() {
        return diagramScrollPane;
    }
}
