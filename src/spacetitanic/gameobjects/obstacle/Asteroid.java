package spacetitanic.gameobjects.obstacle;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.GameObject;
import spacetitanic.gameobjects.Resource;
import spacetitanic.gameobjects.abilities.Destroyable;
import spacetitanic.gameobjects.abilities.Profit;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Asteroid extends GameObject implements Destroyable, Profit {
    private Color debrisColor;
    private int maxHitpoints, hitpoints;
    private int radie;
    private int value;

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
        maxHitpoints = radie + 20;
        hitpoints = maxHitpoints;
        images = new BufferedImage[1];
        images[0] = gamePanel.graphics.crater200x200[(int) (Math.random() * gamePanel.graphics.crater200x200.length)];
        value = radie * 3;
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
        g2.drawImage(images[0], (int) (-collisionShape.getBounds().width * gamePanel.scaleX / 2), (int) (-collisionShape.getBounds().getBounds().height * gamePanel.scaleY / 2),
                (int) (images[0].getWidth() * gamePanel.scaleX), (int) (images[0].getWidth() * gamePanel.scaleY), null);
        g2.setClip(null);
        g2.setColor(Color.green);
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
        /*System.out.println("Asteroid destroyed at x:" + (int) x + ", y:" + (int) y);*/
        int minimumRadie = 20;
        if (radie > minimumRadie) {
            for (int i = 0; i < 2; i++) {
                int rad = (int) (radie * (Math.random() * 0.2 + 0.3));
                if (rad > 10) {
                    Asteroid asteroid = new Asteroid(gamePanel, (int) x, (int) y, rad);
                    asteroid.setDirection((Math.random() * 120 - 60) + direction);
                    gamePanel.map.addObjects(asteroid);
                }
            }
        }
        if (radie <= minimumRadie) {
            if (Math.random() < 0.4) {
                int newValue = (int) (Math.random() * 180 + 80);
                generateResource(newValue);
            } else {
                generateResource(value);
            }
        }
        do {
            int resValue = (int) (Math.random() * value + value / 4);
            if (resValue < value / 2) {
                generateResource(resValue);
                value -= resValue;
            } else {
                generateResource(value);
                value = 0;
            }
        } while (value > 0);
    }

    @Override
    public void takeDamage(int damage) {
        hitpoints -= damage;
        if (hitpoints <= 0) {
            destroyed();
            dead = true;
        }
    }

    public Color getDebrisColor() {
        return debrisColor;
    }


    @Override
    public void generateResource(int value) {
        Resource resource = new Resource(gamePanel, (int) x, (int) y, value);
        gamePanel.map.addObjects(resource);

    }
}
