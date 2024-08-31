package wipcode;
import javax.swing.*;
import java.awt.*;


public class InstructionPanel extends JDialog {
    //Field Variables
    private JFrame parentFrame;
    public InstructionPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.setTitle("Instructions");
        this.setModal(true);
        this.setSize(400, 400);
        this.setLocationRelativeTo(parentFrame);
        this.setVisible(true);
    }
}
