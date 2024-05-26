package wipcode;

import java.util.LinkedList;

public class Control implements Runnable {
        //Instance fields
        private GUI gui;
        private TerrainGenerator terrainGenerator;
        private GameState gameState;
        private LinkedList<GameObject> gameObjects;

    /**
     * Constructor for control class
     */
    public Control() {
            gui = new GUI( this);
            terrainGenerator = new TerrainGenerator();
            gameState = new GameState(this);
            gameObjects = gameState.getGameObjects();
            gui.setGameState(gameState);
            gameState.setGUI(gui);
        }
    public void startRound() {
//        gameState.addGameObject(new Tank(100, 700));
//        gameState.addGameObject(new Tank(500, 700));
        gameState.addGameObject(new TerrainBlock(100, 0, 100, 100));
        gameState.addGameObject(new TerrainBlock(100, 700, 100, 100));
        gui.repaint();
    }




    @Override
    public void run() {
        startRound();
    }
}
