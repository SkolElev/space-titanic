package spacetitanic.gameobjects.equipments;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.Ship;

import java.awt.*;
import java.awt.geom.Point2D;

public class Hardpoint {
    private GamePanel gamePanel;
    private Ship ship;
    private HardpointType type;
    private Equipment equipped;
    private String hardpointName = "unnamed hardpoint";
    public Point2D hardCenter;

    public Hardpoint(GamePanel gamePanel, Ship ship, int centerX, int centerY, HardpointType type, String hardpointName) {
        this.gamePanel = gamePanel;
        this.ship = ship;
        this.type = type;
        this.hardpointName = hardpointName;
        hardCenter = new Point2D.Double(centerX, centerY);
    }

    public void update() {
        if (equipped != null) {
            equipped.update();
        }
    }

    public void render(Graphics2D g2) {
        if (equipped != null) {
            equipped.render(g2);
        }
        g2.setColor(Color.yellow);
        int size = 10;
        g2.drawRect((int) (getHardCenter().getX() - size / 2), (int) (getHardCenter().getY() - size / 2), size, size);
    }

    public Point2D getHardCenter() {
        Point2D transformedPoint = ship.objectTransform.transform(hardCenter, null);
        return transformedPoint;
    }

    public HardpointType getType() {
        return type;
    }

    public Equipment getEquipped() {
        return equipped;
    }

    public void setEquipped(Equipment equipped) {
        this.equipped = equipped;
    }

    public String getHardpointName() {
        return hardpointName;
    }
}
