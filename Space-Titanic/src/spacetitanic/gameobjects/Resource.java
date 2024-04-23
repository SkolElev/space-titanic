package spacetitanic.gameobjects;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Resource extends GameObject {
    float scale = 1.0f;

    public Resource(GamePanel gamePanel, int x, int y, int value) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        speed = Math.random() * 2 + 0.5;
        direction = Math.random() * 360.0;
        rotation = 0.0;
        rotationSpeed = Math.random() * 0.8 - 0.4;
        velocityVector.set(Math.cos(Math.toRadians(direction)) * speed, Math.sin(Math.toRadians(direction)) * speed);

        images = new BufferedImage[1];
        int iconNumber = 0;
        if (value > 200) {
            iconNumber = 2;
        } else if (value > 100) {
            iconNumber = 1;
        }
        images[0] = gamePanel.graphics.resourceIcons[iconNumber];
        /* This code is wrong! and needs to be fixed! */
        int collisionShapeWidth = (int) (images[0].getWidth() * scale * gamePanel.scaleX);
        int collisionShapeHeight = (int) (images[0].getHeight() * scale * gamePanel.scaleY);
        collisionShape = new Rectangle((int) (collisionShapeWidth / 6), (int) (collisionShapeHeight / 6), collisionShapeWidth, collisionShapeHeight);
    }

    @Override
    public void update() {
        x += velocityVector.x;
        y += velocityVector.y;

        x = (x + gamePanel.worldWidth) % gamePanel.worldWidth;
        y = (y + gamePanel.worldHeight) % gamePanel.worldHeight;

        positionVector.set(x, y);
        rotation += rotationSpeed;
    }

    @Override
    protected void renderObject(Graphics2D g2, double positionX, double positionY) {
        AffineTransform old = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(positionX, positionY);
        objectTransform.rotate(Math.toRadians(rotation));
        g2.transform(objectTransform);

        g2.drawImage(images[0], (int) (-images[0].getWidth() * scale / 2), (int) (-images[0].getHeight() * scale / 2), (int) (images[0].getWidth() * scale * gamePanel.scaleX),
                (int) (images[0].getWidth() * scale * gamePanel.scaleY), null);
        g2.setClip(null);
        g2.setColor(Color.green);
        g2.draw(collisionShape);


        g2.setTransform(old);

    }

}
