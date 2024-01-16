package spacetitanic;

import javax.swing.*;

public class SpaceTitanic {
    public SpaceTitanic() {
        JFrame frame = new JFrame("Space-Titanic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GamePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SpaceTitanic();
    }

}
