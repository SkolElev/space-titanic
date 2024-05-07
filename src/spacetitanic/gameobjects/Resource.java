package spacetitanic.gameobjects;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Resource extends GameObject {
    private float scale = 1.0f;
    private int value;
    private int updateTimer = 0;
    private int updateDelay = 6;

    public Resource(GamePanel gamePanel, int x, int y, int value) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.value = value;
        speed = Math.random() * 2 + 0.5;
        direction = Math.random() * 360.0;
        rotation = 0.0;
        rotationSpeed = Math.random() * 0.8 - 0.4;
        velocityVector.set(Math.cos(Math.toRadians(direction)) * speed, Math.sin(Math.toRadians(direction)) * speed);

        int iconNumber = 0;
        if (value > 200) {
            iconNumber = 3;
        } else if (value > 100) {
            iconNumber = 2;
        } else {
            iconNumber = (int) (Math.random() * 4);
            if (iconNumber >= 2) {
                iconNumber += 2;
            }
        }
        images = gamePanel.graphics.gemIcons[iconNumber];
        int width = (int) (images[0].getWidth() * gamePanel.scaleX * scale);
        int height = (int) (images[0].getHeight() * gamePanel.scaleY * scale);
        int[] xPoints = {0, width / 2, 0, -width / 2};
        int[] yPoints = {-height / 2, 0, height / 2, 0};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);

        /*System.out.println("Resource value: " + value);*/

    }

    @Override
    public void update() {
        x += velocityVector.x;
        y += velocityVector.y;

        x = (x + gamePanel.worldWidth) % gamePanel.worldWidth;
        y = (y + gamePanel.worldHeight) % gamePanel.worldHeight;

        positionVector.set(x, y);
        rotation += rotationSpeed;

        updateTimer++;
        if (updateTimer >= updateDelay) {
            currentImage++;
            if (currentImage >= images.length - 1) {
                currentImage = 0;
            }
            updateTimer = 0;
        }

    }

    @Override
    protected void renderObject(Graphics2D g2, double positionX, double positionY) {
        AffineTransform old = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(positionX, positionY);
        objectTransform.rotate(Math.toRadians(rotation));
        g2.transform(objectTransform);

        g2.drawImage(images[currentImage], (int) (-images[0].getWidth() * scale * gamePanel.scaleX / 2), (int) (-images[0].getHeight() * gamePanel.scaleY * scale / 2),
                (int) (images[0].getWidth() * scale * gamePanel.scaleX), (int) (images[0].getHeight() * scale * gamePanel.scaleY), null);

        g2.setColor(Color.white);
        g2.draw(collisionShape);

        g2.setTransform(old);

    }

    public int pickUp() {
        dead = true;
        return value;
    }

}
