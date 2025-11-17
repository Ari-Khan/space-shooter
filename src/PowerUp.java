/** 
 * PowerUp.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A class describing power-up elements in the game.
*/ 

// Import necessary packages
import java.awt.Color;
import java.awt.Graphics;

// Class representing a power-up element in the game
public class PowerUp extends Element {
    // Initialize type of power-up constant
    private final String type;

    // Constructor to initialize the power-up's attributes
    public PowerUp(int x, int y, String type) {
        // Call the superclass constructor with specific size and speed
        super(x, y, 25, 25, 5);
        this.type = type;
    } // End of constructor

    // Override the update method to define power-up movement (downward)
    @Override
    public void update() {
        y += speed;
    } // End of update method

    // Override the draw method to render the power-up
    @Override
    public void draw(Graphics g) {
        // Set color based on power-up type
        if (type.equals("speed")) {
            g.setColor(Color.GREEN);
        } else if (type.equals("rapidfire")) {
            g.setColor(Color.ORANGE);
        } else if (type.equals("shield")) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.GRAY);
        }
        g.fillOval(x, y, width, height);
    } // End of draw method

    // Method to get the type of power-up
    public String getType() {
        return type;
    } // End of getType method
} // End of PowerUp class
