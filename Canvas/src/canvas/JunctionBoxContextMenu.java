package canvas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JunctionBoxContextMenu extends JPopupMenu {
    private JMenuItem editMenuItem;
    private JMenuItem cutMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem renameMenuItem;
    private JMenuItem showExampleMenuItem;

    public JunctionBoxContextMenu(JunctionBox junctionBox) {
        // Edit
        editMenuItem = new JMenuItem("Edit");
        editMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Edit JunctionBox: " + junctionBox.getName());
            }
        });

        // Cut
        cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Cut JunctionBox: " + junctionBox.getName());
            }
        });

        // Copy
        copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Copy JunctionBox: " + junctionBox.getName());
            }
        });

        // Rename
        renameMenuItem = new JMenuItem("Rename");
        renameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog("Enter new name:");
                if (newName != null && !newName.isEmpty()) {
                    junctionBox.setName(newName);
                    JOptionPane.showMessageDialog(null, "Renamed JunctionBox to: " + newName);
                }
            }
        });

        // Show Example
        showExampleMenuItem = new JMenuItem("Show Example");
        showExampleMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Showing example for: " + junctionBox.getName());
            }
        });

        // Add items to the context menu
        add(editMenuItem);
        add(cutMenuItem);
        add(copyMenuItem);
        add(renameMenuItem);
        add(showExampleMenuItem);
    }
}
