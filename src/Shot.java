/** 
 * Shot.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A class describing shot elements fired by the spacecraft.
*/ 

// Import necessary packages
import java.awt.Color;
import java.awt.Graphics;

// Class representing a shot element in the game
public class Shot extends Element {
    // Constructor to initialize the shot's attributes
    public Shot(int x, int y) {
        // Call the superclass constructor with specific size and speed
        super(x, y, 4, 12, 15);
    } // End of constructor

    // Override the update method to define shot movement (upward)
    @Override
    public void update() {
        y -= speed;
    } // End of update method

    // Override the draw method to render the shot
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    } // End of draw method
} // End of Shot class