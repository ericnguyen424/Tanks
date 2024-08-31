package wipcode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.function.Function;

public class GameState {
    //Instance fields
    private LinkedList<GameObject> gameObjects;
    private LinkedTree terrainBlocks;
    private Control control;
    private GUI gui;
    private int rightPlayerPoints, leftPlayerPoints;
    private Wind wind;

    public GameState(Control control) {
        this.control = control;
        gameObjects = new LinkedList<>();
        rightPlayerPoints = 0;
        leftPlayerPoints = 0;
        gui = null;
        this.wind = new Wind(this);
    }

    /**
     * When called, this function generates a new wind for gameState
     */
    public void newWind() {
        this.wind = new Wind(this);
    }


    /**
     * Accessor for wind
     * @return
     */
    public Wind getWind() {
        return this.wind;
    }

    /**
     * Mutator for GUI
     * @param gui
     */
    public void setGUI(GUI gui) {
        if (gui == null) {
            throw new IllegalArgumentException();
        }
        this.gui = gui;
    }

    /**
     * Accessor for rightPlayerPoints
     * @return
     */
    public int getRightPlayerPoints() {
        return this.rightPlayerPoints;
    }

    /**
     * Accessor for leftPlayerPoints
     * @return
     */
    public int getLeftPlayerPoints() {
        return this.leftPlayerPoints;
    }

    /**
     * Mutator for rightPlayerPoints
     * @param increment
     */
    public void incrementRightPlayerPoints(int increment) {
        this.rightPlayerPoints += increment;
    }

    /**
     * Mutator for leftPlayerPoints
     * @param increment
     */
    public void incrementLeftPlayerPoints(int increment) {
        this.leftPlayerPoints += increment;
    }
    /**
     * Accessor for Game Objects
     * @return
     */
    public LinkedList<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    /**
     * Adder for gameObject
     * @param gameObject
     */
    public void addGameObject(GameObject gameObject) {
        //check to see if legal input
        if (gameObject == null) {
            throw new IllegalArgumentException();
        } //If it is a Tank or  TerrainBlock, we add it to the end of the list
        else if (gameObject instanceof Tank || gameObject instanceof TerrainBlock) {
            gameObjects.addLast(gameObject);

        } //Else if it is a TankShot, add it to the beginning of the list
        else {
            gameObjects.addFirst(gameObject);
        }
        gui.repaint();
    }

    /**
     * Remover for gameObjects
     * @return
     */
    public GameObject removeGameObject() {
        return gameObjects.removeFirst();
    }

    /**
     * Remover for gameObjects
     * @return
     */
    public void removeAllGameObjects() {
        while (peekGameObjects() != null) {
            removeGameObject();
        }

    }

    /**
     * Mutator for Terrain Blocks Linked Tree
     * @param linkedTree
     */
    public void setTerrainBlocks(LinkedTree linkedTree) {
        this.terrainBlocks = linkedTree;
    }


    /**
     * Peeks at gameObject
     */
    public GameObject peekGameObjects() {
        return gameObjects.peek();
    }

    /**
     * Given two areas, determines if Area1 intersects Area2
     * @param areaOne
     * @param areaTwo
     * @return boolean true or false
     */
    public boolean intersects(Area areaOne, Area areaTwo) {
        if (areaOne == null || areaTwo == null) {
            throw new IllegalArgumentException();
        }
        //Get the area of the two objects
        Area intersection = (Area) areaOne.clone();
        intersection.intersect(areaTwo);
        //The intersect function creates a new object that contains the space they both occupy
        return !intersection.isEmpty();
    }

