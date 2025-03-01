package canvas;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JunctionBox extends Element {
    private Color color = Color.WHITE;
    private JunctionPoint[] junctionPoints;
    private static final int OFFSET = 12; // Distance from the edge for connector points
    private BufferedImage image;         // Image to display inside the JunctionBox
    //private int id;    // Unique identifier
    
    
    // Image path within the src folder
    private static String imagePath = "/images/flash.png"; // Adjust to your image's path
    
    // Unique ID generation
    private static int id = 0;
    private static synchronized int generateUniqueId() {
        return id++;
    }
    
    private String type; 
    private String name = "JunctionBox ";

    /**
     * Constructs a JunctionBox.
     * Note: The passed width and height are swapped to force portrait orientation.
     *
     * @param x         The x-coordinate of the top-left corner.
     * @param y         The y-coordinate of the top-left corner.
     * @param width     The width (this becomes the effective height).
     * @param height    The height (this becomes the effective width).
     * @param imagePath The resource path for the image (can be null).
     */
    public JunctionBox(Point point, Point endPoint, int width, int height) {
        // Swap width and height for portrait orientation:
        // effectiveWidth = height and effectiveHeight = width.
        // Call super() as the very first statement.
        super(point, endPoint, width, height);
        
        // Now assign the ID and update the name.
        id++;
        this.name += this.id;

        // Initialize connector points.
        junctionPoints = new JunctionPoint[4];
        initializeJunctionPoints();

        // Attempt to load the image.
        loadImage();
    }


    /**
     * Loads the image from the specified path.
     *
     * @param imagePath The path to the image resource.
     */
    private void loadImage() {
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

    /**
     * Initializes the four JunctionPoints around the box.
     */
    private void initializeJunctionPoints() {
        for (int i = 0; i < 4; i++) {
            int jpX = 0, jpY = 0;
            switch (i) {
                case 0: // Top
                    jpX = point.x + width / 2;
                    jpY = point.y - OFFSET;
                    break;
                case 1: // Right
                    jpX = point.x + width + OFFSET;
                    jpY = point.y + height / 2;
                    break;
                case 2: // Bottom
                    jpX = point.x + width / 2;
                    jpY = point.y + height + OFFSET;
                    break;
                case 3: // Left
                    jpX = point.x - OFFSET;
                    jpY = point.y + height / 2;
                    break;
            }
            junctionPoints[i] = new JunctionPoint(jpX, jpY);
            junctionPoints[i].setParentBox(this);
        }
    }

    /**
     * Checks if a given point is within the bounds of the JunctionBox.
     *
     * @param p the point to test.
     * @return true if the point is inside the box; false otherwise.
     */
    public boolean contains(Point p) {
        Rectangle bounds = new Rectangle(point.x, point.y, width, height);
        return bounds.contains(p);
    }

    public JunctionPoint[] getJunctionPoints() {
        return junctionPoints;
    }

    /**
     * Draws the JunctionBox including its border, image (if available), connector lines, and the JunctionPoints.
     *
     * @param g the Graphics context.
     */
    public void draw(Graphics g) {
        // Draw the box border.
        g.setColor(Color.BLACK);
        g.drawRect(point.x, point.y, width, height);

        // Draw the image if available; otherwise, fill with the default color.
        if (image != null) {
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();
            double imgAspect = (double) imgWidth / imgHeight;
            double boxAspect = (double) width / height;

            int drawWidth, drawHeight;
            if (imgAspect > boxAspect) {
                drawWidth = width - 2;
                drawHeight = (int) (drawWidth / imgAspect);
            } else {
                drawHeight = height - 2;
                drawWidth = (int) (drawHeight * imgAspect);
            }

            int drawX = point.x + (width - drawWidth) / 2 + 1;
            int drawY = point.y + (height - drawHeight) / 2 + 1;
            g.drawImage(image, drawX, drawY, drawWidth, drawHeight, null);
        } else {
            g.setColor(color);
            g.fillRect(point.x + 1, point.y + 1, width - 1, height - 1);
        }

        // Draw connector lines to each JunctionPoint.
        g.setColor(Color.BLACK);
        g.drawLine(point.x + width / 2, point.y, junctionPoints[0].getX(), junctionPoints[0].getY());
        g.drawLine(point.x + width, point.y + height / 2, junctionPoints[1].getX(), junctionPoints[1].getY());
        g.drawLine(point.x + width / 2, point.y + height, junctionPoints[2].getX(), junctionPoints[2].getY());
        g.drawLine(point.x, point.y + height / 2, junctionPoints[3].getX(), junctionPoints[3].getY());

        // Draw each JunctionPoint.
        for (JunctionPoint jp : junctionPoints) {
            jp.draw(g);
        }
    }
    
 // Add the getBounds method to return a Rectangle representing the bounding box of the JunctionBox
    public Rectangle getBounds() {
        return new Rectangle(point.x, point.y, width, height);
    }

    // Optional convenience getter methods.
    public int getX() {
        return point.x;
    }

    public int getY() {
        return point.y;
    }

    public int getId() {
        return id;
    }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
    
    
}
