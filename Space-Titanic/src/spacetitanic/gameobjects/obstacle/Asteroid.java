package spacetitanic.gameobjects.obstacle;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.GameObject;
import spacetitanic.gameobjects.abilities.Destroyable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Asteroid extends GameObject implements Destroyable {
    private Color debrisColor;
    private int maxHitpoints, hitpoints;
    private int radie;

    public Asteroid(GamePanel gamePanel, int x, int y, int radie) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.radie = radie;
        collisionShape = createRandomPolygon(radie);
        int colorInt = (int) (Math.random() * 80 + 80);
        debrisColor = new Color(colorInt, colorInt, colorInt);
        speed = Math.random() * 2 + 0.5;
        direction = Math.random() * 360.0;
        rotation = 0.0;
        rotationSpeed = Math.random() * 0.8 - 0.4;
        velocityVector.set(Math.cos(Math.toRadians(direction)) * speed, Math.sin(Math.toRadians(direction)) * speed);
        maxHitpoints = 300;
        hitpoints = maxHitpoints;
        images = new BufferedImage[1];
        images[0] = gamePanel.graphicsLoader.crater200x200[(int) (Math.random() * gamePanel.graphicsLoader.crater200x200.length)];
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

        g2.setClip(collisionShape);
        g2.drawImage(images[0], -collisionShape.getBounds().width / 2, -collisionShape.getBounds().getBounds().height / 2,
                (int) (images[0].getWidth() * gamePanel.scaleX), (int) (images[0].getWidth() * gamePanel.scaleY), null);
        g2.setClip(null);
        g2.setColor(Color.black);
        g2.draw(collisionShape);

        if (hit) {
            g2.setColor(Color.red);
        } else {
            g2.setColor(Color.white);
        }
        g2.draw(collisionShape.getBounds());

        g2.setTransform(old);

        int hitbarLength = 150;
        int currentHBLength = (int) ((double) hitpoints / maxHitpoints * hitbarLength);
        g2.setColor(Color.orange);
        g2.fillRect((int) (positionX - hitbarLength / 2), (int) (positionY + collisionShape.getBounds().getHeight() / 2), currentHBLength, 10);
        g2.setColor(Color.red);
        g2.drawRect((int) (positionX - hitbarLength / 2), (int) (positionY + collisionShape.getBounds().getHeight() / 2), hitbarLength, 10);
    }

    private Shape createRandomPolygon(double size) {
        double angle = 0.0;
        double currentSize = size;
        ArrayList<Point> points = new ArrayList<>();
        while (angle < 360.0) {
            currentSize = (int) (size + Math.random() * currentSize * 0.5 - currentSize * 0.25);
            Point p = new Point((int) (Math.cos(Math.toRadians(angle)) * currentSize), (int) (Math.sin(Math.toRadians(angle)) * currentSize));
            points.add(p);
            angle = angle + Math.random() * 10 + 25.0;
        }
        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xPoints[i] = points.get(i).x;
            yPoints[i] = points.get(i).y;
        }
        return new Polygon(xPoints, yPoints, xPoints.length);
    }


    @Override
    public void destroyed() {
        System.out.println("Asteroid destroyed at x:" + (int) x + ", y:" + (int) y);
        if (radie > 20) {
            Asteroid asteroid1 = new Asteroid(gamePanel, (int) x, (int) y, radie / 2);
            Asteroid asteroid2 = new Asteroid(gamePanel, (int) x, (int) y, radie / 2);
            gamePanel.map.addObjects(asteroid1);
            gamePanel.map.addObjects(asteroid2);
        }
    }

    @Override
    public void takeDamage(int damage) {
        hitpoints -= damage;
        if (hitpoints <= 0) {
            destroyed();
            dead = true;
        }
    }
}
