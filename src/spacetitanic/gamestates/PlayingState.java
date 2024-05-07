package spacetitanic.gamestates;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PlayingState implements State {
    GamePanel gamePanel;
    public GameState thisGameState = GameState.PLAYING;

    public PlayingState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void update() {
        if (gamePanel.input.isKeyUp(KeyEvent.VK_ESCAPE)) {
            /*System.out.println("Exited Playing State");*/
            gamePanel.gameStateManager.pop();
        }
        gamePanel.player.update();
        gamePanel.map.update();
    }

    @Override
    public void render(Graphics2D g2) {
        if (gamePanel.map != null) {
            gamePanel.map.render(g2);
        }

        /* Credz */
        g2.setFont(gamePanel.graphics.axaxaxMicro);
        g2.setColor(Color.orange);
        g2.drawString(gamePanel.player.getCredz() + " £", (int) (120 * gamePanel.scaleX), (int) (20 * gamePanel.scaleY));

        /* Backpanel */
        g2.drawImage(gamePanel.graphics.backpanel, 0, (int) (gamePanel.screenHeight - gamePanel.graphics.backpanel.getHeight() * gamePanel.scaleY),
                (int) (gamePanel.screenWidth), (int) (gamePanel.graphics.backpanel.getHeight() * gamePanel.scaleY), null);

        /* Weapon panel */
        int positionX = (int) (gamePanel.screenWidth / 2 + 100 * gamePanel.scaleX);
        int positionY = (int) (gamePanel.screenHeight - gamePanel.graphics.weaponPanel.getHeight() * gamePanel.scaleY * 0.9);
        g2.drawImage(gamePanel.graphics.weaponPanel, positionX, positionY, (int) (gamePanel.graphics.weaponPanel.getWidth() * gamePanel.scaleX),
                (int) (gamePanel.graphics.weaponPanel.getHeight() * gamePanel.scaleY),
                null);
        /* Equipment name */
        g2.setFont(gamePanel.graphics.axaxaxSmall);
        g2.setColor(Color.orange);
        g2.drawString(gamePanel.player.getShip().getWeapon().getEquipmentName(), (int) (positionX + 60 * gamePanel.scaleX),
                (int) (positionY + 25 * gamePanel.scaleY));
        if (gamePanel.player.getShip().getWeapon().isReloading()) {
            g2.setColor(Color.red);
        }
        /* Ammo amount */
        g2.drawString(String.valueOf(gamePanel.player.getShip().getWeapon().getAmmo()), (int) (positionX + 10 * gamePanel.scaleX),
                (int) (positionY + 57 * gamePanel.scaleY));
        /* Ammo image */
        BufferedImage ammoImage = gamePanel.player.getShip().getWeapon().getAmmoImage();
        int width = 152;
        double step = (double) width / gamePanel.player.getShip().getWeapon().getMaxAmmo() * gamePanel.scaleX;
        for (int i = 0; i < gamePanel.player.getShip().getWeapon().getAmmo(); i++) {
            g2.drawImage(ammoImage, (int) (positionX + 62 * gamePanel.scaleX + step * i),
                    (int) (positionY + 39 * gamePanel.scaleY), (int) (ammoImage.getWidth() * gamePanel.scaleX), (int) (ammoImage.getHeight() * gamePanel.scaleY), null);
        }

        /* Cargo panel */
        positionX = (int) (gamePanel.screenWidth / 2 - gamePanel.graphics.cargoPanel.getWidth() * gamePanel.scaleX - 135 * gamePanel.scaleX);
        positionY = (int) (gamePanel.screenHeight - gamePanel.graphics.cargoPanel.getHeight() * gamePanel.scaleY * 0.9);
        g2.drawImage(gamePanel.graphics.cargoPanel, positionX, positionY, (int) (gamePanel.graphics.cargoPanel.getWidth() * gamePanel.scaleX),
                (int) (gamePanel.graphics.cargoPanel.getHeight() * gamePanel.scaleY), null);

        g2.setFont(gamePanel.graphics.axaxaxMicro);
        gamePanel.player.render(g2);
    }

    @Override
    public GameState getGameState() {
        return thisGameState;
    }
}
