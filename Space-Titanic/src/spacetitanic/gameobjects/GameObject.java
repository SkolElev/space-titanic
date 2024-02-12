package spacetitanic.gameobjects;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected GamePanel gamePanel;
    protected double x, y;
    /* The "rotation" is the direction in which the object is looking at.
     * While "direction" is the direction in which the object moves in. */
    protected double rotation, direction, maxSpeed, speed, acceleration, deceleration, rotationSpeed;
    protected int currentImage;
    protected BufferedImage[] images;

    protected AffineTransform objectTransform = new AffineTransform();

    protected Shape collisionShape;

    public abstract void update();
    public abstract void render(Graphics2D g2);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
