package spacetitanic.gameobjects;

import spacetitanic.GamePanel;
import spacetitanic.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Particle {
    private GamePanel gamePanel;
    private double x, y, deltaX, deltaY;
    /* The "rotation" is the direction in which the object is looking at.
     * While "direction" is the direction in which the object moves in. */
    private double rotation, direction, speed, rotationSpeed;
    private BufferedImage image;
    private AffineTransform objectTransform = new AffineTransform();
    private Shape particleShape;
    private boolean dead = false;

    private int lifeCounter;
    private Color color;

    public Particle(GamePanel gamePanel, double x, double y, int size, Color color) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.color = color;
        if (size < 2) {
            size = 2;
        }
        if (this.color == null) {
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            color = new Color(r, g, b);
        }
        particleShape = new Ellipse2D.Float(0 - size / 2.0f, 0 - size / 2.0f, (float) size, (float) size);
        rotation = 0;
        rotationSpeed = 0;
        direction = Math.random() * 360.0;
        speed = Math.random() * 8.0 * gamePanel.scaleX + 1.0;
        lifeCounter = (int) (Math.random() * 20 + 10);
        deltaX = Math.cos(Math.toRadians(direction) * speed);
        deltaY = Math.sin(Math.toRadians(direction) * speed);
    }

    public void update() {
        lifeCounter--;
        if (lifeCounter < 0) {
            dead = true;
        }
        x = x + deltaX;
        y = y + deltaY;
        x = (x + gamePanel.worldWidth) % gamePanel.worldWidth;
        y = (y + gamePanel.worldHeight) % gamePanel.worldHeight;
    }

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

        g2.setColor(color);
        g2.fill(particleShape);

        g2.setTransform(old);
    }
}
