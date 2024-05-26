package wipcode;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    // Instance fields
    Control control;
    GameState gameState;
    public GUI (Control control) {
        this.control = control;
        this.setName("Tanks");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameState = null;
        this.setMinimumSize(new Dimension(1000, 800));
        this.setPreferredSize(new Dimension(1000, 800));
        this.setMaximumSize(new Dimension(1000, 800));
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.repaint();
    }

    /**
     * Setter for gameState
     * @param gameState
     */
    public void setGameState(GameState gameState) {
        if (gameState == null) {
            throw new IllegalArgumentException();
        }
        this.gameState = gameState;
    }

    public void paint(Graphics g) {
        System.out.println(g);
        if (gameState != null) { //Paint every gameObject
            for (GameObject gameObject : gameState.getGameObjects()) {
                gameObject.paintComponent(g);
            }
        }

    }
}
