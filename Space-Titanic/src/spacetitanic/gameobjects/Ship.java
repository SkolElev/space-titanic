package spacetitanic.gameobjects;

import spacetitanic.GamePanel;

import java.awt.*;

public class Ship extends GameObject {
    GamePanel gamePanel;

    public Ship(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        x = (double) gamePanel.screenWidth / 2;
        y = (double) gamePanel.screenHeight / 2;
        /* "The Doritos Ship" */
        int[] xPoints = {30, -15, -8, -15};
        int[] yPoints = {0, 15, 0, -15};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);
    }

    public void update() {
        /* temporary code */
        int[] xPoints = {(int) (30 + x), (int) (-15 + x), (int) (-8 + x), (int) (-15 + x)};
        int[] yPoints = {(int) (0 + y), (int) (15 + y), (int) (0 + y), (int) (-15 + y)};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);
    }

    public void render(Graphics2D g2) {
        /* temporary code */
        g2.setColor(Color.magenta);
        g2.fillOval((int) (x - gamePanel.camera.getxOffset()), (int) (y - gamePanel.camera.getyOffset()), 20, 20);

        /* "The Doritos Ship" */
        g2.setColor(Color.red);
        g2.fill(collisionShape);
    }

    public void goLeft() {
        /* temporary code, must be improved later */
        x -= 5.5;
    }

    public void goRight() {
        /* temporary code, must be improved later */
        x += 5.5;
    }

    public void goUp() {
        /* temporary code, must be improved later */
        y -= 5.5;
    }

    public void goDown() {
        /* temporary code, must be improved later */
        y += 5.5;
    }

}
