import java.awt.*;

public class Shot extends Element {
    public Shot(int x, int y) {
        super(x, y, 4, 12, 10);
    }

    @Override
    public void update() {
        y -= speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }
}
