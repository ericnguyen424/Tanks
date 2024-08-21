package wipcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;


public class Control implements Runnable, KeyListener, ActionListener {
        //Instance fields
        private GUI gui;
        private TerrainGenerator terrainGenerator;
        private GameState gameState;
        private LinkedList<GameObject> gameObjects;
        private Tank[] tanks;
        private boolean leftPlayerTurn;
        LinkedTree currentTerrain;
        private Timer timer;
        //for testing purposes
//        private Point loc = null;
    /**
     * Constructor for control class
     */
    public Control() {
            gui = new GUI( this);
            gameState = new GameState(this);
            terrainGenerator = new TerrainGenerator(gameState);
            gameObjects = gameState.getGameObjects();
            gui.setGameState(gameState);
            gameState.setGUI(gui);
            gui.addKeyListener(this);
            this.tanks = new Tank[2];
            timer = new Timer(5, this);

    }


    public void startRound() {

        gameState.removeAllGameObjects();
        currentTerrain = terrainGenerator.generateTerrain();
        tanks[0] = gameState.generateTank(new int[] {0, 5}, false);
        tanks[1] = gameState.generateTank(new int[] {5, 10}, true);
        gameState.addGameObject(tanks[0]);
        gameState.addGameObject(tanks[1]);
        leftPlayerTurn = true;
        //for testing
//        gui.addMouseListener(this);
//        gui.addMouseMotionListener(this);
        timer.start();
    }

    /**
     * Accessor for tanks
     * @return
     */
    public Tank[] getTanks() {
        return this.tanks;
    }


    /**
     * Accessor for current terrain
     * @return
     */
    public LinkedTree getCurrentTerrain() {
        return this.currentTerrain;
    }



    @Override
    public void run() {

        startRound();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Handling Tank Barrel Adjusters
        // ********HANDLING FOR LEFT PLAYER********

        if (!tanks[0].isShooting() && !tanks[1].isShooting()) { //if neither tank is shooting

            if (leftPlayerTurn && tanks[0] != null) { //if it is the left player's turn and the tank exists
                TerrainBlock[] closestBlocks = currentTerrain.findNearest(tanks[0].getX(), gui);
                //*******IF PLAYER PRESSED A********
                //If the barrel will remain on the screen, increment
                if (e.getKeyCode() == KeyEvent.VK_A && gameState.checkTankAction(closestBlocks, tanks[0], "left")) {
                    tanks[0].incrementAngle(3);

                    //*******IF PLAYER PRESSED D********
                    //If the barrel will remain on the screen, increment

                } else if (e.getKeyCode() == KeyEvent.VK_D && gameState.checkTankAction(closestBlocks, tanks[0], "right")) {
                    tanks[0].incrementAngle(-3);
                }
                //*******IF PLAYER PRESSED W********
                //If power + 5 is below 125, increment by 5
                else if (e.getKeyCode() == KeyEvent.VK_W && gameState.checkTankAction(closestBlocks, tanks[0], "up")
                        && tanks[0].getPower() + 5 <= 125) {
                    tanks[0].incrementPower(5);
                }

                //*******IF PLAYER PRESSED S********
                //If power - 5 is above 30, decrement by 5
                else if (e.getKeyCode() == KeyEvent.VK_S && gameState.checkTankAction(closestBlocks, tanks[0], "down")
                        && tanks[0].getPower() - 5 >= 30) {
                    tanks[0].incrementPower(-5);
                }

            }
            //*******HANDLING FOR RIGHT PLAYER********
            else if (!leftPlayerTurn && tanks[1] != null) { //if it is the right player's turn and the tank exists
                TerrainBlock[] closestBlocks = currentTerrain.findNearest(tanks[1].getX(), gui);
                //*******IF PLAYER PRESSED LEFT********
                //If the barrel will remain on the screen, increment
                if (e.getKeyCode() == KeyEvent.VK_LEFT && gameState.checkTankAction(closestBlocks, tanks[1], "left")) {
                    tanks[1].incrementAngle(3);

                    //*******IF PLAYER PRESSED RIGHT********
                    //If the barrel will remain on the screen, increment

                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && gameState.checkTankAction(closestBlocks, tanks[1], "right")) {
                    tanks[1].incrementAngle(-3);
                }
                //*******IF PLAYER PRESSED UP********
                //If power + 5 is below 125, increment by 5
                else if (e.getKeyCode() == KeyEvent.VK_UP && gameState.checkTankAction(closestBlocks, tanks[1], "up")
                        && tanks[1].getPower() + 5 <= 125) {
                    tanks[1].incrementPower(5);
                }

                //*******IF PLAYER PRESSED DOWN********
                //If power - 5 is above 30, decrement by 5
                else if (e.getKeyCode() == KeyEvent.VK_DOWN && gameState.checkTankAction(closestBlocks, tanks[1], "down")
                        && tanks[1].getPower() - 5 >= 30) {
                    tanks[1].incrementPower(-5);
                }

            }

            //if a player has shot
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                //if left player shot, spawn a shot from left tank
                if (this.leftPlayerTurn) {
                    gameState.addGameObject(tanks[0].spawnShot());
                } else { //right player shot, spawn a shot from right tank
                    gameState.addGameObject(tanks[1].spawnShot());
                }
                //reverse the turns
                this.leftPlayerTurn = !leftPlayerTurn;
                //spawn in a new wind
            }
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameObject go = gameState.peekGameObjects();

        //If the gameObject is a tankShot, increment timeElapsed
        //if the tankShot has expired, remove it
        if (gameState.peekGameObjects() instanceof TankShot) {
            ((TankShot) go).incrementTimeElapsed(.05);
            if (((TankShot) go).hasExpired()) {
                gameState.removeGameObject();
                for (Tank tank : tanks) {
                    //TODO: if either of the tanks has been hit, perform ACTION
                    if (tank.hasExpired()) {
                        //If it is the left player's turn
                        if (leftPlayerTurn) {
                            //if the tank hit themselves
                            if (!tank.isRight() && tank.hasExpired()) {
                                gameState.incrementRightPlayerPoints(1);
                            }
                            //if the tank hit the enemy
                            else if (tank.isRight() && tank.hasExpired()) {
                                gameState.incrementLeftPlayerPoints(1);
                            }

                        } else if (!leftPlayerTurn) {
                            //if the tank hit themselves
                            if (tank.isRight() && tank.hasExpired()) {
                                gameState.incrementLeftPlayerPoints(1);
                            }
                            //if the tank hit the enemy
                            else if (!tank.isRight() && tank.hasExpired()) {
                                gameState.incrementRightPlayerPoints(1);
                            }

                        }
                    }
                    gameState.newWind();
                    tank.setIsShooting(false);
                }

            }

        }


        for (Tank tank : tanks) {
            if (tank.hasExpired()) {
                timer.stop();
                startRound();
            }
        }

        if (gameState.getRightPlayerPoints() == 5 || gameState.getLeftPlayerPoints() == 5) {
            System.out.println("A player has won!!");
        }
        gui.repaint();
    }
}
