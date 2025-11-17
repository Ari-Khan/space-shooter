import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Text {
    private int score;
    private boolean gameOver;
    private int lives = 3;
    private final int width, height;

    public Text(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public void loseLife() {
        lives--;
        if (lives <= 0) {
            gameOver = true;
        }
    }

    public int getLives() {
        return lives;
    }

    public void setGameOver(boolean value) {
        gameOver = value;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 20);

        String livesText = "Lives: " + lives;
        g.drawString(livesText, width - 20 - g.getFontMetrics().stringWidth(livesText), 20);

        if (gameOver) {
            g.setFont(new Font("Verdana", Font.BOLD, 48));
            g.setColor(Color.WHITE);
            String msg = "GAME OVER";
            g.drawString(msg, width / 2 - g.getFontMetrics().stringWidth(msg) / 2, height / 2 - 30);

            g.setFont(new Font("Verdana", Font.PLAIN, 28));
            String scoreMsg = "Final Score: " + score;
            g.drawString(scoreMsg, width / 2 - g.getFontMetrics().stringWidth(scoreMsg) / 2, height / 2 + 20);
        }
    }
}