    /**
     * Given an area, checks if it leaves the screen
     * @param objectArea
     * @return boolean true or false
     */
    public boolean leavesScreen(Area objectArea) {
        if (objectArea == null) {
            throw new IllegalArgumentException();
        }


        Area areaClone = (Area) objectArea.clone();
        //Screen's area
        Area screen = new Area(new Rectangle2D.Double(0, 0, gui.getWidth(), gui.getHeight()));

        areaClone.intersect(screen);
        //If they are the same, that means the tank hasn't left the screen, return false
        //If they aren't equivalent, that means the tank has left the screen, return true
        return !objectArea.getBounds().equals(areaClone.getBounds());

    }

//    /**
//     * Given a tank, checks if it leaves the screen
//     * @param tankArea
//     * @return boolean true or false
//     */
//    public boolean leavesScreen(Area tankArea) {
//        if (tankArea == null) {
//            throw new IllegalArgumentException();
//        }
//        //Screen's area
//
//        Area intersectedTankArea = (Area) tankArea.clone();
//
//        Area screen = new Area(new Rectangle2D.Double(0, 0, gui.getWidth(), gui.getHeight()));
//
//        intersectedTankArea.intersect(screen);
//        //If they are the same, that means the tank hasn't left the screen, return false
//        //If they aren't equivalent, that means the tank has left the screen, return true
//        return !tankArea.getBounds().equals(intersectedTankArea.getBounds());
//
//    }

    /**
     * This function will return true if the tank can perform the action without leaving the screen or hitting a block
     * Actions:
     * "LEFT" moves the barrel left
     * "RIGHT" moves the barrel right
     * "UP" moves the barrel up
     * "DOWN" moves the barrel down
     * @param nearbyBlocks
     * @param tank
     * @param action
     */
    public boolean checkTankAction(TerrainBlock[] nearbyBlocks, Tank tank, String action) {
        if (tank == null || !(action.equalsIgnoreCase("left") || action.equalsIgnoreCase("right")
                || action.equalsIgnoreCase("down") || action.equalsIgnoreCase("up"))) {
            throw new IllegalArgumentException("Illegal input! Check the tank for null or the action.");
        }

        //Hash Table that gets the area once called
        action = action.toLowerCase();
        Hashtable<String, Area> functionTable = new Hashtable<>();
        functionTable.put("left", tank.getFutureArea(3, 0));
        functionTable.put("right", tank.getFutureArea(-3, 0));
        functionTable.put("down", tank.getFutureArea(0, -5));
        functionTable.put("up", tank.getFutureArea(0, 5));

        for (TerrainBlock block : nearbyBlocks) { //if the tank leaves the screen
            if (block == null) {
                if (leavesScreen(functionTable.get(action))) {
                    System.out.println("Returning False 1");
                    return false;
                }
            } else { //if the action will result in the tank intersecting a block, return false
                if (this.intersects(functionTable.get(action), block.getArea())) {
                    System.out.println("Returning False 2");
                    return false;
                }
            }


        }

        return true;
    }

    /**
     * Given a range, spawns in the tank and returns
     * @return
     */
    public Tank generateTank(int[] range, boolean isRight) {
        //illegal argument checks
        if (range.length > 2) {
            throw new IllegalArgumentException("Invalid array length!");
        } else if (this.terrainBlocks == null) {
            throw new RuntimeException("Too early, linked tree not yet set.");
        }
        else {
            for (int i : range) {
                if (i < 0 || i > 10)  { //10 because the width is 1000
                    throw new IllegalArgumentException("Number(s) are out of bounds!");
                }
            }
        }
        //first generate a random number starting at (a (inclusive), b (exclusive)
        //multiply number by 100
        Random random = new Random();
        int x = random.nextInt(range[0], range[1]);
        int y = 0;
        x *= 100;
        //check to see if there is a block there, if there is, add height of block + height of tank to y
        TerrainBlock testSpot = terrainBlocks.search(x);
        if (testSpot != null) {
            y += testSpot.getHeight();
        }
        //add 50 to the x
        x += 50;
        //create the tank and return it
        return new Tank(x, y, isRight, this, control);


    }

    /**
     * Accessor for GUI
     * @return
     */
    public GUI getGui() {
        return this.gui;
    }
}
