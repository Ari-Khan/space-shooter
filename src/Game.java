/** 
 * Game.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A Java program to play a space shooter game.
*/ 

// Import necessary libraries
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;

// Main game class which extends JPanel and uses the Runnable and KeyListener interfaces
public class Game extends JPanel implements Runnable, KeyListener {
    // Initialize objects (Encapsulation)
    private final Spacecraft player;
    private final Text text;

    // Collision manager
    private final CollisionManager collisionManager = new CollisionManager();

    // Initialize sound objects
    private final Sound shootSound = new Sound("src/shot.wav");
    private final Sound explosionSound = new Sound("src/explosion.wav");
    private final Sound powerSound = new Sound("src/powerUp.wav");

    // Initialize object lists
    private final ArrayList<Shot> shots = new ArrayList<>();
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<PowerUp> powerUps = new ArrayList<>();

    // Initialize default variable / constant values
    private boolean running = true;
    private int enemySpawnTimer = 0;
    private final int width = 800, height = 600;

    // A constructor to set up the game 
    public Game() {
        // Set up panel
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        // Set up key listener
        setFocusable(true);
        addKeyListener(this);

        // Set up game objects
        player = new Spacecraft(width / 2 - 25, height - 80, 50, 25, 15);
        text = new Text(width, height);

        // Start game thread
        new Thread(this).start();
    } // End of constructor

    // A method to run the game loop
    @Override
    public void run() {
        // Game loop
        while (running) {
            // Update game only if not game over
            if (!text.isGameOver()) {
                // Increment enemy spawn timer
                enemySpawnTimer++;

                // Update all game elements
                player.update();
                shots.forEach(Shot::update);
                enemies.forEach(Enemy::update);
                powerUps.forEach(PowerUp::update);

                // Handle shooting
                if (player.shouldShoot()) {
                    shots.add(new Shot(player.x + player.width / 2 - 2, player.y));
                    shootSound.play();
                }

                // Remove off-screen elements using lambda expressions
                shots.removeIf(s -> s.isOffScreen(height));
                enemies.removeIf(e -> e.isOffScreen(height));
                powerUps.removeIf(p -> p.isOffScreen(height));

                // Handle collisions using CollisionManager
                collisionManager.handleCollisions(player, shots, enemies, powerUps, text, explosionSound, powerSound);

                // Spawn enemies and power-ups at intervals of 20 frames
                if (enemySpawnTimer % 20 == 0)
                    // Create new enemy at random x position
                    enemies.add(new Enemy((int) (Math.random() * (width - 50)), 0));

                // Spawn power-ups at intervals of 300 frames
                if (enemySpawnTimer % 300 == 0) {
                    // Create new power-up at random x position with random type
                    String[] types = {"speed", "rapidfire", "shield"};
                    String type = types[(int) (Math.random() * types.length)];
                    powerUps.add(new PowerUp((int) (Math.random() * (width - 25)), 0, type));
                } // End if
            } // End if

            // Repaint the panel
            repaint();

            // Try to sleep for ~16ms to achieve ~60 FPS and catch InterruptedException
            try {
                Thread.sleep(16);
            } catch (InterruptedException ignored) {}
        } // End while loop
    } // End of run method

    // A method to paint the game elements
    @Override
    protected void paintComponent(Graphics g) {
        // Clear the panel
        super.paintComponent(g);

        // Draw all game elements using lambda expressions
        player.draw(g);
        shots.forEach(s -> s.draw(g));
        enemies.forEach(e -> e.draw(g));
        powerUps.forEach(p -> p.draw(g));
        text.draw(g);
    } // End of paintComponent method

    // A method to handle key presses and releases
    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        // Handle key presses
        if (k == KeyEvent.VK_LEFT) player.setMovingLeft(true);
        if (k == KeyEvent.VK_RIGHT) player.setMovingRight(true);
        if (k == KeyEvent.VK_SPACE) player.setFiring(true);
    } // End of keyPressed method

    // A method to handle key releases
    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();

        // Handle key releases
        if (k == KeyEvent.VK_LEFT) player.setMovingLeft(false);
        if (k == KeyEvent.VK_RIGHT) player.setMovingRight(false);
        if (k == KeyEvent.VK_SPACE) player.setFiring(false);
    } // End of keyReleased method

    // A method to handle typing (not used)
    @Override
    public void keyTyped(KeyEvent e) {}

    // The main method to start the game
    public static void main(String[] args) {
        // Create and set up the game frame
        JFrame frame = new JFrame("Space Shooter");
        frame.add(new Game());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } // End of main method
} // End of Game class 