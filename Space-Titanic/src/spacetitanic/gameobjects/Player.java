package spacetitanic.gameobjects;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.ships.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private GamePanel gamePanel;
    private String playerName;
    private Ship ship;
    /* space money */
    private int credz = 0;

    public Player(GamePanel gamePanel, String playerName) {
        this.gamePanel = gamePanel;
        this.playerName = playerName;
        ship = new Scrapper(gamePanel);
    }

    public void update() {
        if (gamePanel.input.isKey(KeyEvent.VK_W)) {
            System.out.println("Ship accelerating");
            ship.accelerate();
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_W)) {
            System.out.println("Ship stopping acceleration");
            ship.stopAccelerating();
        }
        if (gamePanel.input.isKey(KeyEvent.VK_S)) {
            System.out.println("Ship reversing");
            ship.decelerate();
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_S)) {
            System.out.println("Ship stopping reversal");
            ship.stopDecelerating();
        }
        if (gamePanel.input.isKey(KeyEvent.VK_A)) {
            System.out.println("Ship turning left");
            ship.rotateLeft();
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_A)) {
            System.out.println("Ship stopping left turn");
            ship.stopRotatingLeft();
        }
        if (gamePanel.input.isKey(KeyEvent.VK_D)) {
            System.out.println("Ship turning right");
            ship.rotateRight();
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_D)) {
            System.out.println("Ship stopping right turn");
            ship.stopRotatingRight();
        }
        if (gamePanel.input.isKey(KeyEvent.VK_Q)) {
            System.out.println("Ship weapon changed to automatic");
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_Q)) {
            System.out.println("Ship weapon change to single shot");
        }
        if (gamePanel.input.isKeyDown(KeyEvent.VK_I)) {
            ship.showInfo();
        }

        ship.update();

    }

    public void render(Graphics2D g2) {
        ship.render(g2);
    }

    public Ship getShip() {
        return ship;
    }

}
