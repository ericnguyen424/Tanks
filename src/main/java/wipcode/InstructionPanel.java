package wipcode;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class InstructionPanel extends JDialog {
    //Field Variables

    private JFrame parentFrame;
    private ArrayList<GameObject> instructionGameObjects;
    //from the main panel

    /**
     * Constructor for Instruction Panel
     * @param parentFrame - Frame that this panel will build from
     */
    public InstructionPanel(JFrame parentFrame) {
        //Spawning gameObjects
        instructionGameObjects = new ArrayList<GameObject>();
        spawnTerrain();

        //JPanel Instructions
        this.parentFrame = parentFrame;
        this.setTitle("Instructions");
        this.setModal(true);
        this.setSize(800, 400);
        this.setLocationRelativeTo(parentFrame);
        System.out.println("Here 0");
        this.setVisible(true);
        this.setFocusable(true);  // Make the GUI focusable
        this.requestFocusInWindow();  // Set focus to the GUI
        this.setFocusTraversalKeysEnabled(true);

        System.out.println("Here 1");
        this.repaint();
    }

    /**
     * When called, this function adds a new TerrainBlock that starts at (0, 0)
     * that is 1/4 of the panel's height and spans across the entire panel
     */
    private void spawnTerrain() {
        this.instructionGameObjects.add(new TerrainBlock(10, 10, 100, 100, this.getHeight()));
//        this.instructionGameObjects.add(new TerrainBlock(
//                0, this.getHeight(), (int)
//                (this.getHeight() * 0.25), this.getWidth()));
        System.out.println("Spawned!");
    }

    private void spawnTank() {

    }

    /**
     * When called, this function draws every gameObject held in instructionGameObjects to the screen
     * @param g
     */
    public void paint(Graphics g) {
        super.paint(g);
        for (GameObject go : this.instructionGameObjects) {
            System.out.println("Paint being called");
            go.paintComponent(g);
        }

    }
}
