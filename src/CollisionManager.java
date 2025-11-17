/** 
 * CollisionManager.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A helper class to manage collisions between game elements.
*/ 

// Import necessary libraries
import java.awt.Rectangle;
import java.util.ArrayList;

// CollisionManager class to handle collisions
public class CollisionManager {
    // Method to handle collisions between various game elements
    public void handleCollisions(Spacecraft player, ArrayList<Shot> shots, ArrayList<Enemy> enemies, ArrayList<PowerUp> powerUps, Text text, Sound explosionSound, Sound powerSound) {
        // Loop to check for collisions between shots and enemies
        for (int i = 0; i < enemies.size(); i++) {
            // Get the current enemy
            Enemy enemy = enemies.get(i);

            // Loop through all shots to check for collisions with the current enemy
            for (int j = 0; j < shots.size(); j++) {
                Shot shot = shots.get(j);

                // Check if the shot intersects with the enemy
                if (shot.getBounds().intersects(enemy.getBounds())) {
                    // If they intersect, remove both the enemy and the shot, update score, and play explosion sound
                    enemies.remove(i);
                    shots.remove(j);
                    text.addScore(10);
                    explosionSound.play();
                    i--;
                    break;
                } // End if
            } // End for loop
        } // End for loop

        // Create a hitbox for the player
        Rectangle playerBounds = player.getBounds();

        // Check for collisions between player and power-ups
        for (int i = 0; i < powerUps.size(); i++) {
            // Get the current power-up
            PowerUp p = powerUps.get(i);

            // Check if the player intersects with the power-up
            if (playerBounds.intersects(p.getBounds())) {
                // If they intersect, activate the power-up effect, play sound, and remove the power-up
                player.activatePowerUp(p.getType());
                powerSound.play();
                powerUps.remove(i);
                i--;
            } // End if
        } // End for loop

        // Check for collisions between player and enemies
        for (int i = 0; i < enemies.size(); i++) {
            // Get the current enemy
            Enemy e = enemies.get(i);

            // Check if the player intersects with the enemy
            if (playerBounds.intersects(e.getBounds())) {
                // If they intersect, reduce player's life, remove the enemy, and play explosion sound
                text.loseLife();
                enemies.remove(i);
                explosionSound.play();
                i--;

                // If the game is over, exit the loop
                if (text.isGameOver()) break;
            } // End if
        } // End for loop

        // If the player has a shield power-up, check for collisions with enemies
        if (player.hasShield()) {
            // Create a larger hitbox for the shield
            Rectangle shieldArea = new Rectangle(player.x - 15, player.y - 15, player.width + 30, player.height + 30);

            // Check for collisions between shield area and enemies
            for (int i = 0; i < enemies.size(); i++) {
                // Get the current enemy
                Enemy enemy = enemies.get(i);

                // Check if the shield area intersects with the enemy
                if (shieldArea.intersects(enemy.getBounds())) {
                    // If they intersect, remove the enemy and play explosion sound
                    enemies.remove(i);
                    explosionSound.play();
                    i--;
                } // End if
            } // End for loop
        } // End if
    } // End of handleCollisions method
} // End of CollisionManager class