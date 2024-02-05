package spacetitanic.gameobjects;

import spacetitanic.GamePanel;

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
        ship = new Ship(gamePanel);
    }

    public void update() {
        if (gamePanel.input.isKey(KeyEvent.VK_W)) {
            System.out.println("Ship accelerating");
            ship.goUp();
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_W)) {
            System.out.println("Ship stopping acceleration");
        }
        if (gamePanel.input.isKey(KeyEvent.VK_S)) {
            System.out.println("Ship reversing");
            ship.goDown();
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_S)) {
            System.out.println("Ship stopping reversal");
        }
        if (gamePanel.input.isKey(KeyEvent.VK_A)) {
            System.out.println("Ship turning left");
            ship.goLeft();
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_A)) {
            System.out.println("Ship stopping left turn");
        }
        if (gamePanel.input.isKey(KeyEvent.VK_D)) {
            System.out.println("Ship turning right");
            ship.goRight();
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_D)) {
            System.out.println("Ship stopping right turn");
        }
        if (gamePanel.input.isKey(KeyEvent.VK_Q)) {
            System.out.println("Ship weapon changed to automatic");
        }
        if (gamePanel.input.isKeyUp(KeyEvent.VK_Q)) {
            System.out.println("Ship weapon change to single shot");
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
