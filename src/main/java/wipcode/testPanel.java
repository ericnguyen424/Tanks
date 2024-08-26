package wipcode;

import javax.swing.*;
import java.awt.*;

public class testPanel {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Main Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a JPanel and add it to the frame
        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN);
        frame.add(panel);

        // Create a button to open the small window
        JButton button = new JButton("Open Small Window");
        button.addActionListener(e -> openSmallWindow(frame));
        panel.add(button);

        // Show the main frame
        frame.setVisible(true);
    }

    private static void openSmallWindow(JFrame parentFrame) {
        // Create a JDialog as a small window
        JDialog smallWindow = new JDialog(parentFrame, "Small Window", true);
        smallWindow.setSize(200, 150);
        smallWindow.setLocationRelativeTo(parentFrame); // Center relative to parent

        // Add content to the small window
        JLabel label = new JLabel("This is a small window", SwingConstants.CENTER);
        smallWindow.add(label);

        // Show the small window
        smallWindow.setVisible(true);
    }
}
