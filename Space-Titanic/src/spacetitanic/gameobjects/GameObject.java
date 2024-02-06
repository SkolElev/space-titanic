package spacetitanic.gameobjects;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GameObject {
    protected double x, y;
    /* The "rotation" is the direction in which the object is looking at.
     * While "direction" is the direction in which the object moves in. */
    protected double rotation, direction;

    protected AffineTransform objectTransform = new AffineTransform();

    protected Shape collisionShape;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
