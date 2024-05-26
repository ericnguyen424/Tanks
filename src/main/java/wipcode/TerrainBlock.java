package wipcode;

import java.awt.*;

public class TerrainBlock implements GameObject {
    private int x, y, height, width;
    public TerrainBlock(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);


        g.fillRect(x, 800 - y - height, width, height);
    }
}
