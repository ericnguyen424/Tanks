package wipcode;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Random;

public class Wind implements GameObject {
    private final int power, angle;
    private final double xVelocity, yVelocity;
    private final GameState gameState;
    public Wind(GameState gameState) {
        this.gameState = gameState;
        Random random = new Random();
        this.angle = random.nextInt(0, 360) + 90;
        this.power = random.nextInt(1, 10);
        this.yVelocity = (power * Math.cos(Math.toRadians(this.angle - 90)));
        this.xVelocity = (power * Math.sin(Math.toRadians(this.angle - 90)));

    }

    /**
     * Accessor for angle
     * @return angle
     */
    public int getAngle() {
        return this.angle;
    }

    /**
     * Accessor for power
     * @return power
     */
    public int getPower() {
        return this.power;
    }

    /**
     * Accessor for X Velocity
     * @return double velocity in x direction
     */
    public double getXVelocity() {
        return this.xVelocity;
    }

    /**
     * Accessor for Y Velocity
     * @return double velocity in y direction
     */
    public double getYVelocity() {
        return this.yVelocity;
    }

    /**
     * Paints in the arrows indicating wind
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = gameState.getGui().getWidth() - 100;
        int y = 100;
        for (int i = 0; i < power; i++) {
            AffineTransform old = g2d.getTransform();

            // Translate to the (x, y) position for the first arrow (bottom of the chain)
            // The subsequent arrows are placed behind, based on the angle
            g2d.translate(x - i * 10 * Math.cos(Math.toRadians(angle)),
                    y - i * 10 * Math.sin(Math.toRadians(angle)));

            // Rotate each arrow based on the angle
            g2d.rotate(Math.toRadians(angle));
            int shade;
            //If there is only one arrow, make it dark
            if (power == 1) {
                shade = 255;
            } else {
                // Adjust the color based on the number of arrows (power)
                shade = 255 - (i * (255 / power)); // Gradual shading from light to dark
                g2d.setColor(new Color(shade, shade, shade));
            }


            drawArrow(g2d);

            g2d.setTransform(old); // Reset the transformation to the original

            g.drawString("Current Wind: " + "\nX Velocity: " + gameState.getWind().getXVelocity() + " Y Velocity: "
                    + gameState.getWind().getYVelocity(), 0, 50);
        }
    }

    private void drawArrow(Graphics2D g2d) {
        // Define the arrow shape with a rectangular base
        int[] xPoints = {0, 0, -7, -7, -15, -7, -7};
        int[] yPoints = {2, -2, -2, -4, 0, 4, 2};
        int nPoints = 7;

        Polygon arrow = new Polygon(xPoints, yPoints, nPoints);
        g2d.fill(arrow);
    }


    @Override
    public Area getArea() {
        return null;
    }
}
