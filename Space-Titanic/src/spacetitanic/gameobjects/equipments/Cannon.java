package spacetitanic.gameobjects.equipments;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cannon extends Equipment {

    private int fireCounter, reloadTimer = 0, projectileType = 0;
    private boolean firing = false;


    public Cannon(GamePanel gamePanel, Hardpoint hardpoint) {
        super(gamePanel, hardpoint);
        this.equipmentType = EquipmentType.CANNON;
        this.hardpointType = HardpointType.WEAPON;

        /* Basic cannon values */
        maxAmmunition = 10;
        damage = 20;
        range = 900;
        reloadTime = 2.0;
        projectileSpeed = 8.0;
        fireDelay = 10;
        equipmentName = "Small Cannon";

        ammunition = maxAmmunition;
        fireCounter = 0;
        try {
            BufferedImage[] pImages = gamePanel.spriteSheetLoader(6, 3, 3, 4, "ships/weapons/bullets_6x3.png");
            this.projectileImage = pImages[(int) (Math.random() * pImages.length)];
            equipmentImages = gamePanel.spriteSheetLoader(32, 32, 8, 1, "ships/weapon/tri_cannon.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
        if (ammunition <= 0) {
            reloading = true;
        }
        if (reloading) {
            imageNumber = 0;
            reloadTime++;
            if (reloadTimer >= reloadTime * gamePanel.FPS) {
                reloadTimer = 0;
                reloading = false;
                ammunition = maxAmmunition;
            }
        }
    }

    @Override
    public void render(Graphics2D g2) {
        int width = (int) (equipmentImages[imageNumber].getWidth() * gamePanel.scaleX);
        int height = (int) (equipmentImages[imageNumber].getWidth() * gamePanel.scaleY);
        int x = (int) hardpoint.getHardCenterPoint().getX();
        int y = (int) hardpoint.getHardCenterPoint().getY();
        g2.drawImage(equipmentImages[imageNumber], x - width / 2, y - height / 2, width, height, null);
    }

    @Override
    public void activate() {

    }

    @Override
    public void activate(GameObject target) {

    }

    @Override
    public void deactivate() {

    }
}
