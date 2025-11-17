import java.awt.Color;
import java.awt.Graphics;

public class Spacecraft extends Element {
    private boolean movingLeft, movingRight, firing;
    private boolean shield, rapidFire;
    private int baseSpeed, shotCooldown, powerUpTimer;
    private int shotsFired, maxShotsPerHold = 10;

    private double xVelocity = 0;
    private final double accel = 0.3;
    private final double friction = 0.1;
    private double maxSpeed;

    public Spacecraft(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
        this.baseSpeed = speed;
        this.maxSpeed = speed;
    }

    @Override
    public void update() {
        if (movingLeft)  xVelocity -= accel;
        if (movingRight) xVelocity += accel;

        if (xVelocity >  maxSpeed) xVelocity =  maxSpeed;
        if (xVelocity < -maxSpeed) xVelocity = -maxSpeed;

        if (!movingLeft && !movingRight) {
            if (xVelocity > 0) xVelocity = Math.max(0, xVelocity - friction);
            else if (xVelocity < 0) xVelocity = Math.min(0, xVelocity + friction);
        }

        x += xVelocity;

        if (x < 0) {
            x = 0;
            xVelocity = -xVelocity * 0.5;
        }
        if (x + width > 800) {
            x = 800 - width;
            xVelocity = -xVelocity * 0.5;
        }

        if (shotCooldown > 0) shotCooldown--;

        if (powerUpTimer > 0) {
            powerUpTimer--;
            if (powerUpTimer == 0) {
                maxSpeed = baseSpeed;
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

    public boolean shouldShoot() {
        if ((!firing || shotCooldown > 0)) return false;
        if ((shotsFired >= maxShotsPerHold) && !rapidFire) return false;

        shootCooldown();
        shotsFired++;
        return true;
    }

    private void shootCooldown() {
        shotCooldown = rapidFire ? 7 : 20;
    }

    public void setFiring(boolean value) {
        if (!firing && value) {
            shotsFired = 0;
        }
        firing = value;
    }

    public void activatePowerUp(String type) {
        if (type.equals("speed")) {
            maxSpeed = baseSpeed * 2;
        } else if (type.equals("rapidfire")) {
            rapidFire = true;
        } else if (type.equals("shield")) {
            shield = true;
        }
        powerUpTimer = 300;
    }

    public void setMovingLeft(boolean value) { movingLeft = value; }
    public void setMovingRight(boolean value) { movingRight = value; }
    public boolean hasShield() { return shield; }
}
