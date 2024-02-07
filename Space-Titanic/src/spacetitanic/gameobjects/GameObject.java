package spacetitanic.gameobjects;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GameObject {
    protected GamePanel gamePanel;
    protected double x, y;
    /* The "rotation" is the direction in which the object is looking at.
     * While "direction" is the direction in which the object moves in. */
    protected double rotation, direction, maxSpeed, speed, acceleration, deceleration, rotationSpeed;

    protected AffineTransform objectTransform = new AffineTransform();

    protected Shape collisionShape;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
