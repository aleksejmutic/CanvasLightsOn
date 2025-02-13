package documentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DocumentationCanvas extends JPanel {
    private JTextArea textArea;

    public DocumentationCanvas() {
        setLayout(new BorderLayout());

        // Create and add the title panel (above JTextArea)
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Documentation");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Create the JTextArea for editing documentation
        textArea = new JTextArea();
        textArea.setFont(new Font("Serif", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(10, 20, 10, 20));  // Adds padding to the JTextArea (left and right)
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));  // Set preferred size for the JTextArea scroll pane

        // Add scroll pane to center
        add(scrollPane, BorderLayout.CENTER);

        // Create and add the button panel (below JTextArea)
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(buttonPanel.getWidth(), 60)); // Increase height of the button panel
        
        // Create the Save button and add to the left of buttonPanel
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to save the content of the text area
                saveContent();
            }
        });
        buttonPanel.add(saveButton, BorderLayout.WEST);
        
        // Create the Quit button and add to the right of buttonPanel
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to quit or close the application
                quitApplication();
            }
        });
        buttonPanel.add(quitButton, BorderLayout.EAST);

        // Add the button panel to the south part of the layout
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveContent() {
        // Your save logic here
        String content = textArea.getText();
        System.out.println("Saving content:\n" + content);
        // Example save to a file or database logic
    }

    private void quitApplication() {
        // Your quit logic here
        System.exit(0);  // This will close the application
    }
}
