package wipcode;

import java.awt.*;
import java.awt.geom.*;

public class Tank implements GameObject {
    private int x, y, width, height, angle, power;
    private boolean isRight, isShooting, hasExpired;
    private GameState gameState;
    private Control control;
    private Point barrelEnd;
    public Tank(int x, int y, boolean isRight, GameState gameState, Control control) {
        this.hasExpired = false;
        this.gameState = gameState;
        this.control = control;
        this.x = x;
        this.y = y;
        width = 50;
        height = 50;
        angle = 90;
        power = 50;
        this.isRight = isRight;
        isShooting = false;
        barrelEnd = null;
    }

    /**
     * Accessor for isRight
     * @return
     */
    public boolean isRight() {
        return this.isRight;
    }



    /**
     * Accessor for hasExpired
     * @return
     */
    public boolean hasExpired() {
        return this.hasExpired;
    }

    /**
     * Mutator for hasExpired
     * @param status
     */
    public void setHasExpired(boolean status) {
        this.hasExpired = status;
    }
    /**
     * Accessor for x
     * @return
     */
    public int getX() {return this.x;}

    /**
     * Accessor for y
     * @return
     */
    public int getY() {return this.y;}
    /**
     * Accessor for angle
     * @return
     */
    public int getAngle() {
        return this.angle;
    }

    /**
     * Accessor for power
     * @return
     */
    public int getPower() {
        return this.power;
    }

    /**
     * Mutator for angle
     * @param increment
     * @return
     */
    public void incrementAngle(int increment) {
        this.angle += increment;
    }
    /**
     * Mutator for power
     * @param increment
     * @return
     */
    public void incrementPower(int increment) {
        this.power += increment;
    }
    public void paintComponent(Graphics g) {
        //drawing in the tank
//        g.setColor(Color.BLACK);
//        g.fillOval(x - (width / 2), 800 - y - height, width, height);
//
//        paintBarrel(g);
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.fill(this.getArea());

    }

    /**
     * Returns the area of the tank
     * @return
     */
    public Area getArea() {
//        Shape tank = new Ellipse2D.Double(x - (width / 2), 800 - y - height, width, height);
//        Shape barrel = new Line2D.Double(x - (width / 2.0) + (width / 2.0), 800 - y - (height / 2.0),
//                (x - (width / 2.0) + (width / 2.0)) - (power * Math.cos(Math.toRadians(angle))),
//                (800 - y - (height / 2.0)) - power * Math.sin(Math.toRadians(angle))
//        );
//        Area tankArea = new Area(tank);
//        tankArea.add(new Area(barrel));
//        return tankArea;

        // Tank as an ellipse
        Shape tank = new Ellipse2D.Double(x - (width / 2), 800 - y - height, width, height);

        // Barrel as a thin rectangle
        double barrelWidth = power; // Length of the barrel
        double barrelHeight = 5; // Thickness of the barrel, can be adjusted
        double barrelX = x - (width / 2.0) + (width / 2.0);
        double barrelY = 800 - y - (height / 2.0);

        // Create a thin rectangle to represent the barrel
        Shape barrel = new Rectangle2D.Double(barrelX, barrelY - (barrelHeight / 2.0), barrelWidth, barrelHeight);

        // Rotate the barrel to the correct angle
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(-angle), barrelX, barrelY);
        Shape rotatedBarrel = transform.createTransformedShape(barrel);

        // Combine the tank and barrel into one Area
        Area tankArea = new Area(tank);
        tankArea.add(new Area(rotatedBarrel));
        // To find the coordinates of the barrel's end after rotation

        Point2D barrelEnd = new Point2D.Double(barrelX + barrelWidth, barrelY);
        Point2D transformedBarrelEnd = transform.transform(barrelEnd, null);

        double barrelEndX = transformedBarrelEnd.getX();
        double barrelEndY = transformedBarrelEnd.getY();
        this.barrelEnd = new Point((int) barrelEndX, (int) barrelEndY);

        return tankArea;

    }

    /**
     * Returns the area of the tank of future angle
     * @return
     */
    public Area getFutureArea(int angleIncrement, int powerIncrement) {
        Shape tank = new Ellipse2D.Double(x - (width / 2), 800 - y - height, width, height);
        // Barrel as a thin rectangle
        double barrelWidth = power + powerIncrement; // Length of the barrel
        double barrelHeight = 5; // Thickness of the barrel, can be adjusted
        double barrelX = x - (width / 2.0) + (width / 2.0);
        double barrelY = 800 - y - (height / 2.0);

        // Create a thin rectangle to represent the barrel
        Shape barrel = new Rectangle2D.Double(barrelX, barrelY - (barrelHeight / 2.0), barrelWidth, barrelHeight);

        // Rotate the barrel to the correct angle
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(-angle - angleIncrement), barrelX, barrelY);
        Shape rotatedBarrel = transform.createTransformedShape(barrel);

        // Combine the tank and barrel into one Area
        Area tankArea = new Area(tank);
        tankArea.add(new Area(rotatedBarrel));

        return tankArea;
    }

    /**
     * Accessor for isShooting
     * @return
     */
    public boolean isShooting() {
        return this.isShooting;
    }

    /**
     * Mutator for isShooting
     * @param status
     */
    public void setIsShooting(boolean status) {
        this.isShooting = status;
    }

    /**
     * Spawns a new tankShot in at the current barrel's coordinates
     * @return
     */
    public TankShot spawnShot() {
        this.isShooting = true;
        return new TankShot(this.barrelEnd.x - 5, this.barrelEnd.y - 5, this.power, this.angle, gameState, control);
    }

}
