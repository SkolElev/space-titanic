package spacetitanic.gameobjects.projectiles;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private int damage, range, rangeCounter = 0;
    private double deltaX, deltaY;

    public Bullet(GamePanel gamePanel, double x, double y, double direction, double speed, int damage, int range, BufferedImage bulletImage) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.images[0] = bulletImage;
        int[] xPoints = {(int) (0 * gamePanel.scaleX), (int) (4 * gamePanel.scaleX), (int) (6 * gamePanel.scaleX), (int) (4 * gamePanel.scaleX), (int) (0 * gamePanel.scaleX)};
        int[] yPoints = {(int) (-1 * gamePanel.scaleY), (int) (-1 * gamePanel.scaleY), (int) (0 * gamePanel.scaleY), (int) (1 * gamePanel.scaleY), (int) (1 * gamePanel.scaleY)};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);
        objectTransform = new AffineTransform();
        objectTransform.translate(x, y);
        deltaX = Math.cos(Math.toRadians(direction) * speed);
        deltaY = Math.sin(Math.toRadians(direction) * speed);
    }

    @Override
    public void update() {
        rangeCounter += speed;
        if (rangeCounter < range) {
            x = x + deltaX;
            y = y + deltaY;
        } else {
            dead = true;
        }
    }

    @Override
    protected void renderObject(Graphics2D g2, double positionX, double positionY) {
        super.renderObject(g2, positionX, positionY);
    }
}
