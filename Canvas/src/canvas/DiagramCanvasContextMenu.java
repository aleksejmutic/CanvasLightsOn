package canvas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiagramCanvasContextMenu extends JPopupMenu {
    private DiagramCanvas canvas;

    public DiagramCanvasContextMenu(DiagramCanvas canvas) {
        this.canvas = canvas;

        // Grid option
        JMenuItem toggleGridMenuItem = new JMenuItem("Toggle Grid");
        toggleGridMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.toggleGrid(); // Toggle grid visibility
            }
        });
        add(toggleGridMenuItem);

        // Zoom In option
        JMenuItem zoomInMenuItem = new JMenuItem("Zoom In");
        zoomInMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.zoomIn(); // Zoom in action
            }
        });
        add(zoomInMenuItem);

        // Zoom Out option
        JMenuItem zoomOutMenuItem = new JMenuItem("Zoom Out");
        zoomOutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.zoomOut(); // Zoom out action
            }
        });
        add(zoomOutMenuItem);
    }
}
