import java.awt.*;

public class Spacecraft extends Element {
    private boolean movingLeft, movingRight;
    private boolean shield, rapidFire;
    private int baseSpeed, shotCooldown;
    private int powerUpTimer;

    public Spacecraft(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
        this.baseSpeed = speed;
    }

    @Override
    public void update() {
        if (movingLeft && x > 0) x -= speed;
        if (movingRight && x + width < 800) x += speed;
        if (shotCooldown > 0) shotCooldown--;

        if (powerUpTimer > 0) {
            powerUpTimer--;
            if (powerUpTimer == 0) {
                speed = baseSpeed;
                rapidFire = false;
                shield = false;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        int[] shipX = {x, x + width / 4, x + width / 2, x + width * 3 / 4, x + width, x + width / 2};
        int[] shipY = {y + height, y + height / 2, y, y + height / 2, y + height, y + height / 2 + 7};
        g.fillPolygon(shipX, shipY, shipX.length);

        if (shield) {
            g.setColor(Color.BLUE);
            g.drawOval(x - 15, y - 15, width + 30, height + 30);
        }
    }

    public boolean canShoot() {
        return shotCooldown == 0;
    }

    public void shootCooldown() {
        shotCooldown = rapidFire ? 10 : 25;
    }

    public void activatePowerUp(String type) {
        if (type.equals("speed")) {
            speed = baseSpeed * 2;
        } else if (type.equals("rapidfire")) {
            rapidFire = true;
        } else if (type.equals("shield")) {
            shield = true;
        }
        powerUpTimer = 300;
    }

    public void setMovingLeft(boolean value) {
        movingLeft = value;
    }

    public void setMovingRight(boolean value) {
        movingRight = value;
    }

    public boolean hasShield() {
        return shield;
    }
}
