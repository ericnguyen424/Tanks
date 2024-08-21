package wipcode;


import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * Nested TankShot class
 */
public class TankShot implements GameObject {

    //Field Variables
    private double initialX, initialY, currentX, currentY;
    private double motionMultiplier, initVelocityX, initVelocityY;
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
        this.currentX = x;
        this.currentY = y;
        this.initialX = x;
        this.initialY = y;
        motionMultiplier = 0.0;
        hasExpired = false;
        this.initVelocityX = power * Math.cos(Math.toRadians(angle));
        this.initVelocityY = power * Math.sin(Math.toRadians(angle));
    }

    /**
     * Increments motionMultiplier by param
     * @param motionMultiplierIncrement
     */
    public void incrementTimeElapsed(double motionMultiplierIncrement) {
        this.motionMultiplier += motionMultiplierIncrement;
        //Update X and Y
        currentX = initialX + (initVelocityX * this.motionMultiplier);
        currentY = initialY + (-initVelocityY * this.motionMultiplier) - (-0.5)*(9.8*this.motionMultiplier*this.motionMultiplier);


        //if the motionMultiplier is higher than 0.2, start adding wind
        if (motionMultiplier > 0.2) {
            currentX += gameState.getWind().getXVelocity() * motionMultiplier;
            currentY -=  gameState.getWind().getYVelocity() * motionMultiplier;
        }
        TerrainBlock[] nearbyBlocks = control.getCurrentTerrain().findNearest((int) currentX, gameState.getGui());
        //This for loop analyzes the LEFTMOST | MIDDLE | RIGHTMOST blocks
        for (TerrainBlock block : nearbyBlocks) {
            //If the tankShot is above the screen but leaves the border, expire it
             if (currentY <= 0 && (currentX >= gameState.getGui().getWidth() || currentX <= 0)) {
                this.setHasExpired(true);
                return;
            }
            //if the current block is null, check if the block has hit the ground
            else if (block == null) {
                //Run if the tankShot is not above the screen
                if (currentY > 0) {
                    //if the tankShot has left the screen
                    if (gameState.leavesScreen(this.getArea())) {
                        this.setHasExpired(true);
                        return;
                    }
                }

            }
            //If the current block exists, check if the tankShot intersects with it
            else if (gameState.intersects(this.getArea(), block.getArea())) {
                this.setHasExpired(true);
                return;
            }
        }
        //if the shot intersects with any tank, expire it
        for (Tank tank : control.getTanks()) {
            if (gameState.intersects(this.getArea(), tank.getArea()) && this.motionMultiplier > 0.2) {
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

    public double getX() { return this.currentX; }
    /**Getter for y
     * @return
     */
    public double getY() { return this.currentY; }
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
        Shape cannonBall = new Ellipse2D.Double(currentX,
                currentY, 10, 10);
        return new Area(cannonBall);
    }




}