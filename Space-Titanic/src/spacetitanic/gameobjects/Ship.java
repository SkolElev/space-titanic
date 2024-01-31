package spacetitanic.gameobjects;

import spacetitanic.GamePanel;

import java.awt.*;

public class Ship {
    GamePanel gamePanel;

    public Ship(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void update() {

    }

    public void render(Graphics2D g2) {
        /* temporary code */
        g2.setColor(Color.magenta);
        g2.fillOval(100, 100, 20, 20);
    }

}
