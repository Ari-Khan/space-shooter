import java.awt.*;

public abstract class Element {
    protected int x, y, width, height, speed;

    public Element(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public abstract void update();
    public abstract void draw(Graphics g);

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isOffScreen(int screenHeight) {
        return y > screenHeight || y + height < 0;
    }
}
