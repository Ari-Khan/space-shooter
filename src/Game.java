import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable, KeyListener {
    private final Spacecraft player;
    private final ArrayList<Shot> shots = new ArrayList<>();
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<PowerUp> powerUps = new ArrayList<>();
    private final Text text;

    private boolean running = true;
    private int enemySpawnTimer = 0;
    private final int width = 800, height = 600;

    public Game() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        player = new Spacecraft(width / 2 - 25, height - 80, 50, 25, 10);
        text = new Text(width, height);

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (running) {
            if (!text.isGameOver()) {
                enemySpawnTimer++;

                // Update entities
                player.update();
                shots.forEach(Shot::update);
                enemies.forEach(Enemy::update);
                powerUps.forEach(PowerUp::update);

                // Player firing
                if (player.shouldShoot()) {
                    shots.add(new Shot(player.x + player.width / 2 - 2, player.y));
                }

                // Remove off-screen
                shots.removeIf(s -> s.isOffScreen(height));
                enemies.removeIf(e -> e.isOffScreen(height));
                powerUps.removeIf(p -> p.isOffScreen(height));

                // Collisions: shots vs enemies
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy enemy = enemies.get(i);
                    for (int j = 0; j < shots.size(); j++) {
                        Shot shot = shots.get(j);
                        if (shot.getBounds().intersects(enemy.getBounds())) {
                            enemies.remove(i);
                            shots.remove(j);
                            text.addScore(10);
                            i--;
                            break;
                        }
                    }
                }

                // Collisions: powerups
                Rectangle playerBounds = player.getBounds();
                for (int i = 0; i < powerUps.size(); i++) {
                    PowerUp p = powerUps.get(i);
                    if (playerBounds.intersects(p.getBounds())) {
                        player.activatePowerUp(p.getType());
                        powerUps.remove(i);
                        i--;
                    }
                }

                // Check collision: player vs enemy (game over)
                for (Enemy e : enemies) {
                    if (player.getBounds().intersects(e.getBounds())) {
                        text.setGameOver(true);
                        break;
                    }
                }

                // Shield hits
                if (player.hasShield()) {
                    Rectangle shieldArea = new Rectangle(player.x - 15, player.y - 15, player.width + 30, player.height + 30);
                    enemies.removeIf(enemy -> shieldArea.intersects(enemy.getBounds()));
                }

                // Spawning
                if (enemySpawnTimer % 100 == 0)
                    enemies.add(new Enemy((int) (Math.random() * (width - 50)), 0));
                if (enemySpawnTimer % 200 == 0) {
                    String[] types = {"speed", "rapidfire", "shield"};
                    String type = types[(int) (Math.random() * types.length)];
                    powerUps.add(new PowerUp((int) (Math.random() * (width - 25)), 0, type));
                }
            }

            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException ignored) {}
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        shots.forEach(s -> s.draw(g));
        enemies.forEach(e -> e.draw(g));
        powerUps.forEach(p -> p.draw(g));
        text.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT) player.setMovingLeft(true);
        if (k == KeyEvent.VK_RIGHT) player.setMovingRight(true);
        if (k == KeyEvent.VK_SPACE) player.setFiring(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT) player.setMovingLeft(false);
        if (k == KeyEvent.VK_RIGHT) player.setMovingRight(false);
        if (k == KeyEvent.VK_SPACE) player.setFiring(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Shooter");
        frame.add(new Game());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}