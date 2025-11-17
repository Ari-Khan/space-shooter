/** 
 * Element.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A class representing a generic game element.
*/

// Import necessary packages
import java.awt.Graphics;
import java.awt.Rectangle;

// Abstract class representing a generic game element
public abstract class Element {
    // Protected attributes for position, size, and speed
    protected int x, y, width, height, speed;

    // Constructor to initialize the specific element's attributes
    public Element(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    } // End of constructor

    // Abstract methods to be implemented by subclasses
    public abstract void update();
    public abstract void draw(Graphics g);

    // Method to get the bounding rectangle of the element for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    } // End of getBounds method

    // Method to check if the element is off-screen
    public boolean isOffScreen(int screenHeight) {
        return y > screenHeight || y + height < 0;
    } // End of isOffScreen method
} // End of Element class