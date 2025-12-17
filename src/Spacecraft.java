/** 
 * Spacecraft.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A class describing the player's spacecraft in the game.
*/ 

// Import necessary packages
import java.awt.Color;
import java.awt.Graphics;

// Class representing the player's spacecraft (Inheritance)
public class Spacecraft extends Element {
    // Attributes for movement and power-ups
    private boolean movingLeft, movingRight, firing;
    private boolean shield, rapidFire;
    private int shotCooldown, powerUpTimer;
    private int shotsFired, maxShotsPerHold = 10;

    // Velocity and acceleration for smooth movement
    private double xVelocity = 0;
    private double accel = 0.3;
    private final double friction = 0.1;
    private double maxSpeed;

    // Constructor to initialize the spacecraft's attributes
    public Spacecraft(int x, int y, int width, int height, int speed) {
        // Call the superclass constructor with specific size and speed
        super(x, y, width, height, speed);
        this.maxSpeed = speed;
    } // End of constructor

    // Override the update method to define spacecraft movement (side to side)
    @Override
    public void update() {
        // Update horizontal velocity based on input
        if (movingLeft)  xVelocity -= accel;
        if (movingRight) xVelocity += accel;

        // Cap velocity to max speed
        if (xVelocity >  maxSpeed) xVelocity =  maxSpeed;
        if (xVelocity < -maxSpeed) xVelocity = -maxSpeed;

        // Apply friction to slow velocity if no input is given
        if (!movingLeft && !movingRight) {
            if (xVelocity > 0) xVelocity = Math.max(0, xVelocity - friction);
            else if (xVelocity < 0) xVelocity = Math.min(0, xVelocity + friction);
        } // End if

        // Update position based on velocity
        x += xVelocity;

        // Keep spacecraft within screen bounds and bounce slightly on impact by reversing velocity
        if (x < 0) {
            x = 0;
            xVelocity = -xVelocity * 0.5;
        }
        if (x + width > 800) {
            x = 800 - width;
            xVelocity = -xVelocity * 0.5;
        }

        // Update shot cooldown and power-up timer
        if (shotCooldown > 0) shotCooldown--;

        // Handle power-up duration
        if (powerUpTimer > 0) {
            powerUpTimer--;
            // Restore normal attributes when timer expires
            if (powerUpTimer == 0) {
                accel = 0.3;
                rapidFire = false;
                shield = false;
            } // End if
        } // End if
    } // End of update method

    // Override the draw method to render the spacecraft
    @Override
    public void draw(Graphics g) {
        // Draw the spacecraft as a "sunken arrow"
        g.setColor(Color.CYAN);
        int[] shipX = {x, x + width / 4, x + width / 2, x + width * 3 / 4, x + width, x + width / 2};
        int[] shipY = {y + height, y + height / 2, y, y + height / 2, y + height, y + height / 2 + 7};
        g.fillPolygon(shipX, shipY, shipX.length);

        // Draw shield around spaceship if active
        if (shield) {
            g.setColor(Color.BLUE);
            g.drawOval(x - 15, y - 15, width + 30, height + 30);
        } // End if
    } // End of draw method

    // Method to determine if the spacecraft should shoot
    public boolean shouldShoot() {
        // If not firing or still in cooldown, cannot shoot
        if ((!firing || shotCooldown > 0)) return false;
        if ((shotsFired >= maxShotsPerHold) && !rapidFire) return false;

        // Reset cooldown and increment shots fired
        shootCooldown();
        shotsFired++;

        // Allow shooting
        return true;
    } // End of shouldShoot method

    // Method to set the shooting cooldown based on rapid fire status
    private void shootCooldown() {
        // Set cooldown to 7 frames if rapid fire is active, else 20 frames
        shotCooldown = rapidFire ? 7 : 20;
    } // End of shootCooldown method

    // Method to set firing status
    public void setFiring(boolean value) {
        // Reset shots fired count when starting to fire
        if (!firing && value) {
            shotsFired = 0;
        } // End if
        firing = value;
    } // End of setFiring method

    // Method to activate a power-up
    public void activatePowerUp(String type) {
        if (type.equals("speed")) {
            accel = 0.6;
        } else if (type.equals("rapidfire")) {
            rapidFire = true;
        } else if (type.equals("shield")) {
            shield = true;
        } // End if
        powerUpTimer = 300;
    } // End of activatePowerUp method

    // Methods to set movement status
    public void setMovingLeft(boolean value) { movingLeft = value; }
    public void setMovingRight(boolean value) { movingRight = value; }
    public boolean hasShield() { return shield; }
} // End of Spacecraft class
