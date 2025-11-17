/** 
 * Enemy.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A class describing enemy elements (asteroids) in the game.
*/ 

// Import necessary packages
import java.awt.Color;
import java.awt.Graphics;

// Class representing an enemy element (asteroid)
public class Enemy extends Element {
    // Constructor to initialize the enemy's attributes
    public Enemy(int x, int y) {
        // Call the superclass constructor with specific size and speed
        super(x, y, 50, 40, 4);
    } // End of constructor

    // Override the update method to define enemy movement (downward)
    @Override
    public void update() {
        y += speed;
    } // End of update method

    // Override the draw method to render the enemy as a hexagon
    @Override
    public void draw(Graphics g) {
        // Set color for the enemy
        g.setColor(new Color(0xFF3300));
        // Calculate hexagon points and draw it
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int radius = width / 2;
        int[] hexX = new int[6];
        int[] hexY = new int[6];
        for (int i = 0; i < 6; i++) {
            hexX[i] = (int) (centerX + radius * Math.cos(Math.toRadians(60 * i)));
            hexY[i] = (int) (centerY + radius * Math.sin(Math.toRadians(60 * i)));
        }
        // Fill the hexagon
        g.fillPolygon(hexX, hexY, 6);
    } // End of draw method
} // End of Enemy class