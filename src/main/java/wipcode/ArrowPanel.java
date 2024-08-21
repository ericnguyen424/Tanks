package wipcode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class ArrowPanel extends JPanel {

    private double angle; // Angle in degrees
    private int power; // Number of arrows
    private int x; // Starting x position
    private int y; // Starting y position

    public ArrowPanel(double angle, int power, int x, int y) {
        this.angle = angle;
        this.power = power;
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(400, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < power; i++) {
            AffineTransform old = g2d.getTransform();

            // Translate to the (x, y) position for the first arrow (bottom of the chain)
            // The subsequent arrows are placed behind, based on the angle
            g2d.translate(x - i * 40 * Math.cos(Math.toRadians(angle)),
                    y - i * 40 * Math.sin(Math.toRadians(angle)));

            // Rotate each arrow based on the angle
            g2d.rotate(Math.toRadians(angle));

            // Adjust the color based on the number of arrows (power)
            int shade = 255 - (i * (255 / power)); // Gradual shading from light to dark
            g2d.setColor(new Color(shade, shade, shade));

            drawArrow(g2d);

            g2d.setTransform(old); // Reset the transformation to the original
        }
    }

    private void drawArrow(Graphics2D g2d) {
        // Define the arrow shape with a rectangular base
        int[] xPoints = {0, 0, -30, -30, -60, -30, -30};
        int[] yPoints = {10, -10, -10, -20, 0, 20, 10};
        int nPoints = 7;

        Polygon arrow = new Polygon(xPoints, yPoints, nPoints);
        g2d.fill(arrow);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rotating Arrows");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            double angle = 59; // Example angle
            int power = 5;     // Example power
            int startX = 200;  // Example starting x position
            int startY = 200;  // Example starting y position

            ArrowPanel panel = new ArrowPanel(angle, power, startX, startY);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
