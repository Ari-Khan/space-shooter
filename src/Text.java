/** 
 * Text.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A class describing game text such as score, lives, and game over messages.
*/ 

// Import necessary packages
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// Class representing game text elements
public class Text {
    // Attributes for score, lives, and game over status
    private int score;
    private boolean gameOver;
    private int lives = 3;
    private final int width, height;

    // Constructor to initialize text attributes
    public Text(int width, int height) {
        this.width = width;
        this.height = height;
    } // End of constructor

    // Method to update score and lives
    public void addScore(int amount) {
        score += amount;
    } // End of addScore method

    // Method to decrease lives
    public void loseLife() {
        lives--;
        // If lives reach zero, set game over status
        if (lives <= 0) {
            gameOver = true;
        } // End if
    } // End of loseLife method

    // Get lives methods
    public int getLives() {
        return lives;
    } // End of getLives method

    // Methods to set and get game over status
    public void setGameOver(boolean value) {
        gameOver = value;
    } // End of setGameOver method

    // Method to check if the game is over
    public boolean isGameOver() {
        return gameOver;
    } // End of isGameOver method

    // Method to get the current score
    public int getScore() {
        return score;
    } // End of getScore method

    // Method to draw the text on the screen (Method Overloading)
    public void draw(Graphics g) {
        // Draw score and lives at the top corners
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 20);

        String livesText = "Lives: " + lives;
        g.drawString(livesText, width - 20 - g.getFontMetrics().stringWidth(livesText), 20);

        // If game over, display game over message at the centre
        if (gameOver) {
            // Draw text at the centre
            g.setFont(new Font("Verdana", Font.BOLD, 48));
            g.setColor(Color.WHITE);
            String msg = "GAME OVER";
            g.drawString(msg, width / 2 - g.getFontMetrics().stringWidth(msg) / 2, height / 2 - 30);

            // Draw final score below game over message
            g.setFont(new Font("Verdana", Font.PLAIN, 28));
            String scoreMsg = "Final Score: " + score;
            g.drawString(scoreMsg, width / 2 - g.getFontMetrics().stringWidth(scoreMsg) / 2, height / 2 + 20);
        } // End if
    } // End of draw method
} // End of Text class