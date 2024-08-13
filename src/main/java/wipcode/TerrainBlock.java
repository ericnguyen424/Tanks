package wipcode;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class TerrainBlock implements GameObject {
    private int x, y, height, width;

    /**
     * Constructor
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public TerrainBlock(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**Getter for x
     * @return
     */
    public int getX() { return this.x; }
    /**Getter for y
     * @return
     */
    public int getY() { return this.y; }

    /**Getter for x
     * @return
     */
    public int getHeight() { return this.height; }
    /**Getter for y
     * @return
     */
    public int getWidth() { return this.width; }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLUE);


        Graphics2D g2 = (Graphics2D) g;
        g2.fill(this.getArea());
    }

    /**
     * Returns the area of the terrain block
     * @return
     */
    public Area getArea() {
        Shape block = new Rectangle2D.Double(x, 800 - y - height, width, height);
        return new Area(block);
    }


}
