package wipcode;

import java.awt.*;

public class Tank implements GameObject {
    int x, y, width, height;
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        width = 100;
        height = 100;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }
}
