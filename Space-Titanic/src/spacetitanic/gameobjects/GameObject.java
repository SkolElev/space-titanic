package spacetitanic.gameobjects;

import spacetitanic.GamePanel;
import spacetitanic.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected GamePanel gamePanel;
    protected double x, y;
    /* The "rotation" is the direction in which the object is looking at.
     * While "direction" is the direction in which the object moves in. */
    protected double rotation, direction, maxSpeed, speed, acceleration, deceleration, rotationSpeed;
    protected Vector2D positionVector = new Vector2D(0.0, 0.0);
    protected Vector2D velocityVector = new Vector2D(0.0, 0.0);
    protected int currentImage;
    protected BufferedImage[] images;

    public AffineTransform objectTransform = new AffineTransform();

    protected Shape collisionShape;
    protected boolean hit = false;


    public abstract void update();

    public void render(Graphics2D g2) {
        int screenX = (int) (x - gamePanel.camera.getXOffset());
        int screenY = (int) (y - gamePanel.camera.getYOffset());

        renderObject(g2, screenX, screenY);

        boolean nearRightEdge = screenX > gamePanel.screenWidth - gamePanel.tileSizeX;
        boolean nearBottomEdge = screenY > gamePanel.screenHeight - gamePanel.tileSizeY;
        boolean nearLeftEdge = screenX < 0;
        boolean nearTopEdge = screenY < 0;

        /* When going straight over the map edges */
        if (nearLeftEdge) {
            renderObject(g2, screenX + gamePanel.worldWidth, screenY);
        }
        if (nearRightEdge) {
            renderObject(g2, screenX - gamePanel.worldWidth, screenY);
        }
        if (nearTopEdge) {
            renderObject(g2, screenX, screenY + gamePanel.worldHeight);
        }
        if (nearBottomEdge) {
            renderObject(g2, screenX, screenY - gamePanel.worldHeight);
        }
        /* When going over a corner */
        if (nearLeftEdge && nearTopEdge) {
            renderObject(g2, screenX + gamePanel.worldWidth, screenY + gamePanel.worldHeight);
        }
        if (nearLeftEdge && nearBottomEdge) {
            renderObject(g2, screenX + gamePanel.worldWidth, screenY - gamePanel.worldHeight);
        }
        if (nearRightEdge && nearTopEdge) {
            renderObject(g2, screenX - gamePanel.worldWidth, screenY + gamePanel.worldHeight);
        }
        if (nearRightEdge && nearBottomEdge) {
            renderObject(g2, screenX - gamePanel.worldWidth, screenY - gamePanel.worldHeight);
        }
    }

    protected void renderObject(Graphics2D g2, double positionX, double positionY) {
        AffineTransform old = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(positionX, positionY);
        objectTransform.rotate(Math.toRadians(rotation));
        g2.transform(objectTransform);

        g2.setColor(Color.orange);
        g2.fill(collisionShape);
        g2.setColor(Color.black);
        g2.draw(collisionShape);

        g2.setTransform(old);
    }

    public boolean checkCollision(Shape otherShape) {
        if (objectTransform.createTransformedShape(collisionShape).getBounds().intersects(otherShape.getBounds())) {
            /*System.out.println("Objects intersecting");*/
            Area a = new Area(objectTransform.createTransformedShape(collisionShape));
            a.intersect(new Area(otherShape));
            if (!a.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public Shape getCollisionShape() {
        return objectTransform.createTransformedShape(collisionShape);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
}
