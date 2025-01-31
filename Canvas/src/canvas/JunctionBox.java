package canvas;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JunctionBox {
    private int x, y, width, height;
    private Color color = Color.WHITE;
    private JunctionPoint[] junctionPoints;

    private static final int OFFSET = 10; // Distance from the edge

    private BufferedImage image; // Image to display inside the JunctionBox
    private int id; // Unique identifier

    public JunctionBox(int x, int y, int width, int height, String imagePath) {
        this.width = height; // Swap width and height for portrait orientation
        this.height = width;
        this.x = x;
        this.y = y;
        this.id = generateUniqueId();

        this.junctionPoints = new JunctionPoint[4];
        initializeJunctionPoints();

        loadImage(imagePath);
    }

    public JunctionBox(int x, int y, int width, int height) {
        this(x, y, width, height, null);
    }

    private void loadImage(String imagePath) {
        if (imagePath != null) {
            try {
                image = ImageIO.read(getClass().getResourceAsStream(imagePath));
                if (image == null) {
                    System.err.println("Image not found in resources: " + imagePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
                image = null;
            }
        } else {
            image = null;
        }
    }

    private void initializeJunctionPoints() {
        // Assign unique IDs to the JunctionPoints
        for (int i = 0; i < 4; i++) {
            int jpX = 0, jpY = 0;
            switch (i) {
                case 0: // Top
                    jpX = x + width / 2;
                    jpY = y - OFFSET;
                    break;
                case 1: // Right
                    jpX = x + width + OFFSET;
                    jpY = y + height / 2;
                    break;
                case 2: // Bottom
                    jpX = x + width / 2;
                    jpY = y + height + OFFSET;
                    break;
                case 3: // Left
                    jpX = x - OFFSET;
                    jpY = y + height / 2;
                    break;
            }
            junctionPoints[i] = new JunctionPoint(jpX, jpY);
            junctionPoints[i].setParentBox(this);
            // JunctionPoint IDs are assigned in their constructor
        }
    }

    public boolean contains(Point point) {
        Rectangle bounds = new Rectangle(x, y, width, height);
        return bounds.contains(point);
    }

    public JunctionPoint[] getJunctionPoints() {
        return junctionPoints;
    }

    public void draw(Graphics g) {
        // Draw the JunctionBox border
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        // Draw the image or default fill
        if (image != null) {
            // Maintain aspect ratio
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();
            double imgAspect = (double) imgWidth / imgHeight;
            double boxAspect = (double) width / height;

            int drawWidth, drawHeight;
            if (imgAspect > boxAspect) {
                // Image is wider
                drawWidth = width - 2;
                drawHeight = (int) (drawWidth / imgAspect);
            } else {
                // Image is taller
                drawHeight = height - 2;
                drawWidth = (int) (drawHeight * imgAspect);
            }

            int drawX = x + (width - drawWidth) / 2 + 1;
            int drawY = y + (height - drawHeight) / 2 + 1;

            g.drawImage(image, drawX, drawY, drawWidth, drawHeight, null);
        } else {
            // Default fill
            g.setColor(color);
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
        }

        // Draw connector lines to JunctionPoints
        g.setColor(Color.BLACK);
        // Top
        g.drawLine(x + width / 2, y, junctionPoints[0].getX(), junctionPoints[0].getY());
        // Right
        g.drawLine(x + width, y + height / 2, junctionPoints[1].getX(), junctionPoints[1].getY());
        // Bottom
        g.drawLine(x + width / 2, y + height, junctionPoints[2].getX(), junctionPoints[2].getY());
        // Left
        g.drawLine(x, y + height / 2, junctionPoints[3].getX(), junctionPoints[3].getY());

        // Draw JunctionPoints
        for (JunctionPoint point : junctionPoints) {
            point.draw(g);
        }
    }

    // Getter methods
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public int getId() { return id; }

    // Unique ID generation
    private static int nextId = 0;

    private static synchronized int generateUniqueId() {
        return nextId++;
    }
}
