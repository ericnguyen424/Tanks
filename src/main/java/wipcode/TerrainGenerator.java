package wipcode;
import java.util.Random;
public class TerrainGenerator {

    private GameState gameState;
    private TerrainBlock offScreenBlock;
    public TerrainGenerator(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * This function creates and returns a LinkedTree containing 5 blocks of terrain
     * @return
     */
    public LinkedTree generateTerrain() {
        offScreenBlock = new TerrainBlock(0, -10, 10, this.gameState.getGui().getWidth());
        LinkedTree returnTree = new LinkedTree();
        //I want to generate 5 random blocks on the terrain.
        //for loop that loops 5 times
        Random random = new Random();
        for (int i = 0; i < 7; i ++) {
            //generate a random x coordinate
            int x;
            do {
                x = random.nextInt(0, 10); //if duplicate, then regenerate x coordinate
                x *= 100;
            } while(returnTree.search(x) != null);
            //generate random y coordinate
            int height = random.nextInt(1, 7);
            height *= 100;
            TerrainBlock newBlock = new TerrainBlock(x, 0, height, 100);
            returnTree.insert(x, newBlock); //add to the binary tree
            //add a new terrainblock to gameObjects
            gameState.addGameObject(newBlock);

        }

        gameState.setTerrainBlocks(returnTree);
        return returnTree;
    }

    /**
     * Accessor for screen block
     * @return
     */
    public TerrainBlock getOffScreenBlock() {
        return this.offScreenBlock;
    }

}
