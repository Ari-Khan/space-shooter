import java.awt.*;

public class Enemy extends Element {
    public Enemy(int x, int y) {
        super(x, y, 50, 40, 2);
    }

    @Override
    public void update() {
        y += speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int radius = width / 2;
        int[] hexX = new int[6];
        int[] hexY = new int[6];
        for (int i = 0; i < 6; i++) {
            hexX[i] = (int) (centerX + radius * Math.cos(Math.toRadians(60 * i)));
            hexY[i] = (int) (centerY + radius * Math.sin(Math.toRadians(60 * i)));
        }
        g.fillPolygon(hexX, hexY, 6);
    }
}
