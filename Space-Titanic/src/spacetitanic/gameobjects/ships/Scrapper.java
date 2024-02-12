package spacetitanic.gameobjects.ships;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.Ship;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class Scrapper extends Ship {


    public Scrapper(GamePanel gamePanel) {
        super(gamePanel);
        rotation = 45.0;
        x = (double) gamePanel.screenWidth / 2;
        y = (double) gamePanel.screenHeight / 2;

        try {
            images = gamePanel.spriteSheetLoader(64, 64, 17, 1, "ships/scrapper.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("scrapper " + images.length);

        /* "The Doritos Ship" */
        int[] xPoints = {30, -15, -8, -15};
        int[] yPoints = {0, 15, 0, -15};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);

        acceleration = 0.004;
        deceleration = 0.002;
        maxSpeed = 9.5;

        rotationSpeed = 0.5;
        maxTurnSpeed = 3.5;

        updateTimer = 0;
        updateDelay = 3; /* Approximately 20 updates per second */
    }

    @Override
    public void update() {
        super.update();
        updateTimer++;
        if (updateTimer >= updateDelay) {
            /* Update the frame of the graphics */
            currentImage++;
            if (currentImage >= images.length - 1) {
                currentImage = 0;
            }
            if (accelerating) {

            } else {

            }

            updateTimer = 0;
        }


    }

    public void render(Graphics2D g2) {
        /* temporary transform */
        AffineTransform oldCoordinates = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(x - gamePanel.camera.getxOffset(), y - gamePanel.camera.getyOffset());
        objectTransform.rotate(Math.toRadians(rotation));
        /*objectTransform.scale(1.5, 1.5);*/
        g2.transform(objectTransform);

        g2.drawImage(images[currentImage], -images[currentImage].getWidth() / 2, -images[currentImage].getHeight() / 2,
                (int) (images[currentImage].getWidth() * gamePanel.scaleX), (int) (images[currentImage].getHeight() * gamePanel.scaleY),
                null);

        /* Render "The Doritos Ship" */
        g2.setColor(Color.red);
        g2.draw(collisionShape);

        /* Changes the coordinates to its previous values */
        g2.setTransform(oldCoordinates);
    }

}
