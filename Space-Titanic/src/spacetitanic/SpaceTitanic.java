package spacetitanic;

import javax.swing.*;
import java.awt.*;

public class SpaceTitanic {
    public SpaceTitanic() {
        JFrame frame = new JFrame("Space-Titanic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);

        /* "My own code" - Should be removed at a later time */
        /*frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel temporaryPanel = new JPanel(new GridBagLayout());
        temporaryPanel.setBackground(Color.black);
        temporaryPanel.add(new GamePanel());
        frame.add(temporaryPanel);*/

        frame.add(new GamePanel()); // Default: Uncommented
        frame.pack(); // Default: Uncommented
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SpaceTitanic();
    }

}
