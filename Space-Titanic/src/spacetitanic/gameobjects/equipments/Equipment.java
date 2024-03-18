package spacetitanic.gameobjects.equipments;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.GameObject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Equipment {
    protected GamePanel gamePanel;
    protected Hardpoint hardpoint;
    protected EquipmentType equipmentType;
    protected HardpointType hardpointType;
    protected BufferedImage[] equipmentImages;
    protected String equipmentName;
    protected int imageNumber = 0;

    protected boolean reloading = false, manualReloadAllowed = true;
    protected int ammunition, maxAmmunition, damage, range, fireDelay;
    protected double reloadTime, projectileSpeed;
    protected BufferedImage projectileImage;


    public Equipment(GamePanel gamePanel, Hardpoint hardpoint) {
        this.gamePanel = gamePanel;
        this.hardpoint = hardpoint;
    }

    public abstract void update();

    public abstract void render(Graphics2D g2);

    public abstract void activate();

    public abstract void activate(GameObject target);

    public abstract void deactivate();

    public void reload() {
        if (manualReloadAllowed) {
            reloading = true;
        }
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public HardpointType getHardpointType() {
        return hardpointType;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public boolean isReloading() {
        return reloading;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public int getMaxAmmunition() {
        return maxAmmunition;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getFireDelay() {
        return fireDelay;
    }

    public double getReloadTime() {
        return reloadTime;
    }
}
