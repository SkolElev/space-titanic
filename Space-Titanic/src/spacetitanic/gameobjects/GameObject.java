package spacetitanic.gameobjects;

import java.awt.*;

public abstract class GameObject {
    protected double x, y;
    protected double rotation, direction;
    protected Shape collisionShape;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
