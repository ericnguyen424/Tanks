package wipcode;

import java.util.LinkedList;

public class GameState {
    //Instance fields
    private LinkedList<GameObject> gameObjects;
    Control control;
    GUI gui;

    public GameState(Control control) {
        this.control = control;
        gameObjects = new LinkedList<>();
        gui = null;
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
    }

    /**
     * Remover for gameObjects
     * @return
     */
    public GameObject removeGameObject() {
        return gameObjects.remove();
    }

    /**
     * Peeks at gameObject
     */
    public GameObject peekGameObjects() {
        return gameObjects.peek();
    }

}
