package wipcode;

import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {
    // Instance fields
    Control control;
    GameState gameState;
    public GUI (Control control) {
        this.control = control;
        this.gameState = null;

        JFrame frame = new JFrame("Tanks");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        this.setMinimumSize(new Dimension(1000, 800));
        this.setPreferredSize(new Dimension(1000, 800));
        this.setMaximumSize(new Dimension(1000, 800));

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        this.setFocusable(true);  // Make the GUI focusable
        this.requestFocusInWindow();  // Set focus to the GUI
        this.setFocusTraversalKeysEnabled(true);



        repaint();
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

    public void paintComponent(Graphics g) {
//        super.paintComponents(g);
        if (gameState != null) { //Paint every gameObject
            for (GameObject gameObject : gameState.getGameObjects()) {
                gameObject.paintComponent(g);
            }
            g.drawString("Right Player Points: " + gameState.getRightPlayerPoints(), 0, 10);
            g.drawString("Left Player Points: " + gameState.getLeftPlayerPoints(), 0, 30);

        }

    }
}
