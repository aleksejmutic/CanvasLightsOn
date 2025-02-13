package documentation;

import javax.swing.*;
import java.awt.*;

public class DocumentationTabbedPane extends JTabbedPane {
    private DocumentationCanvas documentationCanvas;

    public DocumentationTabbedPane() {
        documentationCanvas = new DocumentationCanvas();

        // Add DocumentationCanvas directly as a tab
        this.addTab("Documentation", documentationCanvas);

        // Ensure tab expands fully
        this.setTabComponentAt(0, createTabPanel());
    }

    private JPanel createTabPanel() {
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tabPanel.setOpaque(false);

        JLabel label = new JLabel("Documentation");
        JButton closeButton = new JButton("X");
        closeButton.setPreferredSize(new Dimension(15, 15));
        closeButton.setToolTipText("Close Tab");
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder());

        closeButton.addActionListener(e -> {
            int index = getSelectedIndex();
            if (index != -1) {
                removeTabAt(index);
            }
        });

        tabPanel.add(label);
        tabPanel.add(closeButton);

        return tabPanel;
    }

    public DocumentationCanvas getDocumentationCanvas() {
        return documentationCanvas;
    }
}
