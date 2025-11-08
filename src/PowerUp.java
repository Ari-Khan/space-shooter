import java.awt.*;

public class PowerUp extends Element {
    private final String type;

    public PowerUp(int x, int y, String type) {
        super(x, y, 25, 25, 3);
        this.type = type;
    }

    @Override
    public void update() {
        y += speed;
    }

    @Override
    public void draw(Graphics g) {
        if (type.equals("speed")) {
            g.setColor(Color.GREEN);
        } else if (type.equals("rapidfire")) {
            g.setColor(Color.ORANGE);
        } else if (type.equals("shield")) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.GRAY);
        }
        g.fillOval(x, y, width, height);
    }

    public String getType() {
        return type;
    }
}
