package spacetitanic.gameobjects.projectiles;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.GameObject;
import spacetitanic.gameobjects.abilities.Delay;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Delay {

    private int damage, range, rangeCounter = 0;
    private double deltaX, deltaY;
    private BufferedImage bulletImage;
    private boolean delayed = true;
    private int delayTimer = 0, delayDuration = 10;

    public Bullet(GamePanel gamePanel, double x, double y, double direction, double speed, int damage, int range, BufferedImage bulletImage) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.rotation = direction;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.bulletImage = bulletImage;
        int[] xPoints = {(int) (0 * gamePanel.scaleX), (int) (4 * gamePanel.scaleX), (int) (6 * gamePanel.scaleX), (int) (4 * gamePanel.scaleX), (int) (0 * gamePanel.scaleX)};
        int[] yPoints = {(int) (-1 * gamePanel.scaleY), (int) (-1 * gamePanel.scaleY), (int) (0 * gamePanel.scaleY), (int) (1 * gamePanel.scaleY), (int) (1 * gamePanel.scaleY)};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);
        objectTransform = new AffineTransform();
        /*objectTransform.translate(x, y);*/
        deltaX = Math.cos(Math.toRadians(direction)) * speed;
        deltaY = Math.sin(Math.toRadians(direction)) * speed;
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

        if (!checkDelayed()) {
            /* Check if the bullet has collided with another object */

        }
    }

    @Override
    protected void renderObject(Graphics2D g2, double positionX, double positionY) {
        /*super.renderObject(g2, positionX, positionY);*/
        AffineTransform old = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(positionX, positionY);
        objectTransform.rotate(Math.toRadians(rotation));
        g2.transform(objectTransform);

        g2.drawImage(bulletImage,
                0,
                (int) (-1 * bulletImage.getHeight() / 2 * gamePanel.scaleY),
                (int) (bulletImage.getWidth() * gamePanel.scaleX),
                (int) (bulletImage.getHeight() * gamePanel.scaleY),
                null);

        g2.setColor(Color.red);
        g2.fill(collisionShape);

        g2.setTransform(old);


    }

    @Override
    public boolean isDelayed() {
        return delayed;
    }

    @Override
    public boolean checkDelayed() {
        if (delayed) {
            delayTimer++;
            if (delayTimer >= delayDuration) {
                delayed = false;
            }
        }
        return delayed;
    }
}
