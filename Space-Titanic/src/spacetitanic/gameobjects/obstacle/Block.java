package spacetitanic.gameobjects.obstacle;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Block extends GameObject {
    private Color color;

    public Block(GamePanel gamePanel, int x, int y) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        collisionShape = createRandomPolygon();
        color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), 200);
    }

    @Override
    public void update() {
        x = (x + gamePanel.worldWidth) % gamePanel.worldWidth;
        y = (y + gamePanel.worldHeight) % gamePanel.worldHeight;
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
    }

    @Override
    protected void renderObject(Graphics2D g2, double positionX, double positionY) {
        AffineTransform old = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(positionX, positionY);
        objectTransform.rotate(Math.toRadians(rotation));
        g2.transform(objectTransform);

        g2.setColor(color);
        g2.fill(collisionShape);
        g2.setColor(Color.black);
        g2.draw(collisionShape);

        g2.drawString((int) (x / gamePanel.tileSizeX) + ", " + (int) (y / gamePanel.tileSizeY), 0, 0);
        g2.setColor(Color.white);
        g2.draw(collisionShape.getBounds());

        g2.setTransform(old);
    }

    private Shape createRandomPolygon() {
        double angle = 0.0;
        double size = Math.random() * 50 + 50;
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

}
