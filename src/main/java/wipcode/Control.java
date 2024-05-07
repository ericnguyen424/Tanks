package wipcode;

public class Control implements Runnable {
        //Instance fields
        private GUI gui;
        private TerrainGenerator terrainGenerator;

    /**
     * Constructor for control class
     */
    public Control() {
            gui = new GUI();
            terrainGenerator = new TerrainGenerator();
        }



    @Override
    public void run() {

    }
}
