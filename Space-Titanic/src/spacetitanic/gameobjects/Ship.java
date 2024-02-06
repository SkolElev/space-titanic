package spacetitanic.gameobjects;

import spacetitanic.GamePanel;
import spacetitanic.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ship extends GameObject {

    Vector2D positionVector = new Vector2D(0.0, 0.0);
    Vector2D velocityVector = new Vector2D(0.0, 0.0);

    public Ship(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        rotation = 45.0;
        x = (double) gamePanel.screenWidth / 2;
        y = (double) gamePanel.screenHeight / 2;
        /* "The Doritos Ship" */
        int[] xPoints = {30, -15, -8, -15};
        int[] yPoints = {0, 15, 0, -15};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);

        speed = 10.5;

    }

    public void update() {
        /* Check collision */

        /* Updates the position and speed */
        updatePosition();
        /* Applies friction */
        updateVelocity();

        /* Update cargo bay */

        /* Weapon update */
    }

    public void render(Graphics2D g2) {
        /* temporary transform */
        AffineTransform oldCoordinates = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(x - gamePanel.camera.getxOffset(), y - gamePanel.camera.getyOffset());
        objectTransform.rotate(Math.toRadians(rotation));
        objectTransform.scale(1.5, 1.5);
        g2.transform(objectTransform);

        /* Render "The Doritos Ship" */
        g2.setColor(Color.red);
        g2.fill(collisionShape);
        g2.setColor(Color.white);
        g2.draw(collisionShape);

        /* Changes the coordinates to its previous values */
        g2.setTransform(oldCoordinates);
    }

    public void rotateLeft() {
        /* temporary code, must be improved later */
        rotation -= 5.5;
    }

    public void rotateRight() {
        /* temporary code, must be improved later */
        rotation += 5.5;
    }

    public void accelerate() {
        double deltaX = Math.cos(Math.toRadians(rotation)) * speed;
        double deltaY = Math.sin(Math.toRadians(rotation)) * speed;
        x = x + deltaX;
        y = y + deltaY;
    }

    public void decelerate() {
        double deltaX = Math.cos(Math.toRadians(rotation - 180)) * speed;
        double deltaY = Math.sin(Math.toRadians(rotation - 180)) * speed;
        x = x + deltaX;
        y = y + deltaY;
    }

    private void updatePosition() {
        positionVector.add(velocityVector);
    }

    private void updateVelocity() {

    }

}
