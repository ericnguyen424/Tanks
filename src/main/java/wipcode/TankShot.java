package wipcode;


import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * Nested TankShot class
 */
public class TankShot implements GameObject {

    //Field Variables
    private int x, y;
    private double timeElapsed, initVelocityX, initVelocityY;
    private boolean hasExpired;
    private GameState gameState;
    private Control control;

    /**
     * Constructor
     * @param x
     * @param y
     */
    public TankShot(int x, int y, int power, int angle, GameState gameState, Control control) {
        this.gameState = gameState;
        this.control = control;
        this.x = x;
        this.y = y;
        timeElapsed = 0.0;
        hasExpired = false;
        this.initVelocityX = power * Math.cos(Math.toRadians(angle));
        this.initVelocityY = power * Math.sin(Math.toRadians(angle));
    }

    /**
     * Increments timeElapsed by param
     * @param timeElapsed
     */
    public void incrementTimeElapsed(double timeElapsed) {

        this.timeElapsed += timeElapsed;
        //Update X and Y
        double currentX = x + (initVelocityX * this.timeElapsed);
        double currentY = y + (-initVelocityY * this.timeElapsed) - (-0.5)*(9.8*this.timeElapsed*this.timeElapsed);

        TerrainBlock[] nearbyBlocks = control.getCurrentTerrain().findNearest((int) currentX, gameState.getGui());

        for (TerrainBlock block : nearbyBlocks) {
            //if the shot leaves the screen or hits a block, expire it
            if (block == null) {
                //Run if the tankShot is not above the screen
                if (currentY > 0) {
                    if (gameState.leavesScreen(this.getArea())) {
                        this.setHasExpired(true);
                        return;
                    }
                }

            }
            else if (gameState.intersects(this.getArea(), block.getArea())) {
                this.setHasExpired(true);
                return;
            }
        }
        //if the shot intersects with any tank, expire it
        for (Tank tank : control.getTanks()) {
            if (gameState.intersects(this.getArea(), tank.getArea()) && this.timeElapsed > 0.2) {
                tank.setHasExpired(true);
                this.setHasExpired(true);
                return;
            }
        }

    }

    /**
     * Mutator for hasExpired
     * @param status
     */
    public void setHasExpired(boolean status) {
        this.hasExpired = status;
    }

    /**Setter for x
     * @return
     */
    public void setX(int x) { this.x = x; }
    /**Setter for y
     * @return
     */
    public void setY(int y) { this.y = y; }
    /**Getter for x
     * @return
     */
    public int getX() { return this.x; }
    /**Getter for y
     * @return
     */
    public int getY() { return this.y; }
    @Override
    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.fill(this.getArea());
    }

    /**
     * Accessor for hasExpired
     * @return
     */
    public boolean hasExpired() {
        return this.hasExpired;
    }
    /**
     * Creates and returns the area of this cannonBall
     * @return
     */
    public Area getArea() {
        Shape cannonBall = new Ellipse2D.Double(x + (initVelocityX * timeElapsed),
                y + (-initVelocityY * timeElapsed) - (-0.5)*(9.8*timeElapsed*timeElapsed), 10, 10);
        return new Area(cannonBall);
    }




